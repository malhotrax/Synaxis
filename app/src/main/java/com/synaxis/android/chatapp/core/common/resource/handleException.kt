package com.synaxis.android.chatapp.core.common.resource

import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun <T> handleException(exception: Exception): ApiResult<T> {
    return when(exception) {
        is ConnectException -> {
            ApiResult.error(
                message = "Server is down please try again.",
                errorType = ErrorType.NETWORK
            )
        }
        is UnknownHostException -> {
            ApiResult.error(
                message = "No internet connection",
                errorType = ErrorType.NETWORK
            )
        }
        is SocketTimeoutException -> {
            ApiResult.error(
                message = "Server taking too long to respond. Please check your connection and try again",
                errorType = ErrorType.TIMEOUT
            )
        }
        is IOException -> {
            ApiResult.error(
                message = "Network error: ${exception.message}",
                errorType = ErrorType.NETWORK
            )
        }
        is Exception -> {
            ApiResult.error(
                message = "Unexcepted Error ${exception.message}",
                errorType = ErrorType.UNKNOWN
            )
        }
    }
}