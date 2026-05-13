package com.synaxis.android.chatapp.di

import com.synaxis.android.chatapp.core.network.connectivity.NetworkConnectivityManager
import com.synaxis.android.chatapp.core.network.connectivity.NetworkConnectivityManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkBindModule {

    @Binds
    @Singleton
    abstract fun bindNetworkConnectivityManager(
        impl: NetworkConnectivityManagerImpl
    ): NetworkConnectivityManager
}