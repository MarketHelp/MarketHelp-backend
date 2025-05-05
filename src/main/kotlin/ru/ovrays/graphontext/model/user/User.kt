package ru.ovrays.graphontext.model.user

import java.time.OffsetDateTime
import java.util.UUID

data class User(
    val id: UUID,
    val login: String,
    val role: UserRole,
    val updatedAt: OffsetDateTime,
    val createdAt: OffsetDateTime
)
