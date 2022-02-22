package com.genenakagaki.sample.solid.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class BookRepository(val db: DSLContext) {

    fun insert(bookTitle: String) {
        db.execute(
            """
            INSERT INTO book (title) 
            VALUES (?)
        """, bookTitle
        )
    }

    fun findBookPrice(bookId: Int): MutableMap<String, String>? {
        val bookPriceRecord = db.fetch(
            """
            SELECT rent_price, buy_price FROM book_price
            WHERE book_id = ?
        """.trimIndent(), bookId
        ).firstOrNull() ?: return null

        return bookPriceRecord.map {
            mutableMapOf(
                "rent_price" to it.get("rent_price") as String,
                "buy_price" to it.get("buy_price") as String,
            )
        }
    }

    fun findCurrentRentalList(username: String): MutableList<MutableMap<String, String>> {
        return db.fetch(
            """
            SELECT book_id, rented_at, rent_until FROM app_user_book_rental_current
            WHERE username = ?
        """.trimIndent(), username
        ).map {
            mutableMapOf(
                "book_id" to it.get("book_id") as String,
                "rented_at" to it.get("rented_at") as String,
                "rent_until" to it.get("rent_until") as String
            )
        }
    }

    fun insertRentalData(rentalData: Map<String, String>) {
        db.execute(
            """
            INSERT INTO app_user_book_rental_current(`username`, `book_id`, `rented_at`, `rent_until`)
            VALUES (?, ?, ?, ?)
        """.trimIndent(),
            rentalData["username"],
            rentalData["book_id"],
            rentalData["rented_at"],
            rentalData["rent_until"]
        )

        db.execute(
            """
            INSERT INTO app_user_book_rental_history(`username`, `book_id`, `rented_at`, `rent_until`)
            VALUES (?, ?, ?, ?)
        """.trimIndent(),
            rentalData["username"],
            rentalData["book_id"],
            rentalData["rented_at"],
            rentalData["rent_until"]
        )
    }
}
