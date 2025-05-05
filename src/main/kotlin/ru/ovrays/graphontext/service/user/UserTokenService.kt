package ru.ovrays.graphontext.service.user

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import ru.ovrays.graphontext.configuration.AuthProperties
import ru.ovrays.graphontext.service.util.ClockService
import ru.ovrays.graphontext.util.KLogging
import ru.tinkoff.kora.common.Component
import java.time.temporal.ChronoUnit.MINUTES
import java.util.*
import javax.crypto.spec.SecretKeySpec
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Component
@OptIn(ExperimentalEncodingApi::class)
class UserTokenService(
    private val userService: UserService,
    private val clockService: ClockService,
    private val authProperties: AuthProperties
) {
    private val secretKey: SecretKeySpec

    init {
        val secret = authProperties.token.secret.toByteArray()
        val secretSource = Base64.encode(secret)
            .toByteArray()

        secretKey = SecretKeySpec(secretSource, authProperties.token.algorithm)
    }

    fun createUserToken(userId: UUID): String {
        val now = clockService.now()
            .toInstant()

        val id = UUID.randomUUID()
        val issuedAt = Date.from(now)
        val expiresAt = Date.from(now.plus(authProperties.token.lifetime.toLong(), MINUTES))

        return Jwts.builder()
            .id(id.toString())
            .subject(userId.toString())
            .issuedAt(issuedAt)
            .expiration(expiresAt)
            .signWith(secretKey)
            .compact()
    }

    fun verifyUserToken(token: String?): UUID? {
        val parser = Jwts.parser()
            .verifyWith(secretKey)
            .build()

        if (!parser.isSigned(token)) {
            return null
        }

        return try {
            val jwtClaims = parser.parseSignedClaims(token)
            val userId = UUID.fromString(jwtClaims.payload.subject)

            val isUserExists = userService.isUserExists(userId)

            userId?.takeIf { isUserExists }
        } catch (e: ExpiredJwtException) {
            logger.warn("Expired JWT token declined", e)
            null
        }
    }

    companion object : KLogging()
}
