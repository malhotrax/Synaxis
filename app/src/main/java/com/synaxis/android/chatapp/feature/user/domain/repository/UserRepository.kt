package com.synaxis.android.chatapp.feature.user.domain.repository

import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.common.resource.MessageResponse
import com.synaxis.android.chatapp.core.common.user.GetUser
import com.synaxis.android.chatapp.core.common.user.User
import com.synaxis.android.chatapp.feature.user.domain.model.GetUserResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun deleteAccount(): ApiResult<MessageResponse>
    suspend fun updateUsername(username: String): ApiResult<MessageResponse>
    suspend fun updateFullName(fullName: String): ApiResult<MessageResponse>
    suspend fun logout() : ApiResult<MessageResponse>
    suspend fun updateAvatarUrl(avatarUrl: String): ApiResult<MessageResponse>
    suspend fun searchUser(query: String, limit: Int = 20): ApiResult<GetUserResponse>

    suspend fun updatePassword(password: String): ApiResult<MessageResponse>
}