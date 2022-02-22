package com.genenakagaki.sample.solid.repository

import com.genenakagaki.sample.solid.domain.book.BookPrice
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

interface BookPriceRepository {

    fun findByBookId(bookId: Int): BookPrice?
}

@Repository
class BookPriceRepositoryImpl(
    private val db: DSLContext
) : BookPriceRepository {

    override fun findByBookId(bookId: Int): BookPrice? {
        val bookPriceRecord = db.fetch(
            """
            SELECT rent_price, buy_price FROM book_price
            WHERE book_id = ?
        """.trimIndent(), bookId
        ).firstOrNull() ?: return null

        return BookPrice(
            rentPrice = bookPriceRecord.get("rent_price") as Int,
            buyPrice = bookPriceRecord.get("buy_price") as Int,
        )
    }
}


