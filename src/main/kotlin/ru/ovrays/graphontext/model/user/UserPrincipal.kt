package ru.ovrays.graphontext.model.user

import ru.tinkoff.kora.common.Principal
import java.util.UUID

data class UserPrincipal(
    val userId: UUID
) : Principal
