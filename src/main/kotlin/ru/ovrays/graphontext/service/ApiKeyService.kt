package ru.ovrays.graphontext.service

import ru.ovrays.graphontext.configuration.AuthProperties
import ru.ovrays.graphontext.repository.ApiKeyRepository
import ru.ovrays.graphontext.service.util.ClockService
import ru.tinkoff.kora.common.Component

@Component
class ApiKeyService(
    private val clockService: ClockService,
    private val authProperties: AuthProperties,
    private val apiKeyRepository: ApiKeyRepository
) {
    fun getApiKey(shopId: Long): String {
        return apiKeyRepository.getApiKey(shopId)
    }

    fun createApiKey(shopId: Long, value: String) {
        val now = clockService.now()
        val validUntil = now.plusDays(authProperties.token.lifetime.toLong())

        apiKeyRepository.createApiKey(shopId, value, now, validUntil)
    }

    fun deleteApiKey(shopId: Long) {
        apiKeyRepository.deleteApiKey(shopId)
    }
}
