package com.synaxis.android.chatapp.feature.user.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AlternateEmail
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.synaxis.android.chatapp.feature.user.presentation.component.ProfileHeader
import com.synaxis.android.chatapp.feature.user.presentation.component.ProfileItem

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileVM = hiltViewModel(),
    navigateToUpdateFullName: () -> Unit,
    navigateToUpdateUsername: () -> Unit
) {
    val localLifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        viewModel.uiEvent.flowWithLifecycle(lifecycle = localLifecycleOwner.lifecycle)
            .collect { e ->
                when (e) {
                    ProfileUiEvent.NavigateToUpdateFullNameScreen -> navigateToUpdateFullName()
                    ProfileUiEvent.NavigateToUpdateUsernameScreen -> navigateToUpdateUsername()
                }
            }
    }
    LaunchedEffect(Unit) {
        viewModel.onEvent(ProfileEvent.LoadUser)
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    ProfileScreen(
        modifier = modifier,
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
internal fun ProfileScreen(
    modifier: Modifier = Modifier,
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            ProfileHeader(
                username = state.user.username,
                fullName = state.user.fullName,
                logout = {
                    onEvent(ProfileEvent.LogOut)
                }
            )
            Spacer(Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
            ) {
                if (!state.user.fullName.isNullOrEmpty()) {
                    ProfileItem(
                        title = state.user.fullName,
                        icon = Icons.Rounded.Person,
                        description = "Name",
                        action = { onEvent(ProfileEvent.NavigateToUpdateFullName) }
                    )
                } else {
                    ProfileItem(
                        title = "Set up your name",
                        icon = Icons.Rounded.Person,
                        description = "Name",
                        action = { onEvent(ProfileEvent.NavigateToUpdateFullName) }
                    )
                }
                ProfileItem(
                    title = state.user.username,
                    icon = Icons.Rounded.AlternateEmail,
                    description = "Username",
                    action = { onEvent(ProfileEvent.NavigateToUpdateUsername) }
                )
                ProfileItem(
                    title = state.user.email,
                    icon = Icons.Rounded.Email,
                    description = "Email",
                    action = {}
                )
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(
        state = ProfileState(),
        onEvent = {}
    )
}