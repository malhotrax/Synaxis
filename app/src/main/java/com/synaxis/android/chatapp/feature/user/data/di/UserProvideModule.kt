package com.synaxis.android.chatapp.feature.user.data.di

import com.synaxis.android.chatapp.di.AuthRetrofit
import com.synaxis.android.chatapp.feature.user.data.remote.api.UserApi
import com.synaxis.android.chatapp.feature.user.domain.repository.UserRepository
import com.synaxis.android.chatapp.feature.user.domain.use_case.DeleteAccount
import com.synaxis.android.chatapp.feature.user.domain.use_case.GetCurrentUser
import com.synaxis.android.chatapp.feature.user.domain.use_case.Logout
import com.synaxis.android.chatapp.feature.user.domain.use_case.SearchUser
import com.synaxis.android.chatapp.feature.user.domain.use_case.UpdateAvatarUrl
import com.synaxis.android.chatapp.feature.user.domain.use_case.UpdateFullName
import com.synaxis.android.chatapp.feature.user.domain.use_case.UpdateUsername
import com.synaxis.android.chatapp.feature.user.domain.use_case.UserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserProvideModule {

    @Provides
    @Singleton
    fun provideUserApi(@AuthRetrofit retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideUserUseCase(
        userRepository: UserRepository
    ): UserUseCase = UserUseCase(
        deleteAccount = DeleteAccount(userRepository),
        logout = Logout(userRepository),
        updateUsername = UpdateUsername(userRepository),
        updateFullName = UpdateFullName(userRepository),
        updateAvatarUrl = UpdateAvatarUrl(userRepository),
        searchUser = SearchUser(userRepository),
        getCurrentUser = GetCurrentUser(userRepository)
    )
}