package com.synaxis.android.chatapp.feature.message.data.di

import com.synaxis.android.chatapp.feature.message.data.repository.MessageRepositoryImpl
import com.synaxis.android.chatapp.feature.message.domain.repository.MessageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MessageBindModule {

    @Binds
    @Singleton
    abstract fun bindMessageRepository(
        impl: MessageRepositoryImpl
    ): MessageRepository
}