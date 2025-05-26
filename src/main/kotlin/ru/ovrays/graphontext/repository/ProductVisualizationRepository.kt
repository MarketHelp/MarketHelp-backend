package ru.ovrays.graphontext.repository

import ru.ovrays.graphontext.model.ProductVisualization
import ru.tinkoff.kora.database.common.annotation.Query
import ru.tinkoff.kora.database.common.annotation.Repository
import ru.tinkoff.kora.database.jdbc.JdbcRepository
import java.time.OffsetDateTime
import java.util.*

@Repository
interface ProductVisualizationRepository : JdbcRepository {
    @Query(
        """
        INSERT INTO product_visualization (id, product_id, format, filepath, created_at, updated_at)
        VALUES (:id, :productId, :format, :filepath, :now, :now)
        ON CONFLICT (product_id, format, filepath) 
            DO UPDATE SET updated_at = :now
        RETURNING *
    """
    )
    fun createProductVisualization(
        id: UUID,
        productId: UUID,
        format: String,
        filepath: String,
        now: OffsetDateTime
    ): ProductVisualization

    @Query(
        """
        SELECT * FROM product_visualization
        WHERE product_id = :productId
    """
    )
    fun getProductVisualizations(productId: UUID): List<ProductVisualization>

    @Query(
        """
        SELECT * FROM product_visualization
        WHERE id = :productVisualizationId
    """
    )
    fun findProductVisualization(productVisualizationId: UUID): ProductVisualization?
}
