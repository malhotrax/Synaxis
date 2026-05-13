package com.synaxis.android.chatapp.feature.user.presentation.profile

import androidx.lifecycle.ViewModel
import com.synaxis.android.chatapp.feature.user.domain.use_case.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileVM @Inject constructor(
    private val userUseCase: UserUseCase
): ViewModel() {
}