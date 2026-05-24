package com.synaxis.android.chatapp.feature.user.domain.use_case

import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.common.user.User
import com.synaxis.android.chatapp.feature.user.domain.repository.UserRepository
import javax.inject.Inject

class GetCurrentUser  @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator  fun invoke(): ApiResult<User> {
        return userRepository.getCurrentUser()
    }
}