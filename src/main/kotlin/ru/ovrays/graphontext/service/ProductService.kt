package ru.ovrays.graphontext.service

import ru.ovrays.graphontext.model.Product
import ru.ovrays.graphontext.repository.ProductRepository
import ru.tinkoff.kora.common.Component
import java.util.*

@Component
class ProductService(
    private val productRepository: ProductRepository
) {
    fun getProducts(shopId: UUID): List<Product> {
        return productRepository.getProducts(shopId)
    }

    fun syncProducts(shopId: UUID): List<Product> {
        // TODO: YMarket synchronization

        return productRepository.getProducts(shopId)
    }
}
