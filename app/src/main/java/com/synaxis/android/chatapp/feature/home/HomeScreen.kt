package com.synaxis.android.chatapp.feature.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.synaxis.android.chatapp.core.common.util.PrimaryTabs
import com.synaxis.android.chatapp.feature.chat.presentation.ChatScreen
import com.synaxis.android.chatapp.feature.home.component.BottomNavBar
import com.synaxis.android.chatapp.feature.home.component.BottomNavItem
import com.synaxis.android.chatapp.feature.home.component.TopNavigationBar
import com.synaxis.android.chatapp.feature.request.RequestScreen
import com.synaxis.android.chatapp.feature.user.presentation.profile.ProfileScreen
import kotlinx.coroutines.launch

@Preview
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToSearch:() ->Unit = {},
    navigateToUpdateUsername: () -> Unit = {},
    navigateToUpdateFullName: () -> Unit = {}
) {
    val primaryTabs = PrimaryTabs.entries
    val pagerState = rememberPagerState(pageCount = { primaryTabs.size })
    val currentPage = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {

            val title =
                if (primaryTabs[currentPage].title != "Chats") primaryTabs[currentPage].title else "SynAxis"
            TopNavigationBar(title = title)
        },
        bottomBar = {
            BottomNavBar(
                bottomNavItems = primaryTabs.mapIndexed { index, tab ->
                    BottomNavItem(
                        title = tab.title,
                        selectedIcon = tab.selectedIcon,
                        unselectedIcon = tab.unselectedIcon,
                        isSelected = currentPage == index,
                        action = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            HorizontalPager(
                state = pagerState
            ) { pageIndex ->
                when (primaryTabs[pageIndex]) {
                    PrimaryTabs.CHATS -> ChatScreen(navigateToSearch = navigateToSearch)
                    PrimaryTabs.REQUEST -> RequestScreen()
                    PrimaryTabs.PROFILE -> ProfileScreen(
                        navigateToUpdateUsername =navigateToUpdateUsername,
                        navigateToUpdateFullName = navigateToUpdateFullName
                    )
                }
            }
        }
    }
}