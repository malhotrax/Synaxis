package com.synaxis.android.chatapp.feature.friends.presentation

import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.synaxis.android.chatapp.feature.chat.domain.model.Chat
import com.synaxis.android.chatapp.feature.friends.domain.model.Friend
import com.synaxis.android.chatapp.feature.friends.presentation.component.AllFriends
import com.synaxis.android.chatapp.ui.component.Header
import com.synaxis.android.chatapp.feature.friends.presentation.component.Pending
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlin.enums.EnumEntries

@Composable
fun FriendsScreen(
    modifier: Modifier = Modifier,
    viewModel: FriendsVM = hiltViewModel(),
    navigateToConversation: (Chat) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.uiState.collect { e->
            when(e) {
                is FriendsUiEvent.NavigateToConversation -> navigateToConversation(e.chat)
                is FriendsUiEvent.ShowSnackBar -> {}
            }
        }
    }
    val state by viewModel.state.collectAsStateWithLifecycle()

    val friends = viewModel.friends.collectAsLazyPagingItems()
    FriendsScreen(
        modifier = modifier,
        state = state,
        friends = friends,
        onEvent = viewModel::onEvent,
    )
}

@Composable
internal fun FriendsScreen(
    modifier: Modifier = Modifier,
    state: FriendsState,
    friends: LazyPagingItems<Friend>,
    onEvent: (FriendsEvent) -> Unit
) {
    val tabs = FriendTabRow.entries
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    Column(
        modifier =  modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Header(
            title = "Friends",
            onSearch = {}
        )
        TabRow(pagerState = pagerState, tabs)
        HorizontalPager(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            state = pagerState
        ) { page ->
            when (tabs[page]) {
                FriendTabRow.ALL_FRIENDS -> AllFriends(
                    friends = friends,
                    onEvent = onEvent,
                )

                FriendTabRow.PENDING -> Pending(
                    onEvent = onEvent,
                    state = state
                )
            }
        }
        if(!state.error.isNullOrEmpty()) {
            Toast.makeText(LocalContext.current,state.error, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun TabRow(
    pagerState: PagerState,
    tabs: EnumEntries<FriendTabRow>
) {
    val totalTabWidthPx = remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .onSizeChanged {
                totalTabWidthPx.intValue = it.width
            }
            .shadow(5.dp, RoundedCornerShape(15.dp))
            .clip(RoundedCornerShape(15.dp))
            .background(color = MaterialTheme.colorScheme.surfaceContainer)
            .border(
                width = 0.5.dp,
                shape = RoundedCornerShape(15.dp),
                color = MaterialTheme.colorScheme.surfaceContainerHighest
            )

    ) {
        val calculateTabWidthPx =
            if (tabs.isNotEmpty()) totalTabWidthPx.intValue / tabs.size else 0
        val calculateTabWidthDp = with(density) { calculateTabWidthPx.toDp() }
        val animateTranslationX by animateFloatAsState(
            targetValue = (pagerState.currentPage * calculateTabWidthPx).toFloat(),
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
        if (calculateTabWidthPx > 0) {
            Box(
                modifier = Modifier
                    .padding(6.dp)
                    .width(calculateTabWidthDp - 12.dp)
                    .height(40.dp)
                    .graphicsLayer {
                        translationX = animateTranslationX
                    }
                    .clip(RoundedCornerShape(15.dp))
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(0.8f)
                    )
            )
        }
        PrimaryTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.Transparent,
            indicator = {},
            divider = {}
        ) {
            tabs.forEachIndexed { index, tab ->
                val isSelected = index == pagerState.currentPage
                Tab(
                    selected = isSelected,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    selectedContentColor = MaterialTheme.colorScheme.surfaceContainer,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp)
                            .height(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = tab.title,
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun FriendScreenPreview() {
    val friends = flowOf(PagingData.empty<Friend>()).collectAsLazyPagingItems()
    FriendsScreen(
        state = FriendsState(),
        friends = friends,
        onEvent = {},
    )
}


enum class FriendTabRow(val title: String) {
    ALL_FRIENDS("All Friends"),
    PENDING(title = "Pending")
}