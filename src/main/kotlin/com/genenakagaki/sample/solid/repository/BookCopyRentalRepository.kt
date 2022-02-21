package com.genenakagaki.sample.solid.repository

import com.genenakagaki.sample.solid.domain.book.BookCopyRentalEntity
import com.genenakagaki.sample.solid.domain.book.BookCopyRentalHistoryItem
import com.genenakagaki.sample.solid.domain.book.BookCopyStateType
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

interface BookCopyRentalRepository {

    fun findById(bookCopyId: Int): BookCopyRentalEntity?
    fun findWithCondition(bookId: Int, state: BookCopyStateType): BookCopyRentalEntity?
    fun save(entity: BookCopyRentalEntity)
}

@Repository
class BookCopyRentalRepositoryImpl(
    private val db: DSLContext,
) : BookCopyRentalRepository {

    override fun findById(bookCopyId: Int): BookCopyRentalEntity? {
        val record = db.fetch(
            """
                    SELECT book_copy_id, book_copy_state FROM book_copy
                    WHERE book_copy_id = ?
            """.trimIndent(), bookCopyId
        ).firstOrNull() ?: return null

        val rentalHistoryRecordList = db.fetch(
            """
            SELECT rental_history.username,
                   rental_history.return_due_at,
                   rental_history.rented_at,
                   return_history.returned_at
            FROM book_copy_rental_history AS rental_history
                 LEFT JOIN book_copy_return_history AS return_history
                           ON return_history.book_copy_rental_history_id = rental_history.book_copy_rental_history_id
            WHERE book_copy_id = ?
        """.trimIndent(), bookCopyId
        )

        return BookCopyRentalEntity(
            bookCopyId,
            BookCopyStateType.valueOf(record.get("book_copy_state") as String),
            rentalHistoryRecordList.map {
                BookCopyRentalHistoryItem(
                    bookCopyId,
                    it.get("rented_at") as LocalDateTime,
                    it.get("username") as String,
                    it.get("return_due_date") as LocalDateTime,
                    it.get("returned_at") as LocalDateTime?
                )
            }
        )
    }

    override fun findWithCondition(bookId: Int, state: BookCopyStateType): BookCopyRentalEntity? {
        val record = db.fetch(
            """
                SELECT book_copy_id FROM book_copy
                WHERE book_id = ?
                AND book_copy_state = ?
            """.trimIndent(), bookId, state
        ).firstOrNull() ?: return null

        return findById(record["book_copy_id"] as Int)
    }

    override fun save(entity: BookCopyRentalEntity) {
        db.execute(
            """
            UPDATE book_copy
            SET book_copy_state = ?
            WHERE book_copy_id = ?
        """.trimIndent(), entity.state.name, entity.bookCopyId
        )

        db.execute(
            """
            DELETE FROM book_copy_rental_history
            WHERE book_copy_id = ?
        """.trimIndent(), entity.bookCopyId
        )

        entity.rentalHistory.forEach { historyItem ->
            db.execute(
                """
                INSERT INTO book_copy_rental_history(`book_copy_id`, `rented_at`, `username`, `return_due_at`, `returned_at`)
                VALUES (?, ?, ?, ?, ?)
            """.trimIndent(),
                historyItem.bookCopyId,
                historyItem.rentedAt,
                historyItem.username,
                historyItem.returnDueAt,
                historyItem.returnedAt
            )
        }
    }
}

