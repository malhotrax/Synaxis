package com.synaxis.android.chatapp.core.common.remote_mediator

interface PagingLocalDatasource<Entity> {
    suspend fun deleteAll()
    suspend fun insertAll(items: List<Entity>)
}