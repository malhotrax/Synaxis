package com.synaxis.android.chatapp.feature.auth.presentation.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.synaxis.android.chatapp.feature.auth.presentation.login.event.LoginUiEvent


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navigateToForgetPassword: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToSignUp: () -> Unit,
    viewModel: LoginVM = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                LoginUiEvent.NavigateToForgetPassword -> navigateToForgetPassword()
                LoginUiEvent.NavigateToHome -> navigateToHome()
                LoginUiEvent.NavigateToSignUp -> navigateToSignUp()
                is LoginUiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(event.message ?: "Something went wrong")
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            LoginScreenContent(
                modifier = Modifier,
                state = state,
                onEvent = viewModel::onEvent
            )
        }
    }
}