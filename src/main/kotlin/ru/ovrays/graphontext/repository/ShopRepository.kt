package ru.ovrays.graphontext.repository

import ru.ovrays.graphontext.model.Shop
import ru.tinkoff.kora.database.common.annotation.Query
import ru.tinkoff.kora.database.common.annotation.Repository
import ru.tinkoff.kora.database.jdbc.JdbcRepository
import java.time.OffsetDateTime
import java.util.*

@Repository
interface ShopRepository : JdbcRepository {
    @Query(
        """
        INSERT INTO shop (id, user_id, name, updated_at, created_at)
        VALUES (:shopId, :userId, :name, :now, :now)
        RETURNING *
    """
    )
    fun createShop(shopId: Long, userId: UUID, name: String, now: OffsetDateTime): Shop

    @Query(
        """
        UPDATE shop
        SET name = :name
        WHERE id = :shopId
        RETURNING *
    """
    )
    fun updateShop(shopId: Long, name: String): Shop

    @Query(
        """
        SELECT EXISTS(
            SELECT 1 FROM shop
            WHERE user_id = :userId AND name = :name
        )
    """
    )
    fun isShopExists(userId: UUID, name: String): Boolean

    @Query(
        """
        SELECT EXISTS(
            SELECT 1 FROM shop
            WHERE user_id = :userId AND id = :shopId
        )
    """
    )
    fun isShopExists(userId: UUID, shopId: Long): Boolean

    @Query(
        """
        SELECT * FROM shop
        WHERE user_id = :userId
    """
    )
    fun getShops(userId: UUID): List<Shop>

    @Query(
        """
        DELETE FROM shop
        WHERE id = :shopId
    """
    )
    fun deleteShop(shopId: Long)
}
