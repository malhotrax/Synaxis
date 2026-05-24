package com.synaxis.android.chatapp.feature.friends.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.synaxis.android.chatapp.feature.chat.domain.use_case.ChatUseCase
import com.synaxis.android.chatapp.feature.friends.domain.model.Friend
import com.synaxis.android.chatapp.feature.friends.domain.use_case.FriendUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FriendsVM @Inject constructor(
    private val friendsUseCases: FriendUseCases,
    private val chatUseCase: ChatUseCase
): ViewModel() {

    val friends: Flow<PagingData<Friend>> = friendsUseCases
        .getFriends()
        .cachedIn(viewModelScope)

    fun onEvent(event: FriendsEvent) {
        when(event) {
            is FriendsEvent.OpenChat -> onOpenChat(event.userId)
            is FriendsEvent.UnFriend -> onUnFriend(event.userId)
        }
    }

    private fun onUnFriend(userId: String) {}
    private fun onOpenChat(userId: String) {}
}