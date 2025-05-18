package ru.ovrays.graphontext.repository

import ru.ovrays.graphontext.model.Product
import ru.ovrays.graphontext.model.yaMarket.YaMarketProduct
import ru.tinkoff.kora.database.common.annotation.Batch
import ru.tinkoff.kora.database.common.annotation.Query
import ru.tinkoff.kora.database.common.annotation.Repository
import ru.tinkoff.kora.database.jdbc.JdbcRepository
import java.time.OffsetDateTime

@Repository
interface ProductRepository : JdbcRepository {
    @Query(
        """
        WITH visualizations AS (
            SELECT product_id
            FROM product_visualization
            GROUP BY 1
        )
        SELECT
            id,
            name,
            created_at,
            updated_at,
            EXISTS(SELECT 1 FROM visualizations WHERE visualizations.product_id = product_id) as has_visualization
        FROM product
        WHERE shop_id = :shopId
    """
    )
    fun getProducts(shopId: Long): List<Product>

    @Query(
        """
        INSERT INTO product (id, shop_id, name, created_at, updated_at, has_visualization)
        VALUES (:products.id, :shopId, :products.name, :now, :now, false)
    """
    )
    fun createProducts(shopId: Long, @Batch products: List<YaMarketProduct>, now: OffsetDateTime)
}
