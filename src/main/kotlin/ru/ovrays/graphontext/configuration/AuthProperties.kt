package ru.ovrays.graphontext.configuration

import ru.tinkoff.kora.config.common.annotation.ConfigSource

@ConfigSource("auth")
data class AuthProperties(
    val token: AuthToken
) {
    data class AuthToken(
        val secret: String,
        val algorithm: String,
        val lifetime: Int
    )
}
