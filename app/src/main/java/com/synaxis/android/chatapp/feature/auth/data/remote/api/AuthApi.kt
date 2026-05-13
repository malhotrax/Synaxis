package com.synaxis.android.chatapp.feature.auth.data.remote.api

import com.synaxis.android.chatapp.feature.auth.data.remote.dto.AuthUserDto
import com.synaxis.android.chatapp.feature.auth.data.remote.dto.LoginDto
import com.synaxis.android.chatapp.feature.auth.data.remote.dto.RegisterUserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("user/login")
    suspend fun login(
        @Body loginDto: LoginDto
    ): Response<AuthUserDto>

    @POST("user/register")
    suspend fun register(
        @Body registerUserDto: RegisterUserDto
    ): Response<AuthUserDto>
}