package com.synaxis.android.chatapp.feature.chat.data.di

import com.synaxis.android.chatapp.core.database.AppDatabase
import com.synaxis.android.chatapp.di.AuthRetrofit
import com.synaxis.android.chatapp.feature.chat.data.local.dao.ChatDao
import com.synaxis.android.chatapp.feature.chat.data.remote.api.ChatApi
import com.synaxis.android.chatapp.feature.chat.domain.repository.ChatRepository
import com.synaxis.android.chatapp.feature.chat.domain.use_case.ChatExists
import com.synaxis.android.chatapp.feature.chat.domain.use_case.ChatUseCase
import com.synaxis.android.chatapp.feature.chat.domain.use_case.CreateChat
import com.synaxis.android.chatapp.feature.chat.domain.use_case.GetChats
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatProvideModule {

    @Provides
    @Singleton
    fun provideChatDao(appDatabase: AppDatabase): ChatDao = appDatabase.chatDao()

    @Provides
    @Singleton
    fun provideChatApi(@AuthRetrofit retrofit:  Retrofit): ChatApi = retrofit.create(ChatApi::class.java)

    @Provides
    @Singleton
    fun provideChatUseCase(
        chatRepository: ChatRepository
    ): ChatUseCase = ChatUseCase(
        getChats = GetChats(chatRepository),
        createChat = CreateChat(chatRepository),
        chatExists = ChatExists(chatRepository)
    )

}