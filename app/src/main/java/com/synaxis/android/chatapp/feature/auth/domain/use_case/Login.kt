package com.synaxis.android.chatapp.feature.auth.domain.use_case

import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.datastore.session.SessionDatasource
import com.synaxis.android.chatapp.feature.auth.domain.model.AuthUser
import com.synaxis.android.chatapp.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class Login @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): ApiResult<Nothing> {
        return authRepository.login(email = email, password = password)
    }
}