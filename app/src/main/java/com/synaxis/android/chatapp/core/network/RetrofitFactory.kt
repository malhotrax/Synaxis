package com.synaxis.android.chatapp.core.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitFactory {
    private const val BASE_URL = "http://192.168.31.228:3000/api/v1/"
    fun getBaseUrl(): String = BASE_URL
    fun create(
        json: Json,
        httpClient: OkHttpClient? = null
    ): Retrofit {
        val builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        httpClient?.let { client ->
            builder.client(client)
        }

        return builder.build()
    }
}