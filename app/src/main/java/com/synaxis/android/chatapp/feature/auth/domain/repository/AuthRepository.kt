package com.synaxis.android.chatapp.feature.auth.domain.repository

import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.feature.auth.domain.model.AuthUser
import com.synaxis.android.chatapp.feature.auth.domain.model.RegisterUser
import com.synaxis.android.chatapp.feature.auth.domain.model.Tokens

interface AuthRepository {
    suspend fun login(email: String, password: String): ApiResult<Nothing>
    suspend fun register(user: RegisterUser): ApiResult<Nothing>
    suspend fun refreshTokens(refreshToken: String): ApiResult<Unit>
}