package com.synaxis.android.chatapp.feature.user.presentation.update.username

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.feature.user.domain.use_case.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UpdateUsernameVM @Inject constructor(
    private val userUseCase: UserUseCase
): ViewModel() {

    private val _state = MutableStateFlow(UpdateUsernameState())
    val state = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UpdateUsernameUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(e: UpdateUsernameEvent) {
        when(e) {
            UpdateUsernameEvent.ClearErrorMessage -> onClearErrorMessage()
            UpdateUsernameEvent.UpdateUsername -> onUpdateUsername()
            is UpdateUsernameEvent.UsernameChanged -> onUsernameChanged(e.username)
        }
    }

    private fun sendUiEvent(e: UpdateUsernameUiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(e)
        }
    }
    private fun onUpdateUsername() {
        val username = _state.value.username
        if(username.isBlank()) {
            _state.update { it.copy(errorMessage = "Please enter valid username") }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = userUseCase.updateUsername(username)
            _state.update { it.copy(isLoading = false) }
            when(result) {
                ApiResult.Empty -> sendUiEvent(UpdateUsernameUiEvent.ShowSnackBar("Something went wrong"))
                is ApiResult.Error -> _state.update { it.copy(errorMessage = result.message) }
                is ApiResult.Success -> sendUiEvent(UpdateUsernameUiEvent.ShowSnackBar(result.data.message))
            }
        }
    }
    private fun onUsernameChanged(username: String) {
        _state.update { it.copy(username = username) }
    }

    private fun onClearErrorMessage() {
        _state.update { it.copy(errorMessage = null) }
    }
}