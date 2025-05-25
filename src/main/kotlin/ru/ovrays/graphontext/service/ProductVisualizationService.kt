package ru.ovrays.graphontext.service

import ru.ovrays.graphontext.model.ProductVisualization
import ru.ovrays.graphontext.model.exception.BusinessException
import ru.ovrays.graphontext.model.exception.BusinessExceptionCode.PRODUCT_VISUALIZATION_NOT_FOUND
import ru.ovrays.graphontext.repository.ProductVisualizationRepository
import ru.ovrays.graphontext.service.util.ClockService
import ru.tinkoff.kora.common.Component
import java.util.*

@Component
class ProductVisualizationService(
    private val clockService: ClockService,
    private val productVisualizationRepository: ProductVisualizationRepository
) {
    fun getProductVisualizations(productId: UUID): List<ProductVisualization> {
        return productVisualizationRepository.getProductVisualizations(productId)
    }

    fun getProductVisualization(productVisualizationId: UUID): ProductVisualization {
        return productVisualizationRepository.findProductVisualization(productVisualizationId)
            ?: throw BusinessException(PRODUCT_VISUALIZATION_NOT_FOUND)
    }

    fun createProductVisualization(productId: UUID, filePath: String, format: String): ProductVisualization {
        val id = UUID.randomUUID()
        val now = clockService.now()

        return productVisualizationRepository.createProductVisualization(id, productId, format, filePath, now)
    }
}
