package com.synaxis.android.chatapp.feature.user.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.synaxis.android.chatapp.feature.user.data.local.entity.UserEntity


@Dao
interface UserDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(user: UserEntity)

    @Query("UPDATE user SET username = :username WHERE id = :id")
    suspend fun updateUsername(username: String, id: String)

    @Query("UPDATE user SET full_name = :fullName WHERE id = :id")
    suspend fun updateFullName(fullName: String, id: String)

    @Query("UPDATE user SET avatar_url = :avatarUrl WHERE id = :id")
    suspend fun updateAvatarUrl(avatarUrl: String, id: String)

    @Query("DELETE FROM user WHERE id = :id")
    suspend fun deleteAccount(id: String)

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUser(id: String): UserEntity?
}