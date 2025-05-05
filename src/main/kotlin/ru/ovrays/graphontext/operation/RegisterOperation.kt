package ru.ovrays.graphontext.operation

import ru.ovrays.graphontext.api.AuthApiResponses.RegisterApiResponse
import ru.ovrays.graphontext.model.Login200ResponseDto
import ru.ovrays.graphontext.model.exception.BusinessException
import ru.ovrays.graphontext.model.exception.BusinessExceptionCode.USER_ALREADY_EXISTS
import ru.ovrays.graphontext.service.user.UserService
import ru.ovrays.graphontext.service.user.UserTokenService
import ru.tinkoff.kora.common.Component
import ru.tinkoff.kora.logging.common.annotation.Log

@Component
open class RegisterOperation(
    private val userService: UserService,
    private val userTokenService: UserTokenService
) {
    open fun activate(login: String, password: String): RegisterApiResponse {
        val isExists = userService.isUserExists(login)

        if (isExists) {
            throw BusinessException(USER_ALREADY_EXISTS)
        }

        val user = userService.createUser(login, password)
        val token = userTokenService.createUserToken(user.id)

        return RegisterApiResponse.Register200ApiResponse(
            content = Login200ResponseDto(token)
        )
    }
}
