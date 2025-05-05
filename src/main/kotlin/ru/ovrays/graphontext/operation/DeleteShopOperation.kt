package ru.ovrays.graphontext.operation

import ru.ovrays.graphontext.api.ShopApiResponses.DeleteShopApiResponse
import ru.ovrays.graphontext.api.ShopApiResponses.DeleteShopApiResponse.DeleteShop200ApiResponse
import ru.ovrays.graphontext.model.exception.BusinessException
import ru.ovrays.graphontext.model.exception.BusinessExceptionCode.SHOP_NOT_FOUND
import ru.ovrays.graphontext.service.ApiKeyService
import ru.ovrays.graphontext.service.ShopService
import ru.ovrays.graphontext.service.user.UserAuthService
import ru.tinkoff.kora.common.Component
import java.util.*

@Component
class DeleteShopOperation(
    private val shopService: ShopService,
    private val apiKeyService: ApiKeyService,
    private val userAuthService: UserAuthService
) {
    fun activate(shopId: UUID): DeleteShopApiResponse {
        val userId = userAuthService.getCurrentUserId()
        val isShopExists = shopService.isShopExists(userId, shopId)

        if (!isShopExists) {
            throw BusinessException(SHOP_NOT_FOUND)
        }

        apiKeyService.deleteApiKey(shopId)
        shopService.deleteShop(shopId)

        return DeleteShop200ApiResponse()
    }
}