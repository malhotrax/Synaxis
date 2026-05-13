package com.synaxis.android.chatapp.navigation.route

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class Routes: NavKey {


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