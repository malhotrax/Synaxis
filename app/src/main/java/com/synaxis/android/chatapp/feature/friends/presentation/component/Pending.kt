package com.synaxis.android.chatapp.feature.friends.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.synaxis.android.chatapp.feature.friends.presentation.FriendsEvent
import com.synaxis.android.chatapp.feature.friends.presentation.FriendsState
import com.synaxis.android.chatapp.ui.component.CenteredTextMessage
import com.synaxis.android.chatapp.ui.component.FooterError
import com.synaxis.android.chatapp.ui.component.ProgressBarIndicator
import com.synaxis.android.chatapp.ui.component.RequestItem

@Composable
fun Pending(
    modifier: Modifier = Modifier,
    state: FriendsState,
    onEvent: (FriendsEvent) -> Unit
) {
    LaunchedEffect(Unit) {
        onEvent(FriendsEvent.LoadFriendRequest)
    }
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .fillMaxHeight()
    ) {
        when {
            state.isLoading -> ProgressBarIndicator()
            !state.error.isNullOrBlank() -> {
                FooterError(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    message = state.error,
                    retry = { onEvent(FriendsEvent.LoadFriendRequest) },
                    onDismiss = {onEvent(FriendsEvent.ClearError)}

                )
            }
            state.requests.isEmpty() -> {
                CenteredTextMessage(
                    text = "No requests found"
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    items(state.requests, key = { it.id }) { request ->
                        RequestItem(
                            username = request.sender.username,
                            fullName = request.sender.fullName,
                            avatarUrl = request.sender.avatarUrl,
                            acceptRequest = { onEvent(FriendsEvent.AcceptRequest(request.id)) },
                            rejectRequest = { onEvent(FriendsEvent.RejectRequest(request.id)) }
                        )
                    }
                }
            }
        }
    }
}