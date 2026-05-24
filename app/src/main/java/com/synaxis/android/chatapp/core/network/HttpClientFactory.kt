package com.synaxis.android.chatapp.core.network

import com.synaxis.android.chatapp.core.network.interceptor.AuthInterceptor
import com.synaxis.android.chatapp.core.network.interceptor.TokenAuthenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object HttpClientFactory {
    fun create(
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ) : OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor (authInterceptor)
            .addNetworkInterceptor(loggingInterceptor)
            .authenticator(tokenAuthenticator)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }
}
