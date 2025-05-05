package ru.ovrays.graphontext.service.mapper

import ru.ovrays.graphontext.model.ProductVisualization
import ru.ovrays.graphontext.model.ProductVisualizationDto
import ru.tinkoff.kora.common.Component

@Component
class ProductVisualizationMapper {
    fun map(productVisualization: ProductVisualization): ProductVisualizationDto {
        return ProductVisualizationDto(
            id = productVisualization.id,
            format = productVisualization.format,
            updatedAt = productVisualization.updatedAt,
            createdAt = productVisualization.createdAt
        )
    }
}
