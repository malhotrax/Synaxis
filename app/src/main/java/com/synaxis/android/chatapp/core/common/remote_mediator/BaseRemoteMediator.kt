package com.synaxis.android.chatapp.core.common.remote_mediator

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.database.AppDatabase
import com.synaxis.android.chatapp.core.database.remote_keys.ItemType
import com.synaxis.android.chatapp.core.database.remote_keys.RemoteKeys
import com.synaxis.android.chatapp.core.database.remote_keys.RemoteKeysDao


@OptIn(ExperimentalPagingApi::class)
abstract class BaseRemoteMediator<Entity : Any>(
    private val remoteKeysDao: RemoteKeysDao,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, Entity>() {

    abstract val itemType: ItemType
    abstract suspend fun fetch(
        cursor: String? = null,
        limit: Int = 10
    ): ApiResult<PagingResponse<Entity>>

    abstract fun entityId(entity: Entity): String
    abstract fun localDatasource(): PagingLocalDatasource<Entity>

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Entity>
    ): MediatorResult {
        if (loadType == LoadType.PREPEND) {
            return MediatorResult.Success(true)
        }
        val cursor = resolveCursor(loadType, state)
        return when (val response = fetch(cursor, state.config.pageSize)) {
            ApiResult.Empty -> MediatorResult.Success(true)
            is ApiResult.Error -> MediatorResult.Error(Exception(response.message))
            is ApiResult.Success -> {
                val items = response.data.items
                val endOfPaginationReached = !response.data.hasMore || items.isEmpty()
                persist(items, response.data.nextCursor, loadType)
                MediatorResult.Success(endOfPaginationReached)
            }
        }
    }

    private suspend fun persist(
        items: List<Entity>,
        nextCursor: String?,
        loadType: LoadType
    ) {
        appDatabase.withTransaction {
            if (loadType == LoadType.REFRESH) {
                localDatasource().deleteAll()
                remoteKeysDao.deleteAll(itemType = itemType)
            }

            val prevCursor: String? = if (loadType == LoadType.REFRESH) {
                items.firstOrNull()
                    ?.let { remoteKeysDao.getRemoteKeyForItem(entityId(it))?.prevCursor }
            } else null

            val keys = items.map { item ->
                RemoteKeys(
                    itemId = entityId(item),
                    nextCursor = nextCursor,
                    itemType = itemType,
                    prevCursor = prevCursor
                )
            }
            remoteKeysDao.insertAll(keys)
            Log.d("Remote_Keys", remoteKeysDao.getRemoteKeys().toString())
            localDatasource().insertAll(items)
        }
    }

    private suspend fun resolveCursor(
        loadType: LoadType,
        state: PagingState<Int, Entity>
    ): String? {
        return when (loadType) {
            LoadType.REFRESH -> null
            LoadType.APPEND -> lastItemCursor(state)
            LoadType.PREPEND -> null
        }
    }
    private suspend fun lastItemCursor(state: PagingState<Int, Entity>): String? {
        val item =
            state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull() ?: return null
        return remoteKeysDao.getRemoteKeyForItem(entityId(item))?.nextCursor
    }

}

