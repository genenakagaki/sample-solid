package com.genenakagaki.sample.solid.repository

import com.genenakagaki.sample.solid.domain.book.UserBookRentalModel
import com.genenakagaki.sample.solid.domain.book.UserBookRentalEntry
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

interface UserBookRentalRepository {

    fun findByUsername(username: String): UserBookRentalModel?
    fun save(model: UserBookRentalModel)
}

@Repository
class UserBookRentalRepositoryImpl(
    private val db: DSLContext,
) : UserBookRentalRepository {

    override fun findByUsername(username: String): UserBookRentalModel? {
        val userRecord = db.fetch(
            """
            SELECT username, credit FROM app_user
            WHERE username = ?
        """.trimIndent(), username
        ).firstOrNull() ?: return null

        val currentRentalList = db.fetch(
            """
            SELECT book_id, rented_at, rent_until FROM app_user_book_rental_current
            WHERE username = ?
            """.trimIndent(), username
        ).map {
            UserBookRentalEntry(
                bookId = it.get("book_id") as Int,
                rentedAt = it.get("rented_at") as LocalDateTime,
                rentUntil = it.get("rent_until") as LocalDateTime,
            )
        }

        val rentalHistory = db.fetch(
            """
            SELECT book_id, rented_at, rent_until FROM app_user_book_rental_history
            WHERE username = ?
        """.trimIndent(), username
        ).map {
            UserBookRentalEntry(
                bookId = it.get("book_id") as Int,
                rentedAt = it.get("rented_at") as LocalDateTime,
                rentUntil = it.get("rent_until") as LocalDateTime,
            )
        }

        return UserBookRentalModel(
            username = username,
            userCredit = userRecord.get("credit") as Int,
            currentRentalList = currentRentalList,
            rentalHistory = rentalHistory
        )
    }

    override fun save(model: UserBookRentalModel) {
        db.execute(
            """
            UPDATE app_user
            SET credit = ?
            WHERE username = ?
        """.trimIndent(), model.userCredit, model.username
        )

        db.execute(
            """
            DELETE FROM app_user_book_rental_current
            WHERE username = ?
        """.trimIndent(), model.username
        )

        model.currentRentalList.forEach { entry ->
            db.execute(
                """
            INSERT INTO app_user_book_rental_current(`username`, `book_id`, `rented_at`, `rent_until`)
            VALUES (?, ?, ?, ?)
        """.trimIndent(), model.username, entry.bookId, entry.rentedAt, entry.rentUntil
            )
        }

        model.rentalHistory.forEach { entry ->
            db.execute(
                """
            INSERT INTO app_user_book_rental_history(`username`, `book_id`, `rented_at`, `rent_until`)
            VALUES (?, ?, ?, ?)
        """.trimIndent(), model.username, entry.bookId, entry.rentedAt, entry.rentUntil
            )
        }
    }
}

