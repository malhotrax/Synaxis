package com.synaxis.android.chatapp.core.network.connectivity

import kotlinx.coroutines.flow.Flow

interface NetworkConnectivityManager {
    fun isConnected(): Boolean
    fun observerConnectivity() : Flow<Boolean>
}