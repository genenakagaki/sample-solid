package com.genenakagaki.sample.solid

import com.genenakagaki.sample.solid.repository.BookRepository
import com.genenakagaki.sample.solid.repository.UserRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class BookManagementController(
    val bookRepository: BookRepository,
    val userRepository: UserRepository,
) {

    /**
     * bodyのサンプルデータ
     * {
     *   username: "g-nakagaki",
     *   book_id: "1"
     * }
     */
    @PostMapping("/api/book/rent")
    fun rentBook(@RequestBody body: Map<String, String>) {
        val username = body["username"]!!
        val user = userRepository.findByUsername(username)
            ?: throw CustomException("ユーザーが見つかりませんでした。")

        val currentRentalList = bookRepository.findCurrentRentalList(username)

        for (item in currentRentalList) {
            if (item["book_id"] == body["book_id"]) {
                throw CustomException("既に借りてます。")
            }
        }

        val bookPrice = bookRepository.findBookPrice(body["book_id"]!!.toInt())
            ?: throw CustomException("本がみつかりませんでした。")

        if (user["credit"]!!.toInt() < bookPrice["rent_price"]!!.toInt()) {
            throw CustomException("お金が足りません。")
        }

        val updatedUserCredit = user["credit"]!!.toInt() - bookPrice["rent_price"]!!.toInt()
        userRepository.updateUserCredit(username, updatedUserCredit)

        val rentalData = mapOf(
            "username" to username,
            "book_id" to body["book_id"]!!,
            "rented_at" to LocalDateTime.now().toString(),
            "rent_until" to LocalDateTime.now().plusDays(7).toString()
        )

        bookRepository.insertRentalData(rentalData);
    }
}
