package com.synaxis.android.chatapp.feature.user.presentation.update.full_name

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
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
fun UpdateFullNameScreen(
    modifier: Modifier = Modifier,
    viewModel: UpdateFullNameVM = hiltViewModel(),
    skippable: Boolean = false,
    onNavigateBack: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.flowWithLifecycle(lifecycleOwner.lifecycle).collect { e ->
            when (e) {
                UpdateFullNameUiEvent.NavigateBack -> onNavigateBack()
                is UpdateFullNameUiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(e.message)
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.onEvent(UpdateFullNameEvent.ClearMessages)
        }
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    UpdateFullNameScreen(
        snackBarHostState = snackBarHostState,
        modifier = modifier,
        state = state,
        skippable = skippable,
        event = viewModel::onEvent
    )
}

@Composable
internal fun UpdateFullNameScreen(
    modifier: Modifier = Modifier,
    snackBarHostState: SnackbarHostState,
    state: UpdateFullNameState,
    skippable: Boolean,
    event: (UpdateFullNameEvent) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "What's your name?",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(8.dp)
                )
                if (skippable) {
                    Box(
                        modifier = Modifier
                            .shadow(5.dp, CircleShape)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceContainer)
                            .border(
                                width = 0.5.dp,
                                color = MaterialTheme.colorScheme.surfaceContainerHighest
                            )
                            .clickable(onClick = { event(UpdateFullNameEvent.NavigateBack) })
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Skip",
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }

            }
            Text(
                modifier = Modifier.padding(8.dp),
                text = "This name will be visible to other users in chats and your profile.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
            InputTextField(
                modifier = Modifier,
                value = state.fullName,
                onValueChange = { event(UpdateFullNameEvent.NameChanged(it)) },
                label = "Full name",
                leadingIcon = {
                    Icon(Icons.Rounded.Person, "FullName")
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
                    .clickable(onClick = { event(UpdateFullNameEvent.UpdateName) }),
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


@Preview
@Composable
private fun UpdateFullNamePreview() {
    UpdateFullNameScreen(
        state = UpdateFullNameState(),
        event = {},
        skippable = true,
        snackBarHostState = SnackbarHostState()
    )
}