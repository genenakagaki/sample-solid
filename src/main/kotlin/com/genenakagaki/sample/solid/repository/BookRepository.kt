package com.genenakagaki.sample.solid.repository

import com.genenakagaki.sample.solid.core.BookContent
import com.genenakagaki.sample.solid.core.BookPrice
import com.genenakagaki.sample.solid.int
import com.genenakagaki.sample.solid.str
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class BookRepository(
    private val db: DSLContext
) {

    fun findPriceById(bookId: Int): BookPrice? {
        return db.fetch(
            """
                SELECT rent_price, buy_price FROM book_price
                WHERE book_id = ?
            """, bookId
        ).firstOrNull()?.map { data ->
            BookPrice(data.get("rent_price").int(), data.get("buy_price").int())
        }
    }

    fun findContentById(bookId: Int): BookContent? {
        return db.fetch(
            """
                    SELECT book_id, title, content FROM book
                    WHERE book_id = ?
                """, bookId
        ).firstOrNull()?.map { data ->
            BookContent(
                data.get("title").str(),
                data.get("content").str(),
            )
        }
    }
}
