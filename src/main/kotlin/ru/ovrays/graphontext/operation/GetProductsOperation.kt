package ru.ovrays.graphontext.operation

import ru.ovrays.graphontext.api.ProductApiResponses.GetProductsApiResponse
import ru.ovrays.graphontext.api.ProductApiResponses.GetProductsApiResponse.GetProducts200ApiResponse
import ru.ovrays.graphontext.model.exception.BusinessException
import ru.ovrays.graphontext.model.exception.BusinessExceptionCode.SHOP_NOT_FOUND
import ru.ovrays.graphontext.service.ProductService
import ru.ovrays.graphontext.service.ShopService
import ru.ovrays.graphontext.service.mapper.ProductMapper
import ru.ovrays.graphontext.service.user.UserAuthService
import ru.tinkoff.kora.common.Component
import java.util.UUID

@Component
class GetProductsOperation(
    private val shopService: ShopService,
    private val productMapper: ProductMapper,
    private val productService: ProductService,
    private val userAuthService: UserAuthService
) {
    fun activate(shopId: UUID): GetProductsApiResponse {
        val userId = userAuthService.getCurrentUserId()
        val isShopExists = shopService.isShopExists(userId, shopId)

        if (!isShopExists) {
            throw BusinessException(SHOP_NOT_FOUND)
        }

        val products = productService.getProducts(shopId)
            .map(productMapper::map)

        return GetProducts200ApiResponse(products)
    }
}
