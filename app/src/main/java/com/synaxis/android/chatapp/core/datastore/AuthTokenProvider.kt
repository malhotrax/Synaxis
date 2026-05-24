package com.synaxis.android.chatapp.core.datastore

import com.synaxis.android.chatapp.core.datastore.session.SessionDatasource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.io.encoding.Base64

@Singleton
class AuthTokenProvider @Inject constructor(
    private val sessionDatasource: SessionDatasource
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val accessToken: StateFlow<String?> = sessionDatasource.accessToken().stateIn(
        scope = scope, started = SharingStarted.Eagerly, initialValue = null
    )
    private val refreshToken: StateFlow<String?> = sessionDatasource.refreshToken().stateIn(
        scope = scope, started = SharingStarted.Eagerly, initialValue = null
    )
    private val userId: StateFlow<String?> = sessionDatasource.userId().stateIn(
        scope = scope, started = SharingStarted.Eagerly, initialValue = null
    )
    fun getAccessToken() = accessToken.value
    fun getRefreshToken() = refreshToken.value
    fun getUserId() = userId.value
    suspend fun isLoggedIn() = !accessToken.first { it != null}.isNullOrBlank()
}