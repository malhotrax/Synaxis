package com.synaxis.android.chatapp.core.common.remote_mediator

import kotlinx.serialization.Serializable

@Serializable
data class PagingResponse<T> (
    val items: List<T>,
    val nextCursor: String? = null,
    val hasMore: Boolean
)