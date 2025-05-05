package ru.ovrays.graphontext.middleware

import org.slf4j.LoggerFactory
import ru.ovrays.graphontext.model.exception.BusinessException
import ru.ovrays.graphontext.model.exception.BusinessExceptionCode
import ru.ovrays.graphontext.model.exception.BusinessExceptionCode.INTERNAL_SERVER_ERROR
import ru.tinkoff.kora.common.Component
import ru.tinkoff.kora.http.common.body.HttpBody
import ru.tinkoff.kora.http.server.common.HttpServerResponse
import ru.tinkoff.kora.http.server.common.HttpServerResponseException
import java.util.concurrent.CompletionException

@Component
class ErrorInterceptor {
    fun intercept(throwable: Throwable): HttpServerResponse {
        val error = if (throwable is CompletionException) throwable.cause!! else throwable

        if (error is HttpServerResponseException) {
            return error
        }

        val errorCode = getErrorCode(error)
        val jsonBody = HttpBody.json("{\"errorCode\": \"${errorCode.name}\", \"message\": \"${errorCode.message}\"}")

        return HttpServerResponse.of(errorCode.status, jsonBody)
    }

    private fun getErrorCode(
        error: Throwable
    ): BusinessExceptionCode {
        if (error is BusinessException) {
            return error.code
        }

        logger.error("Unexpected exception occurred", error)

        return INTERNAL_SERVER_ERROR
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ErrorInterceptor::class.java)
    }
}
