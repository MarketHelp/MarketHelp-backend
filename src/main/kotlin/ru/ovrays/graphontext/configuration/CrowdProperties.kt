package ru.ovrays.graphontext.configuration

import ru.tinkoff.kora.config.common.annotation.ConfigSource

@ConfigSource("crowd")
data class CrowdProperties(
    val name: String,
    val key: String
)
