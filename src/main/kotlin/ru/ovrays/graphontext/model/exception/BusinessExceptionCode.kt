package ru.ovrays.graphontext.model.exception

enum class BusinessExceptionCode(
    val message: String,
    val status: Int = 422
) {
    INTERNAL_SERVER_ERROR(message = "Internal Server Error", status = 500),
    UNAUTHORIZED(message = "Unauthorized", status = 401),

    INVALID_USER_CREDENTIALS(message = "Invalid user credentials", status = 400),

    USER_NOT_FOUND(message = "User not found"),
    USER_ALREADY_EXISTS(message = "User already exists"),
    SHOP_NOT_FOUND(message = "Shop not found"),
    SHOP_ALREADY_EXISTS(message = "Shop already exists"),
    PRODUCT_VISUALIZATION_NOT_FOUND(message = "Product visualization not found"),
    WRONG_API_KEY(message = "Некорректный API ключ")
}
