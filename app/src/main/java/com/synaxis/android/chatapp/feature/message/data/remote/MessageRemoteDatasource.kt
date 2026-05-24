package com.synaxis.android.chatapp.feature.message.data.remote

import com.synaxis.android.chatapp.core.common.remote_mediator.PagingResponse
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.common.resource.flatmap
import com.synaxis.android.chatapp.core.common.resource.safeApiCall
import com.synaxis.android.chatapp.feature.message.data.local.entity.MessageEntity
import com.synaxis.android.chatapp.feature.message.data.mapper.toEntity
import com.synaxis.android.chatapp.feature.message.data.remote.api.MessageApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRemoteDatasource @Inject constructor(
    private val messageApi: MessageApi
) {
    suspend fun getMessages(
        cursor: String?,
        limit: Int = 10
    ): ApiResult<PagingResponse<MessageEntity>> {
        return safeApiCall {
            messageApi.getMessages(
                cursor = cursor,
                limit = limit
            )
        }.flatmap { pagingResponse ->
            PagingResponse(
                items = pagingResponse.items.map { it.toEntity() },
                hasMore = pagingResponse.hasMore,
                nextCursor = pagingResponse.nextCursor
            )
        }
    }
}