package ru.ovrays.graphontext.operation

import ru.ovrays.graphontext.api.ProductApiResponses.CreateProductVisualizationApiResponse
import ru.ovrays.graphontext.api.ProductApiResponses.CreateProductVisualizationApiResponse.CreateProductVisualization200ApiResponse
import ru.ovrays.graphontext.model.CreateProductVisualizationRequestDto
import ru.ovrays.graphontext.model.GraphicFormat.ALL
import ru.ovrays.graphontext.model.GraphicFormat.BAR
import ru.ovrays.graphontext.model.GraphicFormat.PIE
import ru.ovrays.graphontext.model.exception.BusinessException
import ru.ovrays.graphontext.model.exception.BusinessExceptionCode
import ru.ovrays.graphontext.service.AiService
import ru.ovrays.graphontext.service.GraphicService
import ru.ovrays.graphontext.service.ProductVisualizationService
import ru.ovrays.graphontext.service.ShopService
import ru.ovrays.graphontext.service.StorageService
import ru.ovrays.graphontext.service.user.UserAuthService
import ru.tinkoff.kora.common.Component
import java.util.*

@Component
class CreateProductVisualization(
    private val shopService: ShopService,
    private val aiService: AiService,
    private val graphicService: GraphicService,
    private val storageService: StorageService,
    private val userAuthService: UserAuthService,
    private val productVisualizationService: ProductVisualizationService
) {
    fun activate(
        shopId: Long,
        productId: UUID,
        dto: CreateProductVisualizationRequestDto
    ): CreateProductVisualizationApiResponse {
        val userId = userAuthService.getCurrentUserId()
        val isShopExists = shopService.isShopExists(userId, shopId)

        if (!isShopExists) {
            throw BusinessException(BusinessExceptionCode.SHOP_NOT_FOUND)
        }

        val filename = "$productId-${dto.format}.html"
        val graphic = createGraphic(shopId, productId, filename, dto.format)

        productVisualizationService.createProductVisualization(productId, filename, dto.format)

        return CreateProductVisualization200ApiResponse(graphic)
    }

    private fun createGraphic(
        shopId: Long,
        productId: UUID,
        filename: String,
        format: String
    ): ByteArray {
        val dataFrame = aiService.getStatistics(shopId, productId, format)

        val graphic = if (format == ALL.value) {
            val pieStatistics = aiService.getStatistics(shopId, productId, PIE.value)
            val barStatistics = aiService.getStatistics(shopId, productId, BAR.value)

            graphicService.createGraphic(filename, dataFrame, format, pieStatistics, barStatistics)
        } else {
            graphicService.createGraphic(filename, dataFrame, format)
        }

        storageService.writeFile(filename, graphic.content)

        return storageService.readHtmlToImage(filename)
    }
}
