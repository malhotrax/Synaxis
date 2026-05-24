package com.synaxis.android.chatapp.feature.auth.data.di

import com.synaxis.android.chatapp.core.datastore.session.SessionDatasource
import com.synaxis.android.chatapp.di.PublicRetrofit
import com.synaxis.android.chatapp.feature.auth.data.remote.api.AuthApi
import com.synaxis.android.chatapp.feature.auth.domain.repository.AuthRepository
import com.synaxis.android.chatapp.feature.auth.domain.use_case.AuthUseCase
import com.synaxis.android.chatapp.feature.auth.domain.use_case.Login
import com.synaxis.android.chatapp.feature.auth.domain.use_case.SignUp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthProvideModule {

    @Provides
    @Singleton
    fun provideAuthApi(@PublicRetrofit retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideAuthUseCase(
        authRepository: AuthRepository,
        sessionDatasource: SessionDatasource
    ): AuthUseCase = AuthUseCase(
        login = Login(
            authRepository = authRepository
        ),
        signUp = SignUp(
            authRepository = authRepository
        )
    )

}