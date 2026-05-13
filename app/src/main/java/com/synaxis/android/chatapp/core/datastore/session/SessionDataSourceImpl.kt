package com.synaxis.android.chatapp.core.datastore.session

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("session_manager")

class SessionDataSourceImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : SessionDatasource {
    private val accessToken: Flow<String?> =
        context.dataStore.data.map { it[SessionKeys.ACCESS_TOKEN] }
    private val refreshToken: Flow<String?> =
        context.dataStore.data.map { it[SessionKeys.REFRESH_TOKEN] }
    private val userId: Flow<String?> = context.dataStore.data.map { it[SessionKeys.USER_ID] }

    override fun accessToken(): Flow<String?> = accessToken

    override fun refreshToken(): Flow<String?> = refreshToken

    override fun userId(): Flow<String?> = userId

    override suspend fun saveToken(refreshToken: String, accessToken: String) {
        context.dataStore.edit {
            it[SessionKeys.ACCESS_TOKEN] = accessToken
            it[SessionKeys.REFRESH_TOKEN] = refreshToken
        }
    }

    override suspend fun clear() {
        context.dataStore.edit {
            it.clear()
        }
    }

    override suspend fun saveUserId(userId: String) {
        context.dataStore.edit {
            it[SessionKeys.USER_ID] = userId
        }
    }
}