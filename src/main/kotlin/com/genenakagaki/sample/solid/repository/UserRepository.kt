package com.genenakagaki.sample.solid.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val db: DSLContext
) {

    // ユーザーデータ
    // {
    //   username: "g-nakagaki",
    //   email: "g-nakagaki@email.com",
    //   role: "USER",
    //   credit: "1000"
    // }
    fun findByUsername(username: Any?) =
        db.fetch(
            """
                SELECT username, email, role, credit FROM app_user 
                WHERE username = ?
            """, username
        ).intoMaps().first()

}
