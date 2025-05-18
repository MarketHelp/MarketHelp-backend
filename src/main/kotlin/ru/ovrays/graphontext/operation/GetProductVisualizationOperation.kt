package ru.ovrays.graphontext.operation

import ru.ovrays.graphontext.api.ProductApiResponses.GetProductVisualizationApiResponse
import ru.ovrays.graphontext.api.ProductApiResponses.GetProductVisualizationApiResponse.GetProductVisualization200ApiResponse
import ru.ovrays.graphontext.model.exception.BusinessException
import ru.ovrays.graphontext.model.exception.BusinessExceptionCode.SHOP_NOT_FOUND
import ru.ovrays.graphontext.service.ProductVisualizationService
import ru.ovrays.graphontext.service.ShopService
import ru.ovrays.graphontext.service.StorageService
import ru.ovrays.graphontext.service.user.UserAuthService
import ru.tinkoff.kora.common.Component
import java.util.*

@Component
class GetProductVisualizationOperation(
    private val shopService: ShopService,
    private val storageService: StorageService,
    private val userAuthService: UserAuthService,
    private val productVisualizationService: ProductVisualizationService
) {
    fun activate(
        shopId: Long,
        productVisualizationId: UUID
    ): GetProductVisualizationApiResponse {
        val userId = userAuthService.getCurrentUserId()
        val isShopExists = shopService.isShopExists(userId, shopId)

        if (!isShopExists) {
            throw BusinessException(SHOP_NOT_FOUND)
        }

        val productVisualization = productVisualizationService.getProductVisualization(productVisualizationId)
        val image = storageService.readFile(productVisualization.filepath)

        return GetProductVisualization200ApiResponse(image)
    }
}
