package com.synaxis.android.chatapp.feature.user.data.repository

import androidx.room.withTransaction
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.common.resource.MessageResponse
import com.synaxis.android.chatapp.core.common.user.User
import com.synaxis.android.chatapp.core.database.AppDatabase
import com.synaxis.android.chatapp.core.datastore.session.SessionDatasource
import com.synaxis.android.chatapp.feature.user.data.local.dao.UserDao
import com.synaxis.android.chatapp.feature.user.data.mapper.toDomain
import com.synaxis.android.chatapp.feature.user.data.remote.UserRemoteDatasource
import com.synaxis.android.chatapp.feature.user.domain.model.GetUserResponse
import com.synaxis.android.chatapp.feature.user.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDatasource: UserRemoteDatasource,
    private val sessionDatasource: SessionDatasource,
    private val userDao: UserDao,
    private val appDatabase: AppDatabase
) : UserRepository {

    private suspend fun userId() = sessionDatasource.userId().first { !it.isNullOrEmpty() }
    override suspend fun getCurrentUser(): ApiResult<User> {
        return try {
            val userId = userId() ?: return ApiResult.error("No id found")
            val user = userDao.getUser(userId) ?: return ApiResult.error("Failed to get user")
            ApiResult.success(user.toDomain())
        } catch (e: Exception) {
            ApiResult.error(e.localizedMessage)
        }
    }

    override suspend fun deleteAccount(): ApiResult<MessageResponse> {
        val userId = userId() ?: return ApiResult.error("No id found")
        val result = remoteDatasource.deleteAccount()
        if (result is ApiResult.Success) {
            userDao.deleteAccount(userId)
            sessionDatasource.clear()
        }
        return result
    }

    override suspend fun updateUsername(username: String): ApiResult<MessageResponse> {
        val userId = userId() ?: return ApiResult.error("No user id found")
        return appDatabase.withTransaction {
            val result = remoteDatasource.updateUsername(username)
            if (result is ApiResult.Success) {
                userDao.updateUsername(username, userId)
            }
            result
        }
    }

    override suspend fun updateFullName(fullName: String): ApiResult<MessageResponse> {
        val userId = userId() ?: return ApiResult.error("No user id found")
        return appDatabase.withTransaction {
            val result = remoteDatasource.updateFullName(fullName)
            if (result is ApiResult.Success) {
                userDao.updateFullName(fullName, userId)
            }
            result
        }
    }

    override suspend fun logout() {
        withContext(Dispatchers.IO) {
            sessionDatasource.clear()
            appDatabase.clearAllData()
        }
    }

    override suspend fun updateAvatarUrl(avatarUrl: String): ApiResult<MessageResponse> {
        return when (val result = remoteDatasource.updateAvatarUrl(avatarUrl)) {
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