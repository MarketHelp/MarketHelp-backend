package ru.ovrays.graphontext.service.mapper

import ru.ovrays.graphontext.model.Product
import ru.ovrays.graphontext.model.ProductDto
import ru.tinkoff.kora.common.Component

@Component
class ProductMapper {
    fun map(product: Product): ProductDto {
        return ProductDto(
            id = product.id,
            name = product.name,
            updatedAt = product.updatedAt,
            createdAt = product.createdAt,
            hasVisualization = product.hasVisualization
        )
    }
}
