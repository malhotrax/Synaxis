package com.synaxis.android.chatapp.feature.user.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.feature.user.domain.use_case.UserUseCase
import com.synaxis.android.chatapp.feature.user.presentation.search.event.SearchEvent
import com.synaxis.android.chatapp.feature.user.presentation.search.state.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.QueryChanged -> onQueryChanged(event.query)
            SearchEvent.Search -> onSearch()
            is SearchEvent.SendFriendRequest -> onSendFriendRequest(event.username)
        }
    }

    private fun onQueryChanged(query: String) {
        _state.update { it.copy(query = query) }
    }

    private fun onSendFriendRequest(username: String) {
    }

    private fun onSearch() {
        if (_state.value.query.isBlank()) return
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = userUseCase.searchUser(_state.value.query.trim(), limit = 20)
            _state.update { it.copy(isLoading = false) }
            when (result) {
                ApiResult.Empty -> _state.update { it.copy(errorMessage = "No user found") }
                is ApiResult.Error -> _state.update { it.copy(errorMessage = result.message) }
                is ApiResult.Success -> {
                    _state.update { it.copy(result = result.data.users) }
                }
            }
        }
    }


}