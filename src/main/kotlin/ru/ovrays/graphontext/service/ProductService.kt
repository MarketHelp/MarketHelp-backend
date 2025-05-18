package ru.ovrays.graphontext.service

import ru.ovrays.graphontext.client.YaMarketClient
import ru.ovrays.graphontext.model.Product
import ru.ovrays.graphontext.repository.ProductRepository
import ru.ovrays.graphontext.service.util.ClockService
import ru.tinkoff.kora.common.Component

@Component
class ProductService(
    private val clockService: ClockService,
    private val yaMarketClient: YaMarketClient,
    private val productRepository: ProductRepository
) {
    fun getProducts(shopId: Long): List<Product> {
        return productRepository.getProducts(shopId)
    }

    fun syncProducts(shopId: Long, apiKey: String): List<Product> {
        val now = clockService.now()
        val products = yaMarketClient.getProducts(apiKey, shopId)

        productRepository.createProducts(shopId, products, now)

        return productRepository.getProducts(shopId)
    }
}
