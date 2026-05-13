package com.synaxis.android.chatapp.di

import com.synaxis.android.chatapp.core.network.HttpClientFactory
import com.synaxis.android.chatapp.core.network.RetrofitFactory
import com.synaxis.android.chatapp.core.network.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkProvideModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
        prettyPrint = true
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient = HttpClientFactory.create(
        authInterceptor = authInterceptor
    )

    @Provides
    @Singleton
    fun provideRetrofitClient(
        json: Json,
        httpClient: OkHttpClient
    ): Retrofit = RetrofitFactory.create(json = json, httpClient = httpClient)

}