package ru.ovrays.graphontext.model

import java.time.OffsetDateTime
import java.util.UUID

data class Product(
    val id: UUID,
    val name: String,
    val updatedAt: OffsetDateTime,
    val createdAt: OffsetDateTime,
    val hasVisualization: Boolean
)
