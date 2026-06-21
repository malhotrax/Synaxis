package com.synaxis.android.chatapp.navigation.route

import androidx.navigation3.runtime.NavKey
import com.synaxis.android.chatapp.feature.chat.domain.model.Chat
import kotlinx.serialization.Serializable

@Serializable
sealed class Routes: NavKey {

    @Serializable
    object  Splash: Routes()
    @Serializable
    data class  Conversation(
        val chat: Chat
    ): Routes()

    @Serializable
    object Friends: Routes()

    @Serializable
    object Chats : Routes()

    @Serializable
    object Profile: Routes()
    @Serializable
    object UpdateUsername: Routes()

    @Serializable
    object UpdateFullName : Routes()
    @Serializable
    object Search: Routes()
    @Serializable
    object Home: Routes()

    @Serializable
    object ForgetPassword: Routes()
    @Serializable
    object Login: Routes()

    @Serializable
    object SignUp: Routes()
}