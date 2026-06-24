package com.synaxis.android.chatapp.feature.user.domain.use_case

import com.synaxis.android.chatapp.feature.user.domain.repository.UserRepository
import javax.inject.Inject

class Logout @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() {
        return userRepository.logout()
    }
}