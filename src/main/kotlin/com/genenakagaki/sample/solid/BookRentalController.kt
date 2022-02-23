package com.genenakagaki.sample.solid

import org.jooq.DSLContext
import org.springframework.http.HttpStatus
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@RestController
class BookRentalController(
    private val db: DSLContext,
    private val mailSender: JavaMailSender,
) {

    /**
     * bodyのサンプルデータ
     * {
     *   username: "g-nakagaki",
     *   book_id: "1"
     * }
     */
    @GetMapping("/api/book/view")
    fun viewBook(@RequestBody body: Map<String, String>): Mono<Any?> {
        // 今レンタル中のリスト
        // [
        //   {
        //     book_id: 1,
        //     rented_at: 2022-01-01
        //     rent_until: 2022-01-01
        //   }
        // ]
        val currentRentalList = db.fetch(
            """
                SELECT book_id, rented_at, rent_until FROM app_user_book_rental_current
                WHERE username = ?
            """, body["username"]
        ).intoMaps()

        var isRented = false
        for (rental in currentRentalList) {
            if (rental["book_id"] == body["book_id"]) {
                isRented = true
            }
        }

        if (!isRented) {
            throw RuntimeException("この本は閲覧できません。")
        }

        // 今レンタル中のリスト
        // {
        //   book_id: 1,
        //   title: "A Book",
        //   content: "Once upon on a time in..."
        // }
        val bookContent = db.fetch(
            """
                SELECT book_id, title, content FROM book
                WHERE book_id = ?
            """, body["book_id"]
        ).intoMaps().first()

        if (bookContent == null) {
            throw RuntimeException("本がみつかりませんでした。")
        }

        return Mono.just(bookContent)
    }

    /**
     * bodyのサンプルデータ
     * {
     *   username: "g-nakagaki",
     *   book_id: "1"
     * }
     */
    @PostMapping("/api/book/rent")
    fun rentBook(@RequestBody body: Map<String, String>) {
        val username = body["username"]

        // 今レンタル中のリスト
        // [
        //   {
        //     book_id: 1,
        //     rented_at: 2022-01-01
        //     rent_until: 2022-01-01
        //   }
        // ]
        val currentRentalList = db.fetch(
            """
                SELECT book_id, rented_at, rent_until FROM app_user_book_rental_current
                WHERE username = ?
            """, username
        ).intoMaps()

        // 既にレンタルしてるかどうかのチェック
        for (book in currentRentalList) {
            if (book["book_id"] == body["book_id"]) {
                throw RuntimeException("この本は既にレンタル中です。")
            }
        }

        // レンタルしたい本の金額
        // {
        //   rent_price: 100,
        //   buy_price: 1000
        // }
        val bookPrice = db.fetch(
            """
            SELECT rent_price, buy_price FROM book_price
            WHERE book_id = ?
        """, body["book_id"]
        ).intoMaps().first()

        if (bookPrice == null) {
            throw RuntimeException("本がみつかりませんでした。")
        }

        // ユーザーデータ
        // {
        //   username: "g-nakagaki",
        //   email: "g-nakagaki@email.com",
        //   role: "USER",
        //   credit: "1000"
        // }
        val user = db.fetch(
            """
                SELECT username, email, role, credit FROM app_user 
                WHERE username = ?
            """, username
        ).intoMaps().first()

        if (user == null) {
            throw RuntimeException("ユーザーが見つかりませんでした。")
        }

        // ユーザーのお金が足りるかどうかのチェック
        if (user["credit"].int() < bookPrice["rent_price"].int()) {
            throw RuntimeException("お金が足りません。")
        }

        // ユーザーのお金から貸し出し金額を引く
        val updatedUserCredit = user["credit"].int() - bookPrice["rent_price"].int()

        // ユーザーの更新されたお金をDBに保存
        db.execute(
            """
                UPDATE app_user
                SET credit = ?
                WHERE username = ?
            """, updatedUserCredit, username
        )

        val rentedAt = LocalDateTime.now()

        // レンタル期間は７日
        val rentUntil = rentedAt.plusDays(7)

        // 今レンタル中のリストに新しくレンタルした本を追加
        db.execute(
            """
            INSERT INTO app_user_book_rental_current(`username`, `book_id`, `rented_at`, `rent_until`)
            VALUES (?, ?, ?, ?)
        """.trimIndent(),
            username,
            body["book_id"],
            rentedAt,
            rentUntil
        )

        // レンタル履歴に新しくレンタルした本を追加
        db.execute(
            """
            INSERT INTO app_user_book_rental_history(`username`, `book_id`, `rented_at`, `rent_until`)
            VALUES (?, ?, ?, ?)
        """.trimIndent(),
            username,
            body["book_id"],
            rentedAt,
            rentUntil
        )

        val message = SimpleMailMessage()
        message.setFrom("noreply@sample-solid.com")
        message.setTo(user["email"].str())
        message.setSubject("本のレンタルが完了しました。")
        message.setText(
            """
            ${username}様
            本のレンタルが完了したので読めますよ。
        """
        )
        mailSender.send(message)
    }

    @ExceptionHandler(RuntimeException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(e: RuntimeException): Mono<String> {
        return Mono.just(e.message ?: "")
    }
}
