package ru.ovrays.graphontext.client

import ru.ovrays.graphontext.model.exception.BusinessException
import ru.ovrays.graphontext.model.exception.BusinessExceptionCode.WRONG_API_KEY
import ru.ovrays.graphontext.model.yaMarket.YaMarketProduct
import ru.ovrays.yamarket.api.BusinessOfferMappingsApi
import ru.ovrays.yamarket.api.BusinessOfferMappingsApiResponses.GetOfferMappingsApiResponse.GetOfferMappings200ApiResponse
import ru.ovrays.yamarket.api.CampaignsApi
import ru.ovrays.yamarket.api.CampaignsApiResponses.GetCampaignsApiResponse.GetCampaigns200ApiResponse
import ru.tinkoff.kora.common.Component
import java.util.*

@Component
class YaMarketClient(
    private val campaignsApi: CampaignsApi,
    private val offerMappingsApi: BusinessOfferMappingsApi
) {
    fun getShopId(apiKey: String): Long {
        val response = campaignsApi.getCampaigns(apiKey)

        val shopId = if (response is GetCampaigns200ApiResponse) {
            val campaign = response.content.campaigns.first()

            campaign.business?.id
        } else null

        return shopId ?: throw BusinessException(WRONG_API_KEY)
    }

    fun getProducts(apiKey: String, shopId: Long): List<YaMarketProduct> {
        val response = offerMappingsApi.getOfferMappings(shopId, apiKey)

        if (response is GetOfferMappings200ApiResponse) {
            return response.content.result.offerMappings.map {
                YaMarketProduct(
                    id = UUID.fromString(it.offer.offerId),
                    name = it.offer.name,
                )
            }
        }

        throw BusinessException(WRONG_API_KEY)
    }
}
