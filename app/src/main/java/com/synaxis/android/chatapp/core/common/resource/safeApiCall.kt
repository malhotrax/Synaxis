package com.synaxis.android.chatapp.core.common.resource

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import retrofit2.Response

suspend inline fun <T> safeApiCall(
    crossinline request: suspend  () -> Response<T>
): ApiResult<T> {
    return try {
        val response = withContext(Dispatchers.IO) {
            request()
        }
        handleResponse(response)
    } catch (exception: Exception) {
        handleException(exception)
    }
}