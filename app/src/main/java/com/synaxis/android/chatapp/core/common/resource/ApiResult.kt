package com.synaxis.android.chatapp.core.common.resource

import kotlinx.serialization.Serializable


@Serializable
sealed class ApiResult<out T> {
    @Serializable
    data class Success<out T>(val data: T) : ApiResult<T>()

    @Serializable
    object  Empty : ApiResult<Nothing> ()
    @Serializable
    data class Error<out T>(
        val message: String? = null,
        val code: Int? = null,
        val errorType: ErrorType? = null
    ) : ApiResult<T>()

    companion object {
        fun <T> success(data: T): ApiResult<T> = Success(data)
        fun empty() : ApiResult<Nothing> = Empty
        fun <T> error(
            message: String? = null,
            code: Int? = null,
            errorType: ErrorType? = null
        ): ApiResult<T> = Error(message, code, errorType)
    }
}

fun <T, R> ApiResult<T>.flatmap(transform: (T) -> R): ApiResult<R> {
    return when (this) {
        is ApiResult.Empty -> ApiResult.empty()
        is ApiResult.Error -> ApiResult.error(this.message, this.code, this.errorType)
        is ApiResult.Success -> try {
            ApiResult.success(transform(this.data))
        }catch (e: Exception) {
            ApiResult.error(message = e.message ?: "Mapping failed", errorType = ErrorType.UNKNOWN)
        }
    }
}