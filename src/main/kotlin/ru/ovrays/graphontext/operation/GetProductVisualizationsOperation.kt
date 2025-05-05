package ru.ovrays.graphontext.operation

import ru.ovrays.graphontext.api.ProductApiResponses.GetProductVisualizationsApiResponse
import ru.ovrays.graphontext.api.ProductApiResponses.GetProductVisualizationsApiResponse.GetProductVisualizations200ApiResponse
import ru.ovrays.graphontext.model.exception.BusinessException
import ru.ovrays.graphontext.model.exception.BusinessExceptionCode.SHOP_NOT_FOUND
import ru.ovrays.graphontext.service.ProductVisualizationService
import ru.ovrays.graphontext.service.ShopService
import ru.ovrays.graphontext.service.mapper.ProductVisualizationMapper
import ru.ovrays.graphontext.service.user.UserAuthService
import ru.tinkoff.kora.common.Component
import java.util.*

@Component
class GetProductVisualizationsOperation(
    private val shopService: ShopService,
    private val userAuthService: UserAuthService,
    private val productVisualizationMapper: ProductVisualizationMapper,
    private val productVisualizationService: ProductVisualizationService
) {
    fun activate(
        shopId: UUID,
        productId: UUID
    ): GetProductVisualizationsApiResponse {
        val userId = userAuthService.getCurrentUserId()
        val isShopExists = shopService.isShopExists(userId, shopId)

        if (!isShopExists) {
            throw BusinessException(SHOP_NOT_FOUND)
        }

        val productVisualizations = productVisualizationService.getProductVisualizations(productId)
            .map(productVisualizationMapper::map)

        return GetProductVisualizations200ApiResponse(productVisualizations)
    }
}