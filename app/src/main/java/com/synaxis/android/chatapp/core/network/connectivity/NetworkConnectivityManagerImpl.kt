package com.synaxis.android.chatapp.core.network.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class NetworkConnectivityManagerImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
): NetworkConnectivityManager {
    private val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
    override fun isConnected(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val cap = connectivityManager.getNetworkCapabilities(network) ?: return false
        return cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    override fun observerConnectivity(): Flow<Boolean> = callbackFlow {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                trySend(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED))
            }
            override fun onLost(network: Network) {
                super.onLost(network)
                trySend(false)
            }

            override fun onUnavailable() {
                super.onUnavailable()
                trySend(false)
            }
        }
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
        trySend(isConnected())
        awaitClose {
            connectivityManager.unregisterNetworkCallback( networkCallback)
        }

    }
}