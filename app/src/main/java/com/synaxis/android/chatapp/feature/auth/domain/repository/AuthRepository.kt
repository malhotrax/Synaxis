package com.synaxis.android.chatapp.feature.auth.domain.repository

import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.feature.auth.domain.model.AuthUser
import com.synaxis.android.chatapp.feature.auth.domain.model.RegisterUser

interface AuthRepository {
    suspend fun login(email: String, password: String): ApiResult<Nothing>
    suspend fun register(user: RegisterUser): ApiResult<Nothing>
}