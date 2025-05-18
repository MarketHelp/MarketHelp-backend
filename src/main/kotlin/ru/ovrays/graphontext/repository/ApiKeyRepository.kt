package ru.ovrays.graphontext.repository

import ru.tinkoff.kora.database.common.annotation.Query
import ru.tinkoff.kora.database.common.annotation.Repository
import ru.tinkoff.kora.database.jdbc.JdbcRepository
import java.time.OffsetDateTime

@Repository
interface ApiKeyRepository : JdbcRepository {
    @Query("""
        SELECT value FROM api_key
        WHERE shop_id = :shopId
    """)
    fun getApiKey(shopId: Long): String

    @Query(
        """
        INSERT INTO api_key (shop_id, value, valid_until, created_at)
        VALUES (:shopId, :value, :validUntil, :now)
    """
    )
    fun createApiKey(shopId: Long, value: String, now: OffsetDateTime, validUntil: OffsetDateTime)
    @Query(
        """
        DELETE FROM api_key
        WHERE shop_id = :shopId
    """
    )
    fun deleteApiKey(shopId: Long)
}
