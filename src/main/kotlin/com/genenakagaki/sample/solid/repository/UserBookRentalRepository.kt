package com.genenakagaki.sample.solid.repository

import com.genenakagaki.sample.solid.core.UserBookRentalEntry
import com.genenakagaki.sample.solid.core.UserBookRentalModel
import com.genenakagaki.sample.solid.int
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class UserBookRentalRepository(
    private val db: DSLContext,
    private val userRepository: UserRepository
) {

    fun findByUsername(username: String): UserBookRentalModel? {
        val user = userRepository.findByUsername(username)

        if (user == null) {
            return null;
        }

        val currentRentalList = db.fetch(
            """
            SELECT book_id, rented_at, rent_until FROM app_user_book_rental_current
            WHERE username = ?
            """, username
        ).map { data ->
            UserBookRentalEntry(
                data.get("book_id").int(),
                data.get("rented_at") as LocalDateTime,
                data.get("rent_until") as LocalDateTime,
            )
        }

        val rentalHistory = db.fetch(
            """
            SELECT book_id, rented_at, rent_until FROM app_user_book_rental_history
            WHERE username = ?
        """, username
        ).map { data ->
            UserBookRentalEntry(
                data.get("book_id").int(),
                data.get("rented_at") as LocalDateTime,
                data.get("rent_until") as LocalDateTime,
            )
        }

        return UserBookRentalModel(
            username,
            user.credit,
            currentRentalList,
            rentalHistory
        )
    }

    fun save(model: UserBookRentalModel) {
        db.execute(
            """
            UPDATE app_user
            SET credit = ?
            WHERE username = ?
        """, model.userCredit, model.username
        )

        db.execute(
            """
            DELETE FROM app_user_book_rental_current
            WHERE username = ?
        """, model.username
        )

        model.currentRentalList.forEach { entry ->
            db.execute(
                """
            INSERT INTO app_user_book_rental_current(`username`, `book_id`, `rented_at`, `rent_until`)
            VALUES (?, ?, ?, ?)
        """, model.username, entry.bookId, entry.rentedAt, entry.rentUntil
            )
        }

        model.rentalHistory.forEach { entry ->
            db.execute(
                """
            INSERT INTO app_user_book_rental_history(`username`, `book_id`, `rented_at`, `rent_until`)
            VALUES (?, ?, ?, ?)
        """, model.username, entry.bookId, entry.rentedAt, entry.rentUntil
            )
        }
    }
}
