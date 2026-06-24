package com.synaxis.android.chatapp.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.feature.friends.domain.use_case.FriendUseCases
import com.synaxis.android.chatapp.feature.user.domain.use_case.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchVM @Inject constructor(
    private val userUseCase: UserUseCase,
    private val friendUseCases: FriendUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state
                .map { it.query }
                .distinctUntilChanged()
                .debounce(400)
                .collectLatest { query ->
                    if (query.isBlank()) return@collectLatest
                    search(query)
                }
        }
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.QueryChanged -> onQueryChanged(event.query)
            is SearchEvent.SendMessage -> onSendMessage(event.userId)
            is SearchEvent.SendRequest -> onSendRequest(event.userId)
            SearchEvent.ClearMessages -> onClearMessages()
        }
    }

    private fun onClearMessages() {
        _state.update { it.copy(errorMessage = null, successMessage = null) }
    }

    private suspend fun search(query: String) {
        _state.update { it.copy(isLoading = true) }
        when (val result = userUseCase.searchUser(query)) {
            ApiResult.Empty -> _state.update { it.copy(isLoading = false, users = emptyList()) }
            is ApiResult.Error -> _state.update {
                it.copy(
                    errorMessage = result.message,
                    isLoading = false
                )
            }

            is ApiResult.Success -> {
                val users = result.data.users
                _state.update { it.copy(users = users, isLoading = false) }
            }
        }
    }


    private fun onSendMessage(userId: String) {}

    private fun onQueryChanged(query: String) {
        _state.update { it.copy(query = query) }
    }

    private fun onSendRequest(userId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = friendUseCases.sendFriendRequest(userId)
            _state.update { it.copy(isLoading = false) }
            when (result) {
                ApiResult.Empty -> _state.update { it.copy(errorMessage = "Something went wrong") }
                is ApiResult.Error -> _state.update { it.copy(errorMessage = result.message) }
                is ApiResult.Success -> _state.update { it.copy(successMessage = result.data.message) }
            }
        }
    }
}