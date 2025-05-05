package ru.ovrays.graphontext.model

import java.time.OffsetDateTime
import java.util.UUID

data class Shop(
    val id: UUID,
    val name: String,
    val updatedAt: OffsetDateTime,
    val createdAt: OffsetDateTime,
)
