package com.synaxis.android.chatapp.feature.auth.data.di

import com.synaxis.android.chatapp.feature.auth.data.repository.AuthRepositoryImpl
import com.synaxis.android.chatapp.feature.auth.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthBindModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

}