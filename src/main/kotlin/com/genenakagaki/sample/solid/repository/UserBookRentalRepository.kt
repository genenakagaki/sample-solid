package com.genenakagaki.sample.solid.repository

import com.genenakagaki.sample.solid.list
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class UserBookRentalRepository(
    private val db: DSLContext,
    private val userRepository: UserRepository
) {

    /**
     * userBookRentalData
     * {
     *   username: "g-nakagaki",
     *   userCredit: 500,
     *   currentRentalList: [
     *     {
     *       book_id: "1",
     *       rented_at: "2022-01-01",
     *       rent_until: "2022-01-08"
     *     }
     *   ],
     *   rentalHistory: [
     *     {
     *       book_id: "1",
     *       rented_at: "2022-01-01",
     *       rent_until: "2022-01-08"
     *     }
     *   ]
     * }
     */
    fun findByUsername(username: Any?): MutableMap<String, Any?>? {
        val userData = userRepository.findByUsername(username)

        if (userData == null) {
            return null;
        }

        val currentRentalList = db.fetch(
            """
            SELECT book_id, rented_at, rent_until FROM app_user_book_rental_current
            WHERE username = ?
            """, username
        ).intoMaps()

        val rentalHistory = db.fetch(
            """
            SELECT book_id, rented_at, rent_until FROM app_user_book_rental_history
            WHERE username = ?
        """, username
        ).intoMaps()

        return mutableMapOf(
            "username" to username,
            "userCredit" to userData.get("credit"),
            "currentRentalList" to currentRentalList,
            "rentalHistory" to rentalHistory
        )
    }

    fun save(userBookRentalData: MutableMap<String, Any?>) {
        db.execute(
            """
            UPDATE app_user
            SET credit = ?
            WHERE username = ?
        """, userBookRentalData["userCredit"], userBookRentalData["username"]
        )

        db.execute(
            """
            DELETE FROM app_user_book_rental_current
            WHERE username = ?
        """, userBookRentalData["username"]
        )

        userBookRentalData["currentRentalList"].list().forEach { entry ->
            db.execute(
                """
            INSERT INTO app_user_book_rental_current(`username`, `book_id`, `rented_at`, `rent_until`)
            VALUES (?, ?, ?, ?)
        """, entry["username"], entry["bookId"], entry["rentedAt"], entry["rentUntil"]
            )
        }

        userBookRentalData["rentalHistory"].list().forEach { entry ->
            db.execute(
                """
            INSERT INTO app_user_book_rental_history(`username`, `book_id`, `rented_at`, `rent_until`)
            VALUES (?, ?, ?, ?)
        """, entry["username"], entry["bookId"], entry["rentedAt"], entry["rentUntil"]
            )
        }
    }
}
