package com.synaxis.android.chatapp.feature.auth.data.remote

import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.common.resource.ErrorType
import com.synaxis.android.chatapp.core.common.resource.flatmap
import com.synaxis.android.chatapp.core.common.resource.safeApiCall
import com.synaxis.android.chatapp.core.network.connectivity.NetworkConnectivityManager
import com.synaxis.android.chatapp.feature.auth.data.mapper.toDomain
import com.synaxis.android.chatapp.feature.auth.data.mapper.toDto
import com.synaxis.android.chatapp.feature.auth.data.remote.api.AuthApi
import com.synaxis.android.chatapp.feature.auth.data.remote.dto.LoginDto
import com.synaxis.android.chatapp.feature.auth.data.remote.dto.RefreshTokenDto
import com.synaxis.android.chatapp.feature.auth.data.remote.dto.TokensDto
import com.synaxis.android.chatapp.feature.auth.domain.model.AuthUser
import com.synaxis.android.chatapp.feature.auth.domain.model.RegisterUser
import javax.inject.Inject

class AuthRemoteDatasource  @Inject constructor(
    private val authApi: AuthApi,
    private val networkConnectivityManager: NetworkConnectivityManager
) {
    suspend fun login(email: String, password: String) : ApiResult<AuthUser> {
        if(!networkConnectivityManager.isConnected()) {
            return ApiResult.error(message = "Please connect to the internet", errorType = ErrorType.NETWORK )
        }
        return safeApiCall {
            authApi.login(LoginDto(email = email, password = password))
        }.flatmap { authUserDto ->  authUserDto.toDomain() }
    }
    suspend fun register(registerUser: RegisterUser) : ApiResult<AuthUser> {
        if(!networkConnectivityManager.isConnected()) {
            return ApiResult.error(message = "Please connect to the internet", errorType = ErrorType.NETWORK )
        }
        return safeApiCall {
            authApi.register(registerUser.toDto())
        }.flatmap { authUserDto -> authUserDto.toDomain()}
    }
    suspend fun refreshTokens(refreshToken: String): ApiResult<TokensDto> {
        return safeApiCall {
            authApi.refreshTokens(RefreshTokenDto(refreshToken))
        }
    }
}