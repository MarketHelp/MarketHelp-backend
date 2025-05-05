package ru.ovrays.graphontext.model.user

import java.time.OffsetDateTime
import java.util.UUID

data class UserAuth(
    val userId: UUID,
    val password: String,
    val updatedAt: OffsetDateTime,
    val createdAt: OffsetDateTime
)
