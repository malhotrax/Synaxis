package com.synaxis.android.chatapp.feature.user.data.di

import com.synaxis.android.chatapp.feature.user.data.repository.UserRepositoryImpl
import com.synaxis.android.chatapp.feature.user.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract  class UserBindModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository
}