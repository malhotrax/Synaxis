package com.synaxis.android.chatapp.core.network.interceptor

import com.synaxis.android.chatapp.core.common.network.ApiErrorResponse
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.datastore.AuthTokenProvider
import com.synaxis.android.chatapp.core.datastore.session.SessionDatasource
import com.synaxis.android.chatapp.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val authRepository: AuthRepository,
    private val authTokenProvider: AuthTokenProvider,
    private val json: Json,
    private val sessionDatasource: SessionDatasource,
    private val appScope: CoroutineScope
) : Authenticator {

    @Synchronized
    override fun authenticate(route: Route?, response: Response): Request? {
        if (parseError(response) != "TOKEN_EXPIRED") return null
        if (response.responseCount() >= 3) return null
        val refreshToken = authTokenProvider.getRefreshToken() ?: return null
        val result: ApiResult<Unit> = runBlocking {
            authRepository.refreshTokens(refreshToken)
        }
        return when(result) {
            is ApiResult.Success -> {
                val accessToken =  authTokenProvider.getAccessToken() ?: return null
                response.request
                    .newBuilder()
                    .header("Authorization","Bearer $accessToken")
                    .build()
            }
            else -> {
                appScope.launch {
                    sessionDatasource.clear()
                }
                null
            }
        }

    }

    private fun parseError(response: Response): String? {
        return try {
            val body = response.peekBody(Long.MAX_VALUE).string()
            val jsonBody = json.decodeFromString<ApiErrorResponse>(body)
            jsonBody.code
        } catch (e: Exception) {
            null
        }
    }

    private fun Response.responseCount(): Int {
        return generateSequence(this) { it.priorResponse }.count()
    }

}