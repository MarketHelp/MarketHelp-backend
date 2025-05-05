package ru.ovrays.graphontext.service

import ru.ovrays.graphontext.configuration.AuthProperties
import ru.ovrays.graphontext.repository.ApiKeyRepository
import ru.ovrays.graphontext.service.util.ClockService
import ru.tinkoff.kora.common.Component
import java.util.*

@Component
class ApiKeyService(
    private val clockService: ClockService,
    private val authProperties: AuthProperties,
    private val apiKeyRepository: ApiKeyRepository
) {
    fun createApiKey(shopId: UUID, value: String) {
        val now = clockService.now()
        val validUntil = now.plusDays(authProperties.token.lifetime.toLong())

        apiKeyRepository.createApiKey(shopId, value, now, validUntil)
    }

    fun deleteApiKey(shopId: UUID) {
        apiKeyRepository.deleteApiKey(shopId)
    }
}
