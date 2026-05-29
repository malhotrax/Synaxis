package com.synaxis.android.chatapp.feature.friends.presentation.component

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.synaxis.android.chatapp.core.common.user.GetUser
import com.synaxis.android.chatapp.feature.friends.domain.model.Friend
import com.synaxis.android.chatapp.feature.friends.presentation.FriendsEvent
import com.synaxis.android.chatapp.ui.component.ErrorScreen
import com.synaxis.android.chatapp.ui.component.UserItem
import kotlinx.coroutines.flow.flowOf
import java.time.Instant

@Composable
fun AllFriends(
    modifier: Modifier = Modifier,
    friends: LazyPagingItems<Friend>,
    onEvent: (FriendsEvent) -> Unit,
) {

    val context = LocalContext.current
    val refreshState = friends.loadState.refresh
    val appendState = friends.loadState.append
    val isRefreshing = refreshState is LoadState.Loading

    Box(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                friends.refresh()
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(count = friends.itemCount, key = friends.itemKey { it.id }) { index ->
                    friends[index]?.friend?.let {
                        UserItem(
                            username = it.username,
                            fullName = it.fullName,
                            avatar = it.avatarUrl
                        )
                    }
                }
            }
            if(refreshState is LoadState.Error) {
                val message = refreshState.error.message ?: "Something went wrong"
                Toast.makeText(context, message,Toast.LENGTH_SHORT,).show()
            }
            if(appendState is LoadState.Error) {
                val message = appendState.error.message ?: "Something went wrong"
                Toast.makeText(context, message,Toast.LENGTH_SHORT,).show()
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun AllFriendPreview() {
    val dummyFriendsList = listOf(
        Friend(
            id = "1",
            createdAt = 1716654382000L,
            friend = GetUser(
                id = "u1",
                username = "alex_w",
                fullName = "Alex Walker",
                createdAt = Instant.now().toString().toString()
            )
        ),
        Friend(
            id = "2",
            createdAt = 1716654421000L,
            friend = GetUser(
                id = "u2",
                username = "sarah_m",
                fullName = "Sarah Miller",
                createdAt = Instant.now().toString()
            )
        ),
        Friend(
            id = "3",
            createdAt = 1716654510000L,
            friend = GetUser(
                id = "u3",
                username = "david_k",
                fullName = "David Knight",
                createdAt = Instant.now().toString()
            )
        ),
        Friend(
            id = "4",
            createdAt = 1716654600000L,
            friend = GetUser(
                id = "u4",
                username = "emily_b",
                fullName = "Emily Brooks",
                createdAt = Instant.now().toString()
            )
        ),
        Friend(
            id = "5",
            createdAt = 1716654700000L,
            friend = GetUser(
                id = "u5",
                username = "james_c",
                fullName = "James Carter",
                createdAt = Instant.now().toString()
            )
        ),
        Friend(
            id = "6",
            createdAt = 1716654800000L,
            friend = GetUser(
                id = "u6",
                username = "jess_d",
                fullName = "Jessica Davis",
                createdAt = Instant.now().toString()
            )
        ),
        Friend(
            id = "7",
            createdAt = 1716654900000L,
            friend = GetUser(
                id = "u7",
                username = "ryan_f",
                fullName = "Ryan Foster",
                createdAt = Instant.now().toString()
            )
        ),
        Friend(
            id = "8",
            createdAt = 1716655000000L,
            friend = GetUser(
                id = "u8",
                username = "chloe_h",
                fullName = "Chloe Hayes",
                createdAt = Instant.now().toString()
            )
        ),
        Friend(
            id = "9",
            createdAt = 1716655100000L,
            friend = GetUser(
                id = "u9",
                username = "michael_p",
                fullName = "Michael Pierce",
                createdAt = Instant.now().toString()
            )
        ),
        Friend(
            id = "10",
            createdAt = Instant.now().toEpochMilli(),
            friend = GetUser(
                id = "u10",
                username = "megan_s",
                fullName = "Megan Stone",
                createdAt = Instant.now().toString()
            )
        )
    )
    val friends = flowOf(PagingData.from(dummyFriendsList)).collectAsLazyPagingItems()
    AllFriends(friends = friends, onEvent = {})
}