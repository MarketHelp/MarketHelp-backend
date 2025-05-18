package ru.ovrays.graphontext.operation

import ru.ovrays.graphontext.api.ShopApiResponses.UpdateShopApiResponse
import ru.ovrays.graphontext.api.ShopApiResponses.UpdateShopApiResponse.UpdateShop200ApiResponse
import ru.ovrays.graphontext.model.exception.BusinessException
import ru.ovrays.graphontext.model.exception.BusinessExceptionCode.SHOP_NOT_FOUND
import ru.ovrays.graphontext.service.ShopService
import ru.ovrays.graphontext.service.mapper.ShopMapper
import ru.ovrays.graphontext.service.user.UserAuthService
import ru.tinkoff.kora.common.Component

@Component
class UpdateShopOperation(
    private val shopMapper: ShopMapper,
    private val shopService: ShopService,
    private val userAuthService: UserAuthService
) {
    fun activate(shopId: Long, name: String): UpdateShopApiResponse {
        val userId = userAuthService.getCurrentUserId()
        val isShopExists = shopService.isShopExists(userId, shopId)

        if (!isShopExists) {
            throw BusinessException(SHOP_NOT_FOUND)
        }

        val shop = shopService.updateShop(shopId, name)
        val shopDto = shopMapper.map(shop)

        return UpdateShop200ApiResponse(shopDto)
    }
}
