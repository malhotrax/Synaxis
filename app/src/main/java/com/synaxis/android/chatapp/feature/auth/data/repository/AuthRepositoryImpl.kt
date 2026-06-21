package com.synaxis.android.chatapp.feature.auth.data.repository

import android.util.Log
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.datastore.session.SessionDatasource
import com.synaxis.android.chatapp.feature.auth.data.remote.AuthRemoteDatasource
import com.synaxis.android.chatapp.feature.auth.domain.model.RegisterUser
import com.synaxis.android.chatapp.feature.auth.domain.model.Tokens
import com.synaxis.android.chatapp.feature.auth.domain.repository.AuthRepository
import com.synaxis.android.chatapp.feature.user.data.local.dao.UserDao
import com.synaxis.android.chatapp.feature.user.data.mapper.toEntity
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDatasource: AuthRemoteDatasource,
    private val sessionDatasource: SessionDatasource,
    private val userDao: UserDao
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): ApiResult<Nothing> {
        return when (val result = authRemoteDatasource.login(email = email, password = password)) {
            ApiResult.Empty -> ApiResult.empty()
            is ApiResult.Error -> ApiResult.error(result.message, result.code, result.errorType)
            is ApiResult.Success -> {
                userDao.insert(result.data.user.toEntity())
                sessionDatasource.saveUserId(result.data.user.id)
                sessionDatasource.saveToken(
                    refreshToken = result.data.refreshToken,
                    accessToken = result.data.accessToken
                )
                val accessToken = sessionDatasource.accessToken().first { it != null}
                Log.d("TOKEN_STORING",accessToken.toString())
                ApiResult.empty()
            }
        }
    }

    override suspend fun register(user: RegisterUser): ApiResult<Nothing> {
        return when (val result = authRemoteDatasource.register(user)) {
            ApiResult.Empty -> ApiResult.empty()
            is ApiResult.Error -> ApiResult.error(result.message, result.code, result.errorType)
            is ApiResult.Success -> {
                userDao.insert(result.data.user.toEntity())
                sessionDatasource.saveUserId(result.data.user.id)
                sessionDatasource.saveToken(
                    refreshToken = result.data.refreshToken,
                    accessToken = result.data.accessToken
                )
                ApiResult.empty()
            }
        }
    }

    override suspend fun refreshTokens(refreshToken: String): ApiResult<Unit> {
        return when( val result = authRemoteDatasource.refreshTokens(refreshToken)) {
            ApiResult.Empty -> ApiResult.error()
            is ApiResult.Error -> ApiResult.error(result.message,result.code,result.errorType)
            is ApiResult.Success -> {
                sessionDatasource.saveToken(
                    refreshToken = result.data.refreshToken,
                    accessToken = result.data.accessToken
                )
                ApiResult.success(Unit)
            }
        }
    }
}