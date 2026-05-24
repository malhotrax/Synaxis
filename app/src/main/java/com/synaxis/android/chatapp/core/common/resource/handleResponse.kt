package com.synaxis.android.chatapp.core.common.resource

import android.util.Log
import kotlinx.serialization.json.JsonObject
import org.json.JSONObject
import retrofit2.Response

fun <T> handleResponse(response: Response<T>): ApiResult<T> {
    return when(response.code()) {
        200,201 -> {
           response.body()?.let {
               ApiResult.success(it)
           }?: ApiResult.error(
               message = "No data found.",
               code = response.code(),
               errorType = ErrorType.UNKNOWN
           )
        }
        401 -> {
            val serverMessage = parseServerMessage(response)
            ApiResult.error(
                message = " ${serverMessage}.",
                code = response.code(),
                errorType = ErrorType.UNAUTHORIZED
            )
        }
        404-> {
            val serverMessage = parseServerMessage(response)
            ApiResult.error(
                message = serverMessage ?: "Resource that are you looking for either moved or removed.",
                code =  response.code(),
                errorType = ErrorType.NOT_FOUND
            )
        }
        in 400..499 -> {
            val serverMessage = parseServerMessage(response)
            ApiResult.error(
                message = serverMessage ?: "Something went wrong with your request. Please try again.",
                code = response.code(),
                errorType = ErrorType.CLIENT
            )
        }
        in 500..599 -> {
            val serverMessage = parseServerMessage(response)
            ApiResult.error(
                message = serverMessage ?: "We are experiencing a temporary issue on our end. Please try again in a few moments.",
                code = response.code(),
                errorType = ErrorType.SERVER
            )
        }
        else -> {
            val serverMessage = parseServerMessage(response)
            ApiResult.error(
                message = serverMessage ?: "Unknown error : ${response.message()}",
                code = response.code(),
                errorType = ErrorType.UNKNOWN
            )
        }
    }
}

private fun parseServerMessage(response: Response<*>): String? {
    return try {
        val json = response.errorBody()?.string()
        val jsonObj = JSONObject(json?:"")
        jsonObj.optString("message").ifBlank { null }
    }catch (e: Exception) {
        null
    }
}