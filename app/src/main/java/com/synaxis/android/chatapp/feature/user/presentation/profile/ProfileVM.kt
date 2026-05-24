package com.synaxis.android.chatapp.feature.user.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.datastore.session.SessionDatasource
import com.synaxis.android.chatapp.feature.user.domain.use_case.UserUseCase
import com.synaxis.android.chatapp.feature.user.presentation.profile.event.ProfileEvent
import com.synaxis.android.chatapp.feature.user.presentation.profile.state.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileVM @Inject constructor(
    private val userUseCase: UserUseCase,
    private val sessionDatasource: SessionDatasource
): ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()
    fun onEvent(event: ProfileEvent) {
        when(event) {
            ProfileEvent.Logout -> onLogout()
        }
    }

    init {

    }
    private fun onLogout() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = userUseCase.logout()
            when(result) {
                is ApiResult.Success -> {
                    sessionDatasource.clear()
                }
                else -> {
                    Unit
                }
            }
        }
    }
}