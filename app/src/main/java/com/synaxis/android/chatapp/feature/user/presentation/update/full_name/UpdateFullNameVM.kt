package com.synaxis.android.chatapp.feature.user.presentation.update.full_name

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
class UpdateFullNameVM @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UpdateFullNameState())
    val state = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UpdateFullNameUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(e: UpdateFullNameEvent) {
        when(e) {
            is UpdateFullNameEvent.NameChanged -> onNameChanged(e.name)
            UpdateFullNameEvent.UpdateName -> onUpdateName()
            UpdateFullNameEvent.ClearMessages -> onClearMessages()
            UpdateFullNameEvent.NavigateBack -> onNavigateBack()
        }
    }

    private fun onNavigateBack() {
        sendUiEvent(UpdateFullNameUiEvent.NavigateBack)
    }

    private fun sendUiEvent(e: UpdateFullNameUiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(e)
        }
    }
    private fun onNameChanged(name: String) {
        _state.update { it.copy(fullName =  name) }
    }

    private fun onClearMessages() {
        _state.update { it.copy(errorMessage = null) }
    }
    private fun onUpdateName() {
        val fullName = _state.value.fullName
        if(fullName.trim().isBlank()) {
            _state.update { it.copy(errorMessage = "Please enter valid fullName") }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = userUseCase.updateFullName(fullName)
            _state.update { it.copy(isLoading = false) }
            when(result) {
                ApiResult.Empty -> _state.update { it.copy(errorMessage = "Something went wrong") }
                is ApiResult.Error -> _state.update { it.copy(errorMessage = result.message) }
                is ApiResult.Success -> {
                    sendUiEvent(UpdateFullNameUiEvent.ShowSnackBar(result.data.message))
                    _state.update { it.copy(errorMessage = null) }
                }
            }
        }
    }
}