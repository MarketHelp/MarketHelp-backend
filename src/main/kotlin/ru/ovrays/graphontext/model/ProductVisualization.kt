package ru.ovrays.graphontext.model

import java.time.OffsetDateTime
import java.util.UUID

data class ProductVisualization(
    val id: UUID,
    val format: String,
    val filepath: String,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime
)
