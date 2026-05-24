package com.synaxis.android.chatapp.core.database.remote_keys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(keys: List<RemoteKeys>)

    @Query("DELETE FROM remote_keys WHERE item_type = :itemType")
    suspend fun deleteAll(itemType: ItemType)

    @Query("SELECT * FROM remote_keys WHERE item_id =:itemId")

    suspend fun getRemoteKeyForItem(itemId: String): RemoteKeys?

}