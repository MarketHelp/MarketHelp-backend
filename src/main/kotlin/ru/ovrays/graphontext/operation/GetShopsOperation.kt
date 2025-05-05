package ru.ovrays.graphontext.operation

import ru.ovrays.graphontext.api.ShopApiResponses.GetShopsApiResponse
import ru.ovrays.graphontext.api.ShopApiResponses.GetShopsApiResponse.GetShops200ApiResponse
import ru.ovrays.graphontext.service.ShopService
import ru.ovrays.graphontext.service.mapper.ShopMapper
import ru.ovrays.graphontext.service.user.UserAuthService
import ru.tinkoff.kora.common.Component

@Component
open class GetShopsOperation(
    private val userAuthService: UserAuthService,
    private val shopService: ShopService,
    private val shopMapper: ShopMapper
) {
    open fun activate(): GetShopsApiResponse {
        val userId = userAuthService.getCurrentUserId()
        val shops = shopService.getShops(userId)
            .map(shopMapper::map)

        return GetShops200ApiResponse(shops)
    }
}
