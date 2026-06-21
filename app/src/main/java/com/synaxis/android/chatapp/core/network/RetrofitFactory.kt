package com.synaxis.android.chatapp.core.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitFactory {
    fun create(
        baseUrl: String,
        json: Json,
        httpClient: OkHttpClient? = null
    ): Retrofit {
        val builder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        httpClient?.let { client ->
            builder.client(client)
        }

        return builder.build()
    }
}