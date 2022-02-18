package com.genenakagaki.sample.solid.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class UserRepository(val db: DSLContext) {

    fun selectByUsername(username: String): Map<String, String> {
        val result = db.fetch(
            """
            SELECT username, role FROM app_user 
            WHERE username = ?
        """.trimIndent(), username
        )[0]

        return mapOf(
            "username" to result.get("username") as String,
            "role" to result.get("role") as String
        )
    }
}
