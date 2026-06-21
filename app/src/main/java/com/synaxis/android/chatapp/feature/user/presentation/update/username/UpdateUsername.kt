package com.synaxis.android.chatapp.feature.user.presentation.update.username

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AlternateEmail
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.synaxis.android.chatapp.ui.component.InputTextField
import com.synaxis.android.chatapp.ui.component.SwipeableSnackBar

@Composable
fun UpdateUsernameScreen(
    viewModel: UpdateUsernameVM = hiltViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.uiEvent.flowWithLifecycle(lifecycleOwner.lifecycle).collect { e ->
            when (e) {
                is UpdateUsernameUiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(e.message)
                }
            }
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.onEvent(UpdateUsernameEvent.ClearErrorMessage)
        }
    }
    UpdateUsernameScreen(
        modifier = Modifier,
        state = state,
        event = viewModel::onEvent,
        snackBarHostState = snackBarHostState
    )
}


@Composable
internal fun UpdateUsernameScreen(
    modifier: Modifier = Modifier,
    state: UpdateUsernameState,
    event: (UpdateUsernameEvent) -> Unit,
    snackBarHostState: SnackbarHostState
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(
                text = "Update your username",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Choose a unique @username that others can use to find and mention you.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
            InputTextField(
                modifier = Modifier,
                value = state.username,
                onValueChange = { event(UpdateUsernameEvent.UsernameChanged(it)) },
                label = "Username",
                leadingIcon = {
                    Icon(Icons.Rounded.AlternateEmail, "FullName")
                },
                isError = !state.errorMessage.isNullOrEmpty(),
                errorMessage = state.errorMessage
            )
            Spacer(Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(50.dp)
                    .shadow(5.dp, RoundedCornerShape(30.dp))
                    .clip(RoundedCornerShape(30.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .clickable(onClick = { event(UpdateUsernameEvent.UpdateUsername) }),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Save")
            }

        }
        SwipeableSnackBar(
            hostState = snackBarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .imePadding()
        )
    }
}


@Preview(showSystemUi = true)
@Composable
private fun UpdateUsernameScreenPreview() {
    UpdateUsernameScreen(
        state = UpdateUsernameState(),
        event = {},
        snackBarHostState = SnackbarHostState()
    )
}
