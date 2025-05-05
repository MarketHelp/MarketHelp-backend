package ru.ovrays.graphontext.service.util

import at.favre.lib.crypto.bcrypt.BCrypt
import ru.tinkoff.kora.common.Component

@Component
class BCryptService {
    fun encode(password: String): String {
        return hasher.hashToString(BCRYPT_FACTOR, password.toCharArray())
    }

    fun verify(password: String, encodedPassword: String): Boolean {
        val result = verifier.verify(password.toCharArray(), encodedPassword.toCharArray())

        return result.verified
    }

    companion object {
        private val hasher = BCrypt.withDefaults()
        private val verifier = BCrypt.verifyer()

        private const val BCRYPT_FACTOR = 10
    }
}
