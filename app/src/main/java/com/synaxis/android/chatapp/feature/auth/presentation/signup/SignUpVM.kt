package com.synaxis.android.chatapp.feature.auth.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.common.util.Validator
import com.synaxis.android.chatapp.feature.auth.domain.use_case.AuthUseCase
import com.synaxis.android.chatapp.feature.auth.presentation.signup.event.SignUpEvent
import com.synaxis.android.chatapp.feature.auth.presentation.signup.event.SignUpUiEvent
import com.synaxis.android.chatapp.feature.auth.presentation.signup.event.SignUpUiEvent.*
import com.synaxis.android.chatapp.feature.auth.presentation.signup.state.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpVM @Inject constructor(
    private val authUseCase: AuthUseCase
): ViewModel() {
    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    private val _uiState = MutableSharedFlow<SignUpUiEvent>()
    val uiState = _uiState.asSharedFlow()

    fun onEvent(event: SignUpEvent) {
        when(event) {
            is SignUpEvent.ConfirmPasswordChanged -> onConfirmPasswordChanged(event.confirmPassword)
            is SignUpEvent.EmailChanged -> onEmailChanged(event.email)
            SignUpEvent.NavigateToLogin -> onNavigateToLogin()
            is SignUpEvent.PasswordChanged -> onPasswordChanged(event.password)
            SignUpEvent.SignUp -> onSignUp()
            SignUpEvent.ToggleConfirmedPasswordVisibility -> onToggleConfirmedPasswordVisibility()
            SignUpEvent.TogglePasswordVisibility -> onTogglePasswordVisibility()
            is SignUpEvent.UsernameChanged -> onUsernameChanged(event.username)
        }
    }
    private fun sendUiEvent(event: SignUpUiEvent) {
        viewModelScope.launch {
            _uiState.emit(event)
        }
    }

    private fun onToggleConfirmedPasswordVisibility() {
        _state.update { it.copy(showConfirmedPassword = !it.showConfirmedPassword) }
    }
    private fun onConfirmPasswordChanged(confirmedPassword: String) {
        _state.update { it.copy(confirmPassword = confirmedPassword) }
    }
    private fun onTogglePasswordVisibility() {
        _state.update { it.copy(showPassword = !it.showPassword) }
    }

    private fun onSignUp() {
        val isEmailValid = Validator.isEmailValid(_state.value.email)
        val isPasswordValid = Validator.isPasswordValid(_state.value.password)
        val isUsernameValid = Validator.isUsernameValid(_state.value.username)
        val passwordConfirmed = _state.value.password == _state.value.confirmPassword
        _state.update { it.copy(
            isEmailValid = isEmailValid,
            isPasswordValid = isPasswordValid,
            isConfirmPasswordValid = passwordConfirmed,
            isUsernameValid = isUsernameValid
        ) }

        if(!isUsernameValid || !isEmailValid || !isPasswordValid || !passwordConfirmed) return
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = authUseCase.signUp(email = _state.value.email, username = _state.value.username, password = _state.value.password)
            _state.update { it.copy(isLoading = false) }
            when(result) {
                is ApiResult.Error -> sendUiEvent(ShowSnackBar(result.message))
                is ApiResult.Success -> Unit
                ApiResult.Empty -> sendUiEvent(SignUpUiEvent.NavigateToHome)
            }

        }
    }
    private fun onEmailChanged(email: String) {
        _state.update { it.copy(email = email) }
    }
    private fun onUsernameChanged(username: String) {
        _state.update { it.copy(username = username) }
    }

    private fun onPasswordChanged(password: String) {
        _state.update { it.copy(password = password) }
    }
    private fun onNavigateToLogin() {
        sendUiEvent(SignUpUiEvent.NavigateToLogin)
    }
}