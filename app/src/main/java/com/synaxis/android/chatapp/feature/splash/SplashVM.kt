package com.synaxis.android.chatapp.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synaxis.android.chatapp.core.datastore.session.SessionDatasource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashVM @Inject constructor(
    private val sessionDatasource: SessionDatasource
) : ViewModel() {

    val state: StateFlow<AuthState> = sessionDatasource.accessToken()
        .map { token ->
            AuthState(isLoggedIn = !token.isNullOrEmpty(), isLoading = false)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AuthState(isLoading = true)
        )
}