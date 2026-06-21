package com.synaxis.android.chatapp.core.socket

sealed class ConnectionState  {
    object  Connecting : ConnectionState()
    object Connected : ConnectionState()
    object Disconnected : ConnectionState()
    data class Error(val message: String) : ConnectionState()
}