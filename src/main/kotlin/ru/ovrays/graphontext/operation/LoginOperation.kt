package ru.ovrays.graphontext.operation

import ru.ovrays.graphontext.api.AuthApiResponses.LoginApiResponse
import ru.ovrays.graphontext.api.AuthApiResponses.LoginApiResponse.Login200ApiResponse
import ru.ovrays.graphontext.model.Login200ResponseDto
import ru.ovrays.graphontext.model.exception.BusinessException
import ru.ovrays.graphontext.model.exception.BusinessExceptionCode.INVALID_USER_CREDENTIALS
import ru.ovrays.graphontext.service.user.UserAuthService
import ru.ovrays.graphontext.service.user.UserService
import ru.ovrays.graphontext.service.user.UserTokenService
import ru.tinkoff.kora.common.Component

@Component
open class LoginOperation(
    private val userService: UserService,
    private val userAuthService: UserAuthService,
    private val userTokenService: UserTokenService
) {
    open fun activate(login: String, password: String): LoginApiResponse {
        val user = userService.findUser(login)
            ?: throw BusinessException(INVALID_USER_CREDENTIALS)

        val isVerified = userAuthService.verifyUserAuth(user.id, password)

        if (!isVerified) {
            throw BusinessException(INVALID_USER_CREDENTIALS)
        }

        val token = userTokenService.createUserToken(user.id)

        return Login200ApiResponse(
            content = Login200ResponseDto(token)
        )
    }
}
