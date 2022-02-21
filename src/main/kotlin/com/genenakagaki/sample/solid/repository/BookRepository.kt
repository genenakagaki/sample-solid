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

    fun selectInventory(bookId: Int, state: String): List<Map<String, String>> {
        val recordList = db.fetch(
            """
            SELECT book_inventory_id, book_id, book_inventory_state FROM book_inventory
            WHERE book_id = ?
            AND book_inventory_state = ?
        """.trimIndent(), bookId, state
        )

        return recordList.map {
            mapOf(
                "book_inventory_id" to it.get("book_inventory_id") as String,
                "book_id" to it.get("book_id") as String,
                "book_inventory_state" to it.get("book_inventory_state") as String
            )
        }
    }

    fun selectInventoryRented(username: String): List<Map<String, String>> {
        val recordList = db.fetch(
            """
                    SELECT book_inventory_id, username, return_due_at FROM book_inventory_rented
                    WHERE username = ?
                """.trimIndent(), username
        )

        return recordList.map {
            mapOf(
                "book_inventory_id" to it.get("book_inventory_id") as String,
                "username" to it.get("username") as String,
                "return_due_at" to it.get("return_due_at") as String
            )
        }
    }

    fun insertRentData(bookInventoryId: Int, username: String, returnDueAt: LocalDateTime) {
        db.execute(
            """
                INSERT INTO book_inventory_rented(`book_inventory_id`, `username`, `return_due_at`)
                VALUES (?, ?, ?)
        """.trimIndent(), bookInventoryId, username, returnDueAt
        )

        db.execute(
            """
                INSERT INTO book_inventory_rent_history(`book_inventory_id`, `username`, `return_due_at`)
                VALUES (?, ?, ?)
        """.trimIndent(), bookInventoryId, username, returnDueAt
        )

        db.execute(
            """
                UPDATE book_inventory 
                SET book_inventory_state = 'RENTED'
                WHERE book_inventory_id = ?
        """.trimIndent(), bookInventoryId
        )
    }

    fun insertReturnData(bookInventoryId: Int, username: String) {
        db.execute(
            """
                INSERT INTO book_inventory_return_history(`book_inventory_id`, `username`)
                VALUES (?, ?)
        """.trimIndent(), bookInventoryId, username
        )

        db.execute(
            """
            DELETE FROM book_inventory_rented
            WHERE book_inventory_id = ?
        """.trimIndent(), bookInventoryId
        )

        db.execute(
            """
                UPDATE book_inventory 
                SET book_inventory_state = 'AVAILABLE'
                WHERE book_inventory_id = ?
        """.trimIndent(), bookInventoryId
        )
    }
}
