package ru.ovrays.graphontext.operation

import ru.ovrays.graphontext.api.ProductApiResponses.SyncProductsApiResponse
import ru.ovrays.graphontext.api.ProductApiResponses.SyncProductsApiResponse.SyncProducts200ApiResponse
import ru.ovrays.graphontext.model.exception.BusinessException
import ru.ovrays.graphontext.model.exception.BusinessExceptionCode.SHOP_NOT_FOUND
import ru.ovrays.graphontext.service.ApiKeyService
import ru.ovrays.graphontext.service.ProductService
import ru.ovrays.graphontext.service.ShopService
import ru.ovrays.graphontext.service.mapper.ProductMapper
import ru.ovrays.graphontext.service.user.UserAuthService
import ru.tinkoff.kora.common.Component

@Component
class SyncProductsOperation(
    private val shopService: ShopService,
    private val productMapper: ProductMapper,
    private val productService: ProductService,
    private val apiKeyService: ApiKeyService,
    private val userAuthService: UserAuthService
) {
    fun activate(shopId: Long): SyncProductsApiResponse {
        val userId = userAuthService.getCurrentUserId()
        val isShopExists = shopService.isShopExists(userId, shopId)

        if (!isShopExists) {
            throw BusinessException(SHOP_NOT_FOUND)
        }

        val apiKey = apiKeyService.getApiKey(shopId)
        val products = productService.syncProducts(shopId, apiKey)
            .map(productMapper::map)

        return SyncProducts200ApiResponse(products)
    }
}
