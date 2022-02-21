package com.genenakagaki.sample.solid.repository

import com.genenakagaki.sample.solid.domain.user.AppUserEntity
import com.genenakagaki.sample.solid.domain.user.UserRoleType
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

interface UserRepository {

    fun findByUsername(username: String): AppUserEntity?
}

@Repository
class UserRepositoryImpl(
    private val db: DSLContext
) : UserRepository {

    override fun findByUsername(username: String): AppUserEntity? {
        val record = db.fetch(
            """
            SELECT username, role FROM app_user
            WHERE username = ?
        """.trimIndent(), username
        ).firstOrNull() ?: return null

        return record.map {
            AppUserEntity(
                it.get("username") as String,
                UserRoleType.valueOf(it.get("role") as String)
            )
        }
    }
}
