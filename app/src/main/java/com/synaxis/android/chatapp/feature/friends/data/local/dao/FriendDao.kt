package com.synaxis.android.chatapp.feature.friends.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.synaxis.android.chatapp.feature.friends.data.local.entity.FriendEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FriendDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(friends: List<FriendEntity>)

    @Insert(onConflict = REPLACE)
    suspend fun insert(friend: FriendEntity)

    @Query("SELECT * FROM friends ORDER BY created_at DESC ")
    fun getFriends(): PagingSource<String, FriendEntity>

    @Query("DELETE FROM friends WHERE id = :id")
    suspend fun removeFriend(id: String)

    @Query("DELETE FROM friends")
    suspend fun deleteAll()


}