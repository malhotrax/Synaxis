package com.synaxis.android.chatapp.di

import com.synaxis.android.chatapp.core.network.HttpClientFactory
import com.synaxis.android.chatapp.core.network.RetrofitFactory
import com.synaxis.android.chatapp.core.network.interceptor.AuthInterceptor
import com.synaxis.android.chatapp.core.network.interceptor.TokenAuthenticator
import com.synaxis.android.chatapp.core.socket.SocketManager
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
    private const val BASE_URL = "http://192.168.1.40:3000"
    private const val HTTP_URL = "$BASE_URL/api/v1/"
    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
        prettyPrint = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient = HttpClientFactory.create(
        authInterceptor = authInterceptor,
        tokenAuthenticator = tokenAuthenticator
    )

    @Provides
    @Singleton
    @AuthRetrofit
    fun provideAuthRetrofitClient(
        json: Json,
        httpClient: OkHttpClient
    ): Retrofit = RetrofitFactory.create(HTTP_URL,json = json, httpClient = httpClient)

    @Provides
    @Singleton
    @PublicRetrofit
    fun providePublicRetrofit(
        json: Json
    ): Retrofit = RetrofitFactory.create(HTTP_URL,json)


    @Provides
    @Singleton
    fun provideSocketManager(
        json: Json
    ): SocketManager = SocketManager(BASE_URL,json)
}