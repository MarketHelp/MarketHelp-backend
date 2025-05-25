package ru.ovrays.graphontext.operation

import ru.ovrays.graphontext.api.ShopApiResponses.CreateShopApiResponse
import ru.ovrays.graphontext.api.ShopApiResponses.CreateShopApiResponse.CreateShop200ApiResponse
import ru.ovrays.graphontext.model.exception.BusinessException
import ru.ovrays.graphontext.model.exception.BusinessExceptionCode.SHOP_ALREADY_EXISTS
import ru.ovrays.graphontext.service.ApiKeyService
import ru.ovrays.graphontext.service.ProductService
import ru.ovrays.graphontext.service.ShopService
import ru.ovrays.graphontext.service.mapper.ShopMapper
import ru.ovrays.graphontext.service.user.UserAuthService
import ru.tinkoff.kora.common.Component

@Component
class CreateShopOperation(
    private val shopMapper: ShopMapper,
    private val shopService: ShopService,
    private val productService: ProductService,
    private val apiKeyService: ApiKeyService,
    private val userAuthService: UserAuthService
) {
    fun activate(name: String, apiKey: String): CreateShopApiResponse {
        val userId = userAuthService.getCurrentUserId()
        val isShopExists = shopService.isShopExists(userId, name)

        if (isShopExists) {
            throw BusinessException(SHOP_ALREADY_EXISTS)
        }

        val shop = shopService.createShop(userId, apiKey, name)
        val shopDto = shopMapper.map(shop)

        apiKeyService.createApiKey(shop.id, apiKey)
        productService.syncProducts(shop.id, apiKey)

        return CreateShop200ApiResponse(shopDto)
    }
}
