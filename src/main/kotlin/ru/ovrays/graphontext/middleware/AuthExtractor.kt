package ru.ovrays.graphontext.middleware

import ru.ovrays.graphontext.api.ApiSecurity.BearerAuth
import ru.ovrays.graphontext.model.exception.BusinessException
import ru.ovrays.graphontext.model.exception.BusinessExceptionCode.UNAUTHORIZED
import ru.ovrays.graphontext.model.user.UserPrincipal
import ru.ovrays.graphontext.service.user.UserTokenService
import ru.tinkoff.kora.common.Component
import ru.tinkoff.kora.common.Principal
import ru.tinkoff.kora.common.Tag
import ru.tinkoff.kora.http.server.common.HttpServerRequest
import ru.tinkoff.kora.http.server.common.auth.HttpServerPrincipalExtractor
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

@Component
@Tag(BearerAuth::class)
class AuthExtractor(
    private val userTokenService: UserTokenService
) : HttpServerPrincipalExtractor<Principal> {
    override fun extract(request: HttpServerRequest, value: String?): CompletionStage<Principal> {
        val token = value?.split(AUTH_TOKEN_DIVIDER)
            ?.lastOrNull()

        val userId = userTokenService.verifyUserToken(token)
            ?: throw BusinessException(UNAUTHORIZED)

        val principal = UserPrincipal(userId)

        return CompletableFuture.completedFuture(principal)
    }

    companion object {
        private const val AUTH_TOKEN_DIVIDER = " "
    }
}