package com.synaxis.android.chatapp.feature.auth.domain.use_case

import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.datastore.session.SessionDatasource
import com.synaxis.android.chatapp.feature.auth.domain.model.AuthUser
import com.synaxis.android.chatapp.feature.auth.domain.model.RegisterUser
import com.synaxis.android.chatapp.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class SignUp @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        email: String,
        username: String,
        password: String
    ): ApiResult<Nothing> {
        val registerUser = RegisterUser(email = email, username = username, password = password)
        return authRepository.register(registerUser)
    }
}