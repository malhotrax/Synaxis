package com.synaxis.android.chatapp.feature.user.presentation.profile.update.full_name

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.feature.user.domain.use_case.UserUseCase
import com.synaxis.android.chatapp.feature.user.presentation.profile.update.full_name.event.FullNameUpdateEvent
import com.synaxis.android.chatapp.feature.user.presentation.profile.update.full_name.event.FullNameUpdateUiEvent
import com.synaxis.android.chatapp.feature.user.presentation.profile.update.full_name.state.FullNameUpdateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FullNameUpdateVM @Inject constructor(
    private val userUseCase: UserUseCase
): ViewModel() {
    private val _state = MutableStateFlow(FullNameUpdateState())
    val state = _state.asStateFlow()

    private val _uiState = MutableSharedFlow<FullNameUpdateUiEvent>()
    val uiState = _uiState.asSharedFlow()

    fun onEvent(event: FullNameUpdateEvent) {
        when(event) {
            FullNameUpdateEvent.NavigateBack -> onNavigateBack()
            FullNameUpdateEvent.Update -> onUpdate()
            is FullNameUpdateEvent.FullNameChanged -> onFullNameChanged(event.fullName)
        }
    }

    private fun onUpdate() {
        val isFullNameValid = _state.value.fullName.isNotBlank() // Simple validation for now
        _state.update { it.copy(isFullNameValid = isFullNameValid) }
        if(!isFullNameValid) return
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = userUseCase.updateFullName(_state.value.fullName)
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

    private fun onFullNameChanged(fullName: String) {
        _state.update {
            it.copy(fullName = fullName)
        }
    }

    private fun onNavigateBack() {
        viewModelScope.launch {
            _uiState.emit(FullNameUpdateUiEvent.NavigateBack)
        }
    }
}
