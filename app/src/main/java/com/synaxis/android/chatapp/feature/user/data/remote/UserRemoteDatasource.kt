package com.synaxis.android.chatapp.feature.user.data.remote

import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.common.resource.ErrorType
import com.synaxis.android.chatapp.core.common.resource.MessageResponse
import com.synaxis.android.chatapp.core.common.resource.flatmap
import com.synaxis.android.chatapp.core.common.resource.safeApiCall
import com.synaxis.android.chatapp.core.common.user.User
import com.synaxis.android.chatapp.core.network.connectivity.NetworkConnectivityManager
import com.synaxis.android.chatapp.feature.user.data.mapper.toDomain
import com.synaxis.android.chatapp.feature.user.data.remote.api.UserApi
import com.synaxis.android.chatapp.feature.user.data.remote.dto.UpdateAvatarUrlReq
import com.synaxis.android.chatapp.feature.user.data.remote.dto.UpdateFullNameReq
import com.synaxis.android.chatapp.feature.user.data.remote.dto.UpdatePasswordReq
import com.synaxis.android.chatapp.feature.user.data.remote.dto.UpdateUsernameReq
import com.synaxis.android.chatapp.feature.user.domain.model.GetUserResponse
import javax.inject.Inject

class UserRemoteDatasource @Inject constructor(
    private val userApi: UserApi,
    private val networkConnectivityManager: NetworkConnectivityManager
) {
    suspend fun getCurrentUser() : ApiResult<User> {
        return  safeApiCall {
            userApi.getCurrentUser()
        }
    }
    suspend fun updateUsername(username: String): ApiResult<MessageResponse> {
        if (!networkConnectivityManager.isConnected()) {
            return ApiResult.error("Please connect to internet", errorType = ErrorType.NETWORK)
        }
        val updateUsernameReq = UpdateUsernameReq(username)
        return safeApiCall {
            userApi.updateUsername(updateUsernameReq)
        }
    }

    suspend fun updateFullName(fullName: String): ApiResult<MessageResponse> {
        if (!networkConnectivityManager.isConnected()) {
            return ApiResult.error("Please connect to internet", errorType = ErrorType.NETWORK)
        }
        val updateFullNameReq = UpdateFullNameReq(fullName)
        return safeApiCall {
            userApi.updateFullName(updateFullNameReq)
        }
    }

    suspend fun updateAvatarUrl(avatarUrl: String): ApiResult<MessageResponse> {
        if (!networkConnectivityManager.isConnected()) {
            return ApiResult.error("Please connect to internet", errorType = ErrorType.NETWORK)
        }
        val updateAvatarUrlReq = UpdateAvatarUrlReq(avatarUrl)
        return safeApiCall {
            userApi.updateAvatarUrl(updateAvatarUrlReq)
        }
    }

    suspend fun deleteAccount(): ApiResult<MessageResponse> {
        if (!networkConnectivityManager.isConnected()) {
            return ApiResult.error("Please connect to internet", errorType = ErrorType.NETWORK)
        }
        return safeApiCall {
            userApi.deleteAccount()
        }
    }
    suspend fun searchUser(query: String, limit: Int = 20): ApiResult<GetUserResponse> {
        if (!networkConnectivityManager.isConnected()) {
            return ApiResult.error("Please connect to internet", errorType = ErrorType.NETWORK)
        }
        return safeApiCall {
            userApi.searchUser(query = query, limit = limit)
        }.flatmap {
            it.toDomain()
        }
    }
    suspend fun logout(): ApiResult<MessageResponse> {
        return safeApiCall {
            userApi.logout()
        }
    }

    suspend fun updatePassword(password: String) : ApiResult<MessageResponse> {
        if (!networkConnectivityManager.isConnected()) {
            return ApiResult.error("Please connect to internet", errorType = ErrorType.NETWORK)
        }
        val updatePasswordReq = UpdatePasswordReq(password)
        return safeApiCall {
            userApi.updatePassword(updatePasswordReq)
        }
    }
}
