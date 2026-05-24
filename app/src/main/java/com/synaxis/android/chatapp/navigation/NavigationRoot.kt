package com.synaxis.android.chatapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.synaxis.android.chatapp.navigation.route.Routes

@Composable
fun NavigationRoot() {
    val viewModel = hiltViewModel<NavigationRootVM>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val backStack = rememberNavBackStack(Routes.Login)
    LaunchedEffect(state.isLoggedIn) {
        backStack.clear()
        if(state.isLoggedIn) {
            backStack.add(Routes.Home)
        }else {
            backStack.add(Routes.Login)
        }
    }
    if(state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Waiting...")
        }
        return
    }
    NavDisplay(
        backStack = backStack,
        onBack = {
            backStack.removeLastOrNull()
        },
        entryProvider = entryProvider {

        }
    )
}

