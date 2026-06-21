package com.synaxis.android.chatapp.feature.message.presentation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import com.synaxis.android.chatapp.feature.message.domain.model.Message
import com.synaxis.android.chatapp.feature.message.presentation.components.ConversationHeader
import com.synaxis.android.chatapp.feature.message.presentation.components.MessageBox
import com.synaxis.android.chatapp.feature.message.presentation.components.MessageItem
import com.synaxis.android.chatapp.ui.component.SwipeableSnackBar
import kotlinx.coroutines.flow.flowOf

@Composable
fun ConversationScreen(
    modifier: Modifier = Modifier,
    chat: Chat,
    navigateBack: () -> Unit,
    viewModel: ConversationVM = hiltViewModel<ConversationVM, ConversationVM.Factory> { factory ->
        factory.create(chat)
    }
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val message = viewModel.messages.collectAsLazyPagingItems()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.onEvent(ConversationEvent.JoinChat)

        viewModel.uiState.flowWithLifecycle(lifecycleOwner.lifecycle).collect { e ->
            when (e) {
                ConversationUiEvent.NavigateBack -> navigateBack()
                is ConversationUiEvent.ShowSnackBar -> snackBarHostState.showSnackbar(e.message)
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.onEvent(ConversationEvent.LeaveChat)
        }
    }
    ConversationScreen(
        chat = chat,
        modifier = modifier,
        state = state,
        onEvent = viewModel::onEvent,
        messages = message,
        snackBarHostState = snackBarHostState
    )
}


@Composable
internal fun ConversationScreen(
    chat: Chat,
    messages: LazyPagingItems<Message>,
    modifier: Modifier = Modifier,
    state: ConversationState,
    onEvent: (ConversationEvent) -> Unit,
    snackBarHostState: SnackbarHostState
) {
    val refreshState = messages.loadState.refresh
    val appendState = messages.loadState.append

    LaunchedEffect(refreshState, appendState) {
        if(refreshState is LoadState.Error) {
            Log.e("ConversationVM", refreshState.error.toString())
            snackBarHostState.showSnackbar(refreshState.error.message ?: "Something went wrong")
        }
        if(appendState is LoadState.Error) {
            Log.e("ConversationVM", appendState.error.toString())
            snackBarHostState.showSnackbar(appendState.error.message ?: "Something went wrong")
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
    ) {
        ConversationHeader(
            modifier = Modifier,
            name = chat.name ?: "Unknown",
            lastActivity = chat.lastActivity?.toIso(),
            avatarUrl = state.avatarUrl,
            navigateBack = { onEvent(ConversationEvent.NavigateBack) }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 8.dp),
        ) {
            if(refreshState is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
            if(appendState is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                reverseLayout = true
            ) {
                items(messages.itemCount, key = messages.itemKey { it.id }) { index ->
                    val message = messages[index]
                    message?.let {
                        MessageItem(
                            message = it.text,
                            at = it.createdAt.toIso(),
                            sent = it.senderId == state.userId
                        )
                    }
                }
            }
            SwipeableSnackBar(
                snackBarHostState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp)
            )
        }
        MessageBox(
            value = state.message,
            onValueChange = {
                onEvent(ConversationEvent.MessageChanged(it))
            },
            sendMessage = {
                onEvent(ConversationEvent.SendMessage)
            }
        )
    }

}


@Preview(showSystemUi = true)
@Composable
private fun PreviewConversationScreen() {
    val messages = flowOf(PagingData.empty<Message>()).collectAsLazyPagingItems()
    ConversationScreen(
        chat = Chat(),
        messages = messages,
        state = ConversationState(),
        onEvent = {},
        snackBarHostState = SnackbarHostState()
    )
}