package com.synaxis.android.chatapp.feature.friends.data.di

import com.synaxis.android.chatapp.core.database.AppDatabase
import com.synaxis.android.chatapp.di.AuthRetrofit
import com.synaxis.android.chatapp.feature.friends.data.local.dao.FriendDao
import com.synaxis.android.chatapp.feature.friends.data.remote.api.FriendsApi
import com.synaxis.android.chatapp.feature.friends.domain.repository.FriendsRepository
import com.synaxis.android.chatapp.feature.friends.domain.use_case.AcceptFriendRequest
import com.synaxis.android.chatapp.feature.friends.domain.use_case.DeleteFriendRequest
import com.synaxis.android.chatapp.feature.friends.domain.use_case.FriendUseCases
import com.synaxis.android.chatapp.feature.friends.domain.use_case.GetFriendRequests
import com.synaxis.android.chatapp.feature.friends.domain.use_case.GetFriends
import com.synaxis.android.chatapp.feature.friends.domain.use_case.RejectFriendRequest
import com.synaxis.android.chatapp.feature.friends.domain.use_case.RemoveFriend
import com.synaxis.android.chatapp.feature.friends.domain.use_case.SendFriendRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FriendsProvideModule {

    @Provides
    @Singleton
    fun provideFriendDao(appDatabase: AppDatabase): FriendDao = appDatabase.friendDao()
    @Provides
    @Singleton
    fun provideFriendsApi(@AuthRetrofit retrofit: Retrofit): FriendsApi = retrofit.create(FriendsApi::class.java)

    @Provides
    @Singleton
    fun provideFriendUseCases(
        friendsRepository: FriendsRepository
    ): FriendUseCases = FriendUseCases(
        sendFriendRequest = SendFriendRequest(friendsRepository),
        acceptFriendRequest = AcceptFriendRequest(friendsRepository),
        removeFriend = RemoveFriend(friendsRepository),
        rejectFriendRequest = RejectFriendRequest(friendsRepository),
        deleteFriendRequest = DeleteFriendRequest(friendsRepository),
        getFriends = GetFriends(friendsRepository),
        getFriendRequests = GetFriendRequests(friendsRepository)
    )
}