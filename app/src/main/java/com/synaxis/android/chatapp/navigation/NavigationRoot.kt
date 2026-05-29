package com.synaxis.android.chatapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.synaxis.android.chatapp.core.common.util.NavigationActionItem
import com.synaxis.android.chatapp.core.common.util.PrimaryTabs
import com.synaxis.android.chatapp.feature.auth.presentation.login.LoginScreen
import com.synaxis.android.chatapp.feature.auth.presentation.signup.SignUpScreen
import com.synaxis.android.chatapp.feature.chat.presentation.ChatListScreen
import com.synaxis.android.chatapp.feature.friends.presentation.FriendsScreen
import com.synaxis.android.chatapp.feature.splash.AuthState
import com.synaxis.android.chatapp.feature.splash.SplashScreen
import com.synaxis.android.chatapp.feature.splash.SplashVM
import com.synaxis.android.chatapp.feature.user.presentation.profile.ProfileScreen
import com.synaxis.android.chatapp.navigation.route.Routes
import com.synaxis.android.chatapp.ui.component.BottomNavigation


@Composable
fun App(modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<SplashVM>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    if(state.isLoading) {
        SplashScreen()
        return
    }
    val initialRoute = if(state.isLoggedIn) Routes.Friends else Routes.Login
    NavigationRoot(
        modifier = Modifier,
        state = state,
        initialRoute = initialRoute
    )
}

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    state: AuthState,
    initialRoute: Routes
) {
    val backStack = rememberNavBackStack(initialRoute)
    val primaryTabs = PrimaryTabs.entries
    val showBottomNav = !state.isLoading && state.isLoggedIn
    val currentRoute = backStack.lastOrNull()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomNav) {
                BottomNavigation(
                    navigationItems = primaryTabs.map {
                        NavigationActionItem(
                            title = it.title,
                            selectedIcon = it.selectedIcon,
                            unselectedIcon = it.unselectedIcon,
                            onClick = {
                                if (currentRoute != it.route) {
                                    backStack.clear()
                                    backStack.add(it.route)
                                }
                            },
                            isSelected = currentRoute == it.route,
                            route = it.route
                        )
                    }
                )
            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier.padding(paddingValues),
        ) {
            NavDisplay(
                backStack = backStack,
                onBack = {
                    backStack.removeLastOrNull()
                },
                entryProvider = entryProvider {
                    entry<Routes.Login> {
                        LoginScreen(
                            navigateToForgetPassword = {},
                            navigateToHome = {
                                backStack.clear()
                                backStack.add(Routes.Friends)
                            },
                            navigateToSignUp = {
                                backStack.add(Routes.SignUp)
                            },
                        )
                    }
                    entry<Routes.SignUp> {
                        SignUpScreen(
                            navigateToHome = {
                                backStack.clear()
                                backStack.add(Routes.Friends)
                            },
                            navigateToLogin = {
                                backStack.removeLastOrNull()
                            }
                        )
                    }
                    entry<Routes.Friends> {
                        FriendsScreen()
                    }

                    entry<Routes.Chats> { ChatListScreen() }
                    entry<Routes.Profile> {
                        ProfileScreen(
                            navigateToUpdateUsername = {},
                            navigateToUpdateFullName = {})
                    }
                }
            )
        }
    }

}

