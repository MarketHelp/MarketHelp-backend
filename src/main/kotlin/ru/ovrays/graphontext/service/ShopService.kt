package ru.ovrays.graphontext.service

import ru.ovrays.graphontext.model.Shop
import ru.ovrays.graphontext.repository.ShopRepository
import ru.ovrays.graphontext.service.util.ClockService
import ru.tinkoff.kora.common.Component
import java.util.*

@Component
class ShopService(
    private val shopRepository: ShopRepository,
    private val clockService: ClockService,
) {
    fun createShop(userId: UUID, name: String): Shop {
        val id = UUID.randomUUID();
        val now = clockService.now()

        return shopRepository.createShop(id, userId, name, now)
    }

    fun isShopExists(userId: UUID, name: String): Boolean {
        return shopRepository.isShopExists(userId, name)
    }

    fun isShopExists(userId: UUID, shopId: UUID): Boolean {
        return shopRepository.isShopExists(userId, shopId)
    }

    fun getShops(userId: UUID): List<Shop> {
        return shopRepository.getShops(userId)
    }

    fun updateShop(shopId: UUID, name: String): Shop {
        return shopRepository.updateShop(shopId, name)
    }

    fun deleteShop(shopId: UUID) {
        shopRepository.deleteShop(shopId)
    }
}
