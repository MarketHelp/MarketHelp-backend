package ru.ovrays.graphontext.repository

import ru.ovrays.graphontext.model.user.UserAuth
import ru.tinkoff.kora.database.common.annotation.Query
import ru.tinkoff.kora.database.common.annotation.Repository
import ru.tinkoff.kora.database.jdbc.JdbcRepository
import java.time.OffsetDateTime
import java.util.*

@Repository
interface UserAuthRepository : JdbcRepository {
    @Query("""
        SELECT * FROM user_auth
        WHERE user_id = :userId
    """)
    fun getUserAuth(userId: UUID): UserAuth

    @Query(
        """
          INSERT INTO user_auth (user_id, password, updated_at, created_at)
          VALUES (:userId, :password, :now, :now)
    """
    )
    fun createUserAuth(userId: UUID, password: String, now: OffsetDateTime)
}
