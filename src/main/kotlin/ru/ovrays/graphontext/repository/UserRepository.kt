package ru.ovrays.graphontext.repository

import ru.ovrays.graphontext.model.user.User
import ru.tinkoff.kora.database.common.annotation.Query
import ru.tinkoff.kora.database.common.annotation.Repository
import ru.tinkoff.kora.database.jdbc.JdbcRepository
import java.time.OffsetDateTime
import java.util.*

@Repository
interface UserRepository : JdbcRepository {
    @Query(
        """
        SELECT * FROM "user"
        WHERE login = :login
    """
    )
    fun findUser(login: String): User?

    @Query("""
        SELECT EXISTS (
            SELECT 1 FROM "user"
            WHERE id = :id
        )
    """)
    fun isUserExists(id: UUID): Boolean

    @Query(
        """
        INSERT INTO "user" (id, login, role, updated_at, created_at)
        VALUES (uuid_generate_v4(), :login, 'user', :now, :now)
        RETURNING *
    """
    )
    fun createUser(login: String, now: OffsetDateTime): User
}
