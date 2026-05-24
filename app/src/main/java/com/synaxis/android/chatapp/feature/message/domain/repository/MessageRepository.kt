package com.synaxis.android.chatapp.feature.message.domain.repository

import androidx.paging.PagingData
import com.synaxis.android.chatapp.feature.message.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {

    fun getMessages(): Flow<PagingData<Message>>
}