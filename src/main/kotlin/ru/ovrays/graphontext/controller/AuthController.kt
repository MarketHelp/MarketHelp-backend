package ru.ovrays.graphontext.controller

import ru.ovrays.graphontext.api.AuthApiDelegate
import ru.ovrays.graphontext.api.AuthApiResponses.LoginApiResponse
import ru.ovrays.graphontext.api.AuthApiResponses.RegisterApiResponse
import ru.ovrays.graphontext.model.AuthCredentialsDto
import ru.ovrays.graphontext.operation.LoginOperation
import ru.ovrays.graphontext.operation.RegisterOperation
import ru.tinkoff.kora.common.Component

@Component
class AuthController(
    private val loginOperation: LoginOperation,
    private val registerOperation: RegisterOperation
) : AuthApiDelegate {
    override fun login(
        authCredentialsDto: AuthCredentialsDto
    ): LoginApiResponse {
        return loginOperation.activate(authCredentialsDto.login, authCredentialsDto.password)
    }

    override fun register(
        authCredentialsDto: AuthCredentialsDto
    ): RegisterApiResponse {
        return registerOperation.activate(authCredentialsDto.login, authCredentialsDto.password)
    }
}
