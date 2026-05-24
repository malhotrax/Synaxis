package com.synaxis.android.chatapp.feature.user.data.repository

import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.common.resource.MessageResponse
import com.synaxis.android.chatapp.core.common.user.User
import com.synaxis.android.chatapp.core.datastore.session.SessionDatasource
import com.synaxis.android.chatapp.feature.user.data.remote.UserRemoteDatasource
import com.synaxis.android.chatapp.feature.user.domain.model.GetUserResponse
import com.synaxis.android.chatapp.feature.user.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDatasource: UserRemoteDatasource,
    private val sessionDatasource: SessionDatasource
): UserRepository {
    override suspend fun getCurrentUser(): ApiResult<User> {
        return remoteDatasource.getCurrentUser()
    }

    override suspend fun deleteAccount(): ApiResult<MessageResponse> {
        return remoteDatasource.deleteAccount()
    }

    override suspend fun updateUsername(username: String): ApiResult<MessageResponse> {
        return remoteDatasource.updateUsername(username)
    }

    override suspend fun updateFullName(fullName: String): ApiResult<MessageResponse> {
        return remoteDatasource.updateFullName(fullName)
    }

    override suspend fun logout(): ApiResult<MessageResponse> {
        return remoteDatasource.logout()
    }

    override suspend fun updateAvatarUrl(avatarUrl: String): ApiResult<MessageResponse> {
        return when(val result = remoteDatasource.updateAvatarUrl(avatarUrl)) {
            is ApiResult.Success -> {
                sessionDatasource.clear()
                ApiResult.success(result.data)
            }
            else -> {
                ApiResult.error()
            }
        }
    }

    override suspend fun searchUser(query: String, limit: Int): ApiResult<GetUserResponse> {
        return remoteDatasource.searchUser(query, limit)
    }

    override suspend fun updatePassword(password: String): ApiResult<MessageResponse> {
        return remoteDatasource.updatePassword(password)
    }
}