package com.synaxis.android.chatapp.feature.chat.data.di

import com.synaxis.android.chatapp.feature.chat.data.repository.ChatRepositoryImpl
import com.synaxis.android.chatapp.feature.chat.domain.repository.ChatRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ChatBindModule {

    @Binds
    @Singleton
    abstract fun bindChatRepository(
        impl: ChatRepositoryImpl
    ): ChatRepository
}