package com.synaxis.android.chatapp.core.datastore.session

import kotlinx.coroutines.flow.Flow

interface SessionDatasource {
    fun accessToken() : Flow<String?>
    fun refreshToken() : Flow<String?>
    fun userId() : Flow<String?>
    suspend fun saveToken(
        refreshToken: String,
        accessToken: String
    )
    suspend fun clear()
    suspend fun saveUserId(userId: String)
}