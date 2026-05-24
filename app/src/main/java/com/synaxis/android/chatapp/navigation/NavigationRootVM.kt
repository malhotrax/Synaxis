package com.synaxis.android.chatapp.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synaxis.android.chatapp.core.datastore.AuthTokenProvider
import com.synaxis.android.chatapp.core.datastore.session.SessionDatasource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationRootVM @Inject constructor(
    private val sessionDatasource: SessionDatasource
) : ViewModel() {

    val state: StateFlow<AuthState> = sessionDatasource.accessToken()
        .map { token -> AuthState(isLoggedIn = !token.isNullOrBlank(), isLoading = false)  }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AuthState(isLoading = true)
        )
}