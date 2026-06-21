package com.synaxis.android.chatapp.feature.chat.domain.use_case

data class ChatUseCase(
    val getChats: GetChats,
    val createChat: CreateChat,
    val chatExists: ChatExists
)
