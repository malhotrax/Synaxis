package com.synaxis.android.chatapp.feature.chat.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.synaxis.android.chatapp.feature.chat.domain.model.Chat
import kotlinx.coroutines.flow.flowOf

@Composable
fun ChatListScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatListVM = hiltViewModel()
) {
    val chats = viewModel.chats.collectAsLazyPagingItems()
    ChatListScreen(
        chats = chats
    )
}

@Composable
internal fun ChatListScreen(
    chats: LazyPagingItems<Chat>
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(8.dp)
        ) {
            //search
            when {
                chats.loadState.refresh is LoadState.Loading -> {

                }
                chats.loadState.refresh is LoadState.Error -> {

                }
                else -> {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(chats.itemCount) { index ->
                            val chat = chats[index]

                        }
                    }
                }
            }
        }
    }
}


@Preview(showSystemUi = true )
@Composable
private fun ChatListScreenPreview() {
    val chats = flowOf(PagingData.empty<Chat>()).collectAsLazyPagingItems()
    ChatListScreen(
        chats = chats
    )
}