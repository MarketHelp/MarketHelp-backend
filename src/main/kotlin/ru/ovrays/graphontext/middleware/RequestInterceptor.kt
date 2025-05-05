package ru.ovrays.graphontext.middleware

import ru.ovrays.graphontext.util.KLogging
import ru.tinkoff.kora.common.Component
import ru.tinkoff.kora.common.Context
import ru.tinkoff.kora.common.Tag
import ru.tinkoff.kora.http.server.common.HttpServerInterceptor
import ru.tinkoff.kora.http.server.common.HttpServerModule
import ru.tinkoff.kora.http.server.common.HttpServerRequest
import ru.tinkoff.kora.http.server.common.HttpServerResponse
import java.util.concurrent.CompletionStage

@Component
@Tag(HttpServerModule::class)
class RequestInterceptor(
    private val errorInterceptor: ErrorInterceptor
) : HttpServerInterceptor {
    override fun intercept(
        context: Context,
        request: HttpServerRequest,
        chain: HttpServerInterceptor.InterceptChain
    ): CompletionStage<HttpServerResponse> {
        logger.info("> ${request.path()}")

        return chain.process(context, request)
            .exceptionally(errorInterceptor::intercept)
            .thenApply { response ->
                logger.info("< ${request.path()} – ${response.code()}")
                response
            }
    }

    companion object : KLogging()
}