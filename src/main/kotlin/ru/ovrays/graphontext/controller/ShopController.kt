package ru.ovrays.graphontext.controller

import ru.ovrays.graphontext.api.ShopApiDelegate
import ru.ovrays.graphontext.api.ShopApiResponses.CreateShopApiResponse
import ru.ovrays.graphontext.api.ShopApiResponses.DeleteShopApiResponse
import ru.ovrays.graphontext.api.ShopApiResponses.GetShopsApiResponse
import ru.ovrays.graphontext.api.ShopApiResponses.UpdateShopApiResponse
import ru.ovrays.graphontext.model.CreateShopRequestDto
import ru.ovrays.graphontext.model.UpdateShopRequestDto
import ru.ovrays.graphontext.operation.CreateShopOperation
import ru.ovrays.graphontext.operation.DeleteShopOperation
import ru.ovrays.graphontext.operation.GetShopsOperation
import ru.ovrays.graphontext.operation.UpdateShopOperation
import ru.tinkoff.kora.common.Component

@Component
class ShopController(
    private val getShopsOperation: GetShopsOperation,
    private val createShopOperation: CreateShopOperation,
    private val updateShopOperation: UpdateShopOperation,
    private val deleteShopOperation: DeleteShopOperation
) : ShopApiDelegate {
    override fun createShop(
        createShopRequestDto: CreateShopRequestDto
    ): CreateShopApiResponse {
        return createShopOperation.activate(createShopRequestDto.name, createShopRequestDto.apiKey)
    }

    override fun getShops(): GetShopsApiResponse {
        return getShopsOperation.activate()
    }

    override fun updateShop(
        shopId: Long,
        updateShopRequestDto: UpdateShopRequestDto
    ): UpdateShopApiResponse {
        return updateShopOperation.activate(shopId, updateShopRequestDto.name)
    }

    override fun deleteShop(shopId: Long): DeleteShopApiResponse {
        return deleteShopOperation.activate(shopId)
    }
}
