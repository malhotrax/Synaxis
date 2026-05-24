package com.synaxis.android.chatapp.core.network.interceptor

import android.util.Log
import com.synaxis.android.chatapp.core.datastore.AuthTokenProvider
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val authTokenProvider: AuthTokenProvider,
) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = authTokenProvider.getAccessToken()
        val originalReq = chain.request()
        val request = if (accessToken.isNullOrBlank()) {
            originalReq
        } else {
            originalReq.newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .build()
        }
        return chain.proceed(request)
    }

}