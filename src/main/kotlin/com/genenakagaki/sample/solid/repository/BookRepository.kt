package com.genenakagaki.sample.solid.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class BookRepository(
    private val db: DSLContext
) {

    // レンタルしたい本の金額
    // {
    //   rent_price: 100,
    //   buy_price: 1000
    // }
    fun findPriceById(bookId: Any?) =
        db.fetch(
            """
            SELECT rent_price, buy_price FROM book_price
            WHERE book_id = ?
        """, bookId
        ).intoMaps().first()

    fun findContentById(bookId: Any?) =
        db.fetch(
            """
                SELECT book_id, title, content FROM book
                WHERE book_id = ?
            """, bookId
        ).intoMaps().first()
}
