package ru.ovrays.graphontext.service.user

import ru.ovrays.graphontext.model.user.User
import ru.ovrays.graphontext.model.exception.BusinessException
import ru.ovrays.graphontext.model.exception.BusinessExceptionCode.USER_NOT_FOUND
import ru.ovrays.graphontext.repository.UserRepository
import ru.ovrays.graphontext.service.util.ClockService
import ru.tinkoff.kora.common.Component
import java.util.UUID

@Component
class UserService(
    private val clockService: ClockService,
    private val userRepository: UserRepository,
    private val userAuthService: UserAuthService,
) {
    fun findUser(login: String): User? {
        return userRepository.findUser(login)
    }

    fun getUser(login: String): User {
        return userRepository.findUser(login)
            ?: throw BusinessException(USER_NOT_FOUND)
    }

    fun isUserExists(login: String): Boolean {
        return userRepository.findUser(login) != null
    }

    fun isUserExists(id: UUID): Boolean {
        return userRepository.isUserExists(id)
    }

    fun createUser(login: String, password: String): User {
        val now = clockService.now()
        val user = userRepository.createUser(login, now)

        userAuthService.createUserAuth(user.id, password)

        return user
    }
}
