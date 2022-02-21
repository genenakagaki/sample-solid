package com.genenakagaki.sample.solid.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class UserRepository(val db: DSLContext) {

    fun selectByUsername(username: String): Map<String, String> {
        val record = db.fetch(
            """
            SELECT username, role FROM app_user 
            WHERE username = ?
        """.trimIndent(), username
        )[0]

        return mapOf(
            "username" to record.get("username") as String,
            "role" to record.get("role") as String
        )

    }
}
