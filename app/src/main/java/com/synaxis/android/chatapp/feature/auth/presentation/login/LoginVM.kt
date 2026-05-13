package com.synaxis.android.chatapp.feature.auth.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.common.util.Validator
import com.synaxis.android.chatapp.feature.auth.domain.use_case.AuthUseCase
import com.synaxis.android.chatapp.feature.auth.presentation.login.event.LoginEvent
import com.synaxis.android.chatapp.feature.auth.presentation.login.event.LoginUiEvent
import com.synaxis.android.chatapp.feature.auth.presentation.login.event.LoginUiEvent.*
import com.synaxis.android.chatapp.feature.auth.presentation.login.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()
    private val _uiEvent = MutableSharedFlow<LoginUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> onEmailChanged(event.email)
            LoginEvent.ForgetPassword -> onForgetPassword()
            LoginEvent.Login -> onLogin()
            LoginEvent.NavigateToForgetPassword -> onNavigateToForgetPassword()
            LoginEvent.NavigateToSignUp -> onNavigateToSignUp()
            is LoginEvent.PasswordChanged -> onPasswordChanged(event.password)
            LoginEvent.ToggleShowPassword -> onToggleShowPassword()
        }
    }

    private fun sendUiEvent(event: LoginUiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }

    private fun onEmailChanged(email: String) {
        _state.update {
            it.copy(email = email)
        }
    }

    private fun onForgetPassword() {
        sendUiEvent(LoginUiEvent.NavigateToForgetPassword)
    }

    private fun onPasswordChanged(password: String) {
        _state.update {
            it.copy(password = password)
        }
    }

    private fun onLogin() {
        val isEmailValid = Validator.isEmailValid(_state.value.email)
        val isPasswordValid = Validator.isPasswordValid(_state.value.password)
        _state.update {
            it.copy(
                isEmailValid = isEmailValid,
                isPasswordValid = isPasswordValid
            )
        }

        if (!isEmailValid || !isPasswordValid) return
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = authUseCase.login(_state.value.email, _state.value.password)
            Log.e("ApiResponse", result.toString())
            _state.update { it.copy(isLoading = false) }
            when (result) {
                is ApiResult.Error -> sendUiEvent(ShowSnackBar(result.message))
                is ApiResult.Success -> Unit
                ApiResult.Empty -> sendUiEvent(LoginUiEvent.NavigateToHome)
            }
        }
    }

    private fun onNavigateToForgetPassword() {
        sendUiEvent(LoginUiEvent.NavigateToForgetPassword)
    }

    private fun onToggleShowPassword() {
        _state.update {
            it.copy(showPassword = !it.showPassword)
        }
    }

    private fun onNavigateToSignUp() {
        sendUiEvent(LoginUiEvent.NavigateToSignUp)
    }
}