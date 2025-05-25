package ru.ovrays.graphontext.controller

import ru.ovrays.graphontext.api.ProductApiDelegate
import ru.ovrays.graphontext.api.ProductApiResponses.CreateProductVisualizationApiResponse
import ru.ovrays.graphontext.api.ProductApiResponses.GetProductVisualizationApiResponse
import ru.ovrays.graphontext.api.ProductApiResponses.GetProductVisualizationsApiResponse
import ru.ovrays.graphontext.api.ProductApiResponses.GetProductsApiResponse
import ru.ovrays.graphontext.api.ProductApiResponses.SyncProductsApiResponse
import ru.ovrays.graphontext.model.CreateProductVisualizationRequestDto
import ru.ovrays.graphontext.operation.CreateProductVisualization
import ru.ovrays.graphontext.operation.GetProductVisualizationOperation
import ru.ovrays.graphontext.operation.GetProductVisualizationsOperation
import ru.ovrays.graphontext.operation.GetProductsOperation
import ru.ovrays.graphontext.operation.SyncProductsOperation
import ru.tinkoff.kora.common.Component
import java.util.*

@Component
class ProductController(
    private val getProductsOperation: GetProductsOperation,
    private val syncProductsOperation: SyncProductsOperation,
    private val createProductVisualization: CreateProductVisualization,
    private val getProductVisualizationsOperation: GetProductVisualizationsOperation,
    private val getProductVisualizationOperation: GetProductVisualizationOperation
) : ProductApiDelegate {
    override fun getProducts(shopId: Long): GetProductsApiResponse {
        return getProductsOperation.activate(shopId)
    }

    override fun syncProducts(shopId: Long): SyncProductsApiResponse {
        return syncProductsOperation.activate(shopId)
    }

    override fun getProductVisualizations(
        shopId: Long,
        productId: UUID
    ): GetProductVisualizationsApiResponse {
        return getProductVisualizationsOperation.activate(shopId, productId)
    }

    override fun createProductVisualization(
        shopId: Long,
        productId: UUID,
        createProductVisualizationRequestDto: CreateProductVisualizationRequestDto
    ): CreateProductVisualizationApiResponse {
        return createProductVisualization.activate(shopId, productId, createProductVisualizationRequestDto)
    }

    override fun getProductVisualization(
        shopId: Long,
        productId: UUID,
        productVisualizationId: UUID
    ): GetProductVisualizationApiResponse {
        return getProductVisualizationOperation.activate(shopId, productVisualizationId)
    }
}
