package com.synaxis.android.chatapp.feature.user.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.datastore.session.SessionDatasource
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
class ProfileVM @Inject constructor(
    private val userUseCase: UserUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<ProfileUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(e: ProfileEvent) {
        when (e) {
            ProfileEvent.DeleteAccount -> onDeleteAccount()
            ProfileEvent.LogOut -> onLogout()
            ProfileEvent.NavigateToUpdateFullName -> onNavigateToUpdateFullName()
            ProfileEvent.NavigateToUpdateUsername -> onNavigateToUpdateUsername()
            ProfileEvent.LoadUser -> loadUser()
        }
    }

    private fun onNavigateToUpdateUsername() {
        sendUiEvent(ProfileUiEvent.NavigateToUpdateUsernameScreen)
    }

    private fun onNavigateToUpdateFullName() {
        sendUiEvent(ProfileUiEvent.NavigateToUpdateFullNameScreen)
    }
    private fun sendUiEvent(e: ProfileUiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(e)
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val result = userUseCase.getCurrentUser()) {
                ApiResult.Empty -> _state.update { it.copy(isLoading = false) }
                is ApiResult.Error -> _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }
                is ApiResult.Success -> _state.update {
                    it.copy(
                        isLoading = false,
                        user = result.data
                    )
                }
            }
        }
    }

    private fun onDeleteAccount() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val result = userUseCase.deleteAccount()) {
                ApiResult.Empty -> _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Something went wrong"
                    )
                }

                is ApiResult.Error -> _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }

                is ApiResult.Success -> _state.update {
                    it.copy(isLoading = false, successMessage = result.data.message)
                }
            }
        }
    }

    private fun onLogout() {
        viewModelScope.launch {
            userUseCase.logout()
        }
    }

}