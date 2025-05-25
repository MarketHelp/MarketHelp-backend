package ru.ovrays.graphontext.model

import java.time.OffsetDateTime

data class Shop(
    val id: Long,
    val name: String,
    val updatedAt: OffsetDateTime,
    val createdAt: OffsetDateTime,
)
