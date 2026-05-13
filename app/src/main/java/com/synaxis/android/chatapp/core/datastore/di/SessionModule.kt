package com.synaxis.android.chatapp.core.datastore.di

import com.synaxis.android.chatapp.core.datastore.session.SessionDataSourceImpl
import com.synaxis.android.chatapp.core.datastore.session.SessionDatasource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SessionModule {
    @Binds
    @Singleton
    abstract fun bindSessionDatasource(
        impl: SessionDataSourceImpl
    ): SessionDatasource
}

