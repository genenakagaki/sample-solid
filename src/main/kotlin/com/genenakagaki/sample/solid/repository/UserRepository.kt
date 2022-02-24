package com.genenakagaki.sample.solid.repository

import com.genenakagaki.sample.solid.core.AppUser
import com.genenakagaki.sample.solid.int
import com.genenakagaki.sample.solid.str
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val db: DSLContext
) {

    fun findByUsername(username: String): AppUser? {
        return db.fetch(
            """
                    SELECT username, email, role, credit FROM app_user 
                    WHERE username = ?
                """, username
        ).firstOrNull()?.map { data ->
            AppUser(
                data.get("username").str(),
                data.get("email").str(),
                data.get("role").str(),
                data.get("credit").int(),
            )
        }
    }

}
