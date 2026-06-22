package com.synaxis.android.chatapp.feature.chat.presentation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.synaxis.android.chatapp.core.common.util.DateTimeUtil.toIso
import com.synaxis.android.chatapp.feature.chat.domain.model.Chat
import com.synaxis.android.chatapp.feature.chat.presentation.components.ChatItem
import com.synaxis.android.chatapp.ui.component.Header
import com.synaxis.android.chatapp.ui.component.HintText
import com.synaxis.android.chatapp.ui.component.SwipeableSnackBar
import kotlinx.coroutines.flow.flowOf

@Composable
fun ChatListScreen(
    modifier: Modifier = Modifier,
    navigateToConversation: (Chat) -> Unit,
    viewModel: ChatListVM = hiltViewModel()
) {
    val chats = viewModel.chats.collectAsLazyPagingItems()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        viewModel.uiEvent.flowWithLifecycle(lifecycleOwner.lifecycle).collect { e ->
            when (e) {
                is ChatListUiEvent.NavigateToConversation -> navigateToConversation(e.chat)
            }
        }
    }
    ChatListScreen(
        chats = chats,
        onEvent = viewModel::onEvent

    )
}

@Composable
internal fun ChatListScreen(
    chats: LazyPagingItems<Chat>,
    onEvent: (ChatListEvent) -> Unit
) {
    val refreshState = chats.loadState.refresh
    val appendState = chats.loadState.append
    val isRefreshing = refreshState is LoadState.Loading
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(refreshState) {
        if (refreshState is LoadState.Error) {
            Log.e("ChatListScreen", refreshState.error.toString())
            snackBarHostState.showSnackbar(refreshState.error.message ?: "Something went wrong")
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Header(
                title = "Synaxis",
                onSearch = {
                    onEvent(ChatListEvent.SearchQueryChanged(it))
                }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.8f)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                PullToRefreshBox(
                    isRefreshing = isRefreshing,
                    onRefresh = {
                        chats.refresh()
                    }
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(count = chats.itemCount, key = chats.itemKey { it.id }) { index ->
                            val chat = chats[index]
                            chat?.let {
                                ChatItem(
                                    modifier = Modifier,
                                    avatarUrl = it.avatarUrl,
                                    name = it.name!!,
                                    lastMessage = it.lastMessage,
                                    lastActivity = it.lastActivity?.toIso(),
                                    navigateToConversation = {
                                        onEvent(ChatListEvent.NavigateToConversation(it))
                                    }
                                )
                            }
                        }
                    }
                }
                if (chats.itemCount == 0) {
                    HintText("No chats found")
                }
                SwipeableSnackBar(
                    hostState = snackBarHostState,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun ChatListScreenPreview() {
    val chats = flowOf(PagingData.empty<Chat>()).collectAsLazyPagingItems()
    ChatListScreen(
        chats = chats,
        onEvent = {}
    )
}