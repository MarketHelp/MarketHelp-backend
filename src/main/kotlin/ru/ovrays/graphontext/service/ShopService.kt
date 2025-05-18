package ru.ovrays.graphontext.service

import ru.ovrays.graphontext.client.YaMarketClient
import ru.ovrays.graphontext.model.Shop
import ru.ovrays.graphontext.repository.ShopRepository
import ru.ovrays.graphontext.service.util.ClockService
import ru.tinkoff.kora.common.Component
import java.util.*

@Component
class ShopService(
    private val shopRepository: ShopRepository,
    private val yaMarketClient: YaMarketClient,
    private val clockService: ClockService,
) {
    fun createShop(userId: UUID, apiKey: String, name: String): Shop {
        val now = clockService.now()
        val shopId = yaMarketClient.getShopId(apiKey)

        return shopRepository.createShop(shopId, userId, name, now)
    }

    fun isShopExists(userId: UUID, name: String): Boolean {
        return shopRepository.isShopExists(userId, name)
    }

    fun isShopExists(userId: UUID, shopId: Long): Boolean {
        return shopRepository.isShopExists(userId, shopId)
    }

    fun getShops(userId: UUID): List<Shop> {
        return shopRepository.getShops(userId)
    }

    fun updateShop(shopId: Long, name: String): Shop {
        return shopRepository.updateShop(shopId, name)
    }

    fun deleteShop(shopId: Long) {
        shopRepository.deleteShop(shopId)
    }
}
