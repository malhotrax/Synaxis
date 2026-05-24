package com.synaxis.android.chatapp.core.common.remote_mediator

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
import javax.inject.Inject


@OptIn(ExperimentalPagingApi::class)
abstract class BaseRemoteMediator<Entity : Any> @Inject constructor(
    private val remoteKeysDao: RemoteKeysDao,
    private val appDatabase: AppDatabase
) : RemoteMediator<String, Entity>() {

    abstract val itemType: ItemType
    abstract suspend fun fetch(cursor: String? = null, limit: Int): ApiResult<PagingResponse<Entity>>
    abstract fun entityId(entity: Entity): String
    abstract fun localDatasource(): PagingLocalDatasource<Entity>

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<String, Entity>
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
                persist(items, response.data.nextCursor, loadType)
                MediatorResult.Success(response.data.hasMore)
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
            localDatasource().insertAll(items)
        }
    }

    private suspend fun resolveCursor(
        loadType: LoadType,
        state: PagingState<String, Entity>
    ): String? {
        return when (loadType) {
            LoadType.REFRESH -> closestCursorToAnchor(state)
            LoadType.APPEND -> lastItemCursor(state)
            LoadType.PREPEND -> null
        }
    }

    private suspend fun lastItemCursor(state: PagingState<String, Entity>): String? {
        val item =
            state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull() ?: return null
        return remoteKeysDao.getRemoteKeyForItem(entityId(item))?.nextCursor
    }

    private suspend fun closestCursorToAnchor(state: PagingState<String, Entity>): String? {
        val closestItem =
            state.anchorPosition?.let { state.closestItemToPosition(it) } ?: return null
        return remoteKeysDao.getRemoteKeyForItem(entityId(closestItem))?.prevCursor
    }

}

