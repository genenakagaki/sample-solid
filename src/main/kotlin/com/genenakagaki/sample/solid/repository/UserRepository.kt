package com.genenakagaki.sample.solid.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class UserRepository(val db: DSLContext) {

    /**
     * {
     *     username: "g-nakagaki",
     *     role: "USER",
     *     credit: "1000"
     * }
     */
    fun findByUsername(username: String): MutableMap<String, String>? {
        val record = db.fetch(
            """
            SELECT username, role, credit FROM app_user 
            WHERE username = ?
        """.trimIndent(), username
        ).firstOrNull() ?: return null

        return mutableMapOf(
            "username" to record.get("username") as String,
            "role" to record.get("role") as String,
            "credit" to record.get("credit") as String,
        )
    }

    fun updateUserCredit(username: String, userCredit: Int) {
        db.execute(
            """
            UPDATE app_user
            SET credit = ?
            WHERE username = ?
        """.trimIndent(), userCredit, username
        )
    }
}
