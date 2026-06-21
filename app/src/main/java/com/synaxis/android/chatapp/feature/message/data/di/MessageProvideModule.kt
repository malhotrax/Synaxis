package com.synaxis.android.chatapp.feature.message.data.di

import com.synaxis.android.chatapp.core.database.AppDatabase
import com.synaxis.android.chatapp.di.AuthRetrofit
import com.synaxis.android.chatapp.feature.message.data.local.dao.MessageDao
import com.synaxis.android.chatapp.feature.message.data.remote.api.MessageApi
import com.synaxis.android.chatapp.feature.message.domain.repository.MessageRepository
import com.synaxis.android.chatapp.feature.message.domain.use_case.DeleteMessage
import com.synaxis.android.chatapp.feature.message.domain.use_case.GetMessages
import com.synaxis.android.chatapp.feature.message.domain.use_case.JoinChat
import com.synaxis.android.chatapp.feature.message.domain.use_case.LeaveChat
import com.synaxis.android.chatapp.feature.message.domain.use_case.MessageUseCase
import com.synaxis.android.chatapp.feature.message.domain.use_case.SendMessage
import com.synaxis.android.chatapp.feature.message.domain.use_case.UpdateMessage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MessageProvideModule {

    @Provides
    @Singleton
    fun provideMessageDao(appDatabase: AppDatabase): MessageDao = appDatabase.messageDao()

    @Provides
    @Singleton
    fun provideMessageApi(@AuthRetrofit retrofit: Retrofit): MessageApi = retrofit.create(MessageApi::class.java)

    @Provides
    @Singleton
    fun provideMessageUseCase(
        messageRepository: MessageRepository
    ): MessageUseCase = MessageUseCase(
        getMessages = GetMessages(messageRepository),
        sendMessage = SendMessage(messageRepository),
        updateMessage = UpdateMessage(messageRepository),
        deleteMessage = DeleteMessage(messageRepository),
        leaveChat = LeaveChat(messageRepository),
        joinChat = JoinChat(messageRepository)
    )
}