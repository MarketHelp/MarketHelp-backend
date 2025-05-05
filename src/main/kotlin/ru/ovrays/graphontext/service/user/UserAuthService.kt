package ru.ovrays.graphontext.service.user

import ru.ovrays.graphontext.model.exception.BusinessException
import ru.ovrays.graphontext.model.exception.BusinessExceptionCode.UNAUTHORIZED
import ru.ovrays.graphontext.model.user.UserPrincipal
import ru.ovrays.graphontext.repository.UserAuthRepository
import ru.ovrays.graphontext.service.util.BCryptService
import ru.ovrays.graphontext.service.util.ClockService
import ru.tinkoff.kora.common.Component
import ru.tinkoff.kora.common.Principal
import java.util.*

@Component
class UserAuthService(
    private val clockService: ClockService,
    private val bCryptService: BCryptService,
    private val userAuthRepository: UserAuthRepository
) {
    fun getCurrentUserId(): UUID {
        val principal = Principal.current()
        val userPrincipal = principal as? (UserPrincipal)
            ?: throw BusinessException(UNAUTHORIZED)

        return userPrincipal.userId
    }

    fun createUserAuth(userId: UUID, password: String) {
        val now = clockService.now()
        val encodedPassword = bCryptService.encode(password)

        userAuthRepository.createUserAuth(userId, encodedPassword, now)
    }

    fun verifyUserAuth(userId: UUID, password: String): Boolean {
        val userAuth = userAuthRepository.getUserAuth(userId)

        return bCryptService.verify(password, userAuth.password)
    }
}
