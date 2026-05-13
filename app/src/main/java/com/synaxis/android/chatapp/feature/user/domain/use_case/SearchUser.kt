package com.synaxis.android.chatapp.feature.user.domain.use_case

import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.common.user.GetUser
import com.synaxis.android.chatapp.core.common.user.User
import com.synaxis.android.chatapp.feature.user.domain.model.GetUserResponse
import com.synaxis.android.chatapp.feature.user.domain.repository.UserRepository
import javax.inject.Inject

class SearchUser @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(query: String, limit: Int = 20): ApiResult<GetUserResponse> {
        return userRepository.searchUser(query, limit)
    }
}