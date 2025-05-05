package ru.ovrays.graphontext.model.exception

data class BusinessException(
    val code: BusinessExceptionCode,

    override val cause: Exception? = null,
) : RuntimeException(cause)
