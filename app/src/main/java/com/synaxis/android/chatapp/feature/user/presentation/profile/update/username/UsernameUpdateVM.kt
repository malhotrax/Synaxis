package com.synaxis.android.chatapp.feature.user.presentation.profile.update.username

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.common.util.Validator
import com.synaxis.android.chatapp.feature.user.domain.use_case.UserUseCase
import com.synaxis.android.chatapp.feature.user.presentation.profile.update.username.event.UsernameUpdateEvent
import com.synaxis.android.chatapp.feature.user.presentation.profile.update.username.event.UsernameUpdateUiEvent
import com.synaxis.android.chatapp.feature.user.presentation.profile.update.username.state.UsernameUpdateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsernameUpdateVM @Inject constructor(
    private val userUseCase: UserUseCase
): ViewModel() {
    private val _state = MutableStateFlow(UsernameUpdateState())
    val state = _state.asStateFlow()

    private val _uiState = MutableSharedFlow<UsernameUpdateUiEvent>()
    val uiState = _uiState.asSharedFlow()

    fun onEvent(event: UsernameUpdateEvent) {
        when(event) {
            UsernameUpdateEvent.NavigateBack -> onNavigateBack()
            UsernameUpdateEvent.Update -> onUpdate()
            is UsernameUpdateEvent.UsernameChanged -> onUsernameChanged(event.username)
        }
    }

    private fun onUpdate() {
        val isUsernameValid = Validator.isUsernameValid(_state.value.username)
        _state.update { it.copy(isUsernameValid = isUsernameValid) }
        if(!isUsernameValid) return
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = userUseCase.updateUsername(_state.value.username)
            _state.update { it.copy(isLoading = false) }
            _state.update { current ->
                when(result) {
                    ApiResult.Empty -> current.copy(errorMessage = "Something went wrong")
                    is ApiResult.Error -> current.copy(errorMessage = result.message)
                    is ApiResult.Success -> current.copy(successMessage = result.data.message)
                }
            }

        }
    }

    private fun onUsernameChanged(username: String) {
        _state.update {
            it.copy(username = username)
        }
    }
    private fun onNavigateBack() {
        viewModelScope.launch {
            _uiState.emit(UsernameUpdateUiEvent.NavigateBack)
        }
    }
}