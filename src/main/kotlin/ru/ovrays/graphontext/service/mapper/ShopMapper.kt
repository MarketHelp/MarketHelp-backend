package ru.ovrays.graphontext.service.mapper

import ru.ovrays.graphontext.model.Shop
import ru.ovrays.graphontext.model.ShopDto
import ru.tinkoff.kora.common.Component

@Component
class ShopMapper {
    fun map(shop: Shop): ShopDto {
        return ShopDto(
            id = shop.id,
            name = shop.name,
            updatedAt = shop.updatedAt,
            createdAt = shop.createdAt
        )
    }
}
