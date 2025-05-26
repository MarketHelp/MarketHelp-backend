package ru.ovrays.graphontext.configuration

import ru.tinkoff.kora.config.common.annotation.ConfigSource

@ConfigSource("storage")
data class StorageProperties(
    val templates: String,
    val output: String
)
