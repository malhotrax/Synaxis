package com.synaxis.android.chatapp.feature.friends.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.feature.chat.domain.use_case.ChatUseCase
import com.synaxis.android.chatapp.feature.friends.domain.model.Friend
import com.synaxis.android.chatapp.feature.friends.domain.use_case.FriendUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsVM @Inject constructor(
    private val friendsUseCases: FriendUseCases,
    private val chatUseCase: ChatUseCase
): ViewModel() {

    private val _state = MutableStateFlow(FriendsState())
    val state = _state.asStateFlow()

    private val _uiState = MutableSharedFlow<FriendsUiEvent>()
    val uiState = _uiState.asSharedFlow()

    val friends: Flow<PagingData<Friend>> = friendsUseCases
        .getFriends()
        .cachedIn(viewModelScope)


    fun onEvent(event: FriendsEvent) {
        when(event) {
            is FriendsEvent.OpenChat -> onOpenChat(event.userId)
            is FriendsEvent.UnFriend -> onUnFriend(event.userId)
            FriendsEvent.LoadFriendRequest -> onLoadFriendRequest()
            is FriendsEvent.AcceptRequest -> onAcceptRequest(event.id)
            is FriendsEvent.RejectRequest -> onRejectRequest(event.id)
            FriendsEvent.ClearError -> onClearError()
        }
    }

    private fun onClearError() {
        _state.update { it.copy(error = null, successMessage = null) }
    }

    private fun sendUiEvent(event: FriendsUiEvent) {
        viewModelScope.launch {
            _uiState.emit(event)
        }
    }
    private fun onUnFriend(userId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = friendsUseCases.removeFriend(userId)
            _state.update { it.copy(isLoading = false) }
            when(result) {
                ApiResult.Empty -> _state.update { it.copy(error = "Something went wrong") }
                is ApiResult.Error -> _state.update { it.copy(error = result.message) }
                is ApiResult.Success -> _state.update { it.copy(successMessage = result.data.message) }
            }
        }
    }
    private fun onOpenChat(userId: String) {}

    private fun onLoadFriendRequest() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val requests = friendsUseCases.getFriendRequests()
            _state.update { it.copy(isLoading = false) }
            when(requests) {
                ApiResult.Empty -> Unit
                is ApiResult.Error -> _state.update { it.copy(error = requests.message ) }
                is ApiResult.Success -> {
                    val items = requests.data.requests
                    _state.update { it.copy(
                        requests = items
                    ) }
                }
            }
        }
    }

    private fun onAcceptRequest(id: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = friendsUseCases.acceptFriendRequest(id)
            _state.update { it.copy(isLoading = false) }
            when(result) {
                ApiResult.Empty -> _state.update { it.copy(error = "Something went wrong") }
                is ApiResult.Error -> _state.update { it.copy(error = result.message) }
                is ApiResult.Success -> _state.update { it.copy(successMessage = result.data.message) }
            }
        }
    }
    private fun onRejectRequest(id: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = friendsUseCases.rejectFriendRequest(id)
            _state.update { it.copy(isLoading = false) }
            when(result) {
                ApiResult.Empty -> _state.update { it.copy(error = "Something went wrong") }
                is ApiResult.Error -> _state.update { it.copy(error = result.message) }
                is ApiResult.Success -> _state.update { it.copy(successMessage = result.data.message) }
            }
        }
    }
}

