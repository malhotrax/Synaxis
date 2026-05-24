package com.synaxis.android.chatapp.feature.friends.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.synaxis.android.chatapp.feature.friends.domain.model.Friend

@Composable
fun FriendsScreen(
    modifier: Modifier = Modifier,
    viewModel: FriendsVM = hiltViewModel()
) {
    val friends =  viewModel.friends.collectAsLazyPagingItems()
    FriendsScreen(
        friends = friends
    )
}

@Composable
internal fun FriendsScreen(
    friends: LazyPagingItems<Friend>
) {

}

@Preview
@Composable
private fun FriendScreenPreview() {
    FriendsScreen()
}