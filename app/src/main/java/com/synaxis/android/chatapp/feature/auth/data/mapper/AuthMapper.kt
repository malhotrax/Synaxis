package com.synaxis.android.chatapp.feature.auth.data.mapper

import com.synaxis.android.chatapp.feature.auth.data.remote.dto.AuthUserDto
import com.synaxis.android.chatapp.feature.auth.data.remote.dto.RegisterUserDto
import com.synaxis.android.chatapp.feature.auth.domain.model.AuthUser
import com.synaxis.android.chatapp.feature.auth.domain.model.RegisterUser

fun AuthUser.toDto() = AuthUserDto(
    refreshToken = this.refreshToken,
    accessToken = this.accessToken,
    user = this.user
)
fun AuthUserDto.toDomain() = AuthUser(
    refreshToken = this.refreshToken,
    accessToken = this.accessToken,
    user = this.user
)
fun RegisterUserDto.toDomain() = RegisterUser(
    username = this.username,
    email = this.email,
    password = this.password
)

fun RegisterUser.toDto() = RegisterUserDto(
    username = this.username,
    email = this.email,
    password = this.password
)

