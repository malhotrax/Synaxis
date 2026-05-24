package com.synaxis.android.chatapp.feature.friends.data.di

import com.synaxis.android.chatapp.feature.friends.data.repository.FriendsRepositoryImpl
import com.synaxis.android.chatapp.feature.friends.domain.repository.FriendsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FriendsBindModule {

    @Binds
    @Singleton
    abstract fun bindFriendsRepository(
        impl: FriendsRepositoryImpl
    ): FriendsRepository
}