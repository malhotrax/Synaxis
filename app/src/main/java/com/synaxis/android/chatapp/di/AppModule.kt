package com.synaxis.android.chatapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.synaxis.android.chatapp.core.database.AppDatabase
import com.synaxis.android.chatapp.core.database.remote_keys.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRemoteKeyDao(appDatabase: AppDatabase): RemoteKeysDao = appDatabase.remoteKeyDao()
    @Singleton
    @Provides
    fun appScope() : CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @Singleton
    @Provides
    fun provideAppLocalDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "local_database",

            ).fallbackToDestructiveMigration(false)
        .build()
}