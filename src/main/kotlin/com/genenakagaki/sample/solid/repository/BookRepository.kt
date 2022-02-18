package com.genenakagaki.sample.solid.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class BookRepository(val db: DSLContext) {

    fun insert(bookTitle: String) {
        db.execute(
            """
            INSERT INTO book (title) 
            VALUES (?)
        """, bookTitle)
    }
}
