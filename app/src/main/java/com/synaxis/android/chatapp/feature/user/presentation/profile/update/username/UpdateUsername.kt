package com.synaxis.android.chatapp.feature.user.presentation.profile.update.username

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.synaxis.android.chatapp.feature.user.presentation.profile.update.UpdateProfileTemplate
import com.synaxis.android.chatapp.feature.user.presentation.profile.update.username.event.UsernameUpdateEvent
import com.synaxis.android.chatapp.feature.user.presentation.profile.update.username.event.UsernameUpdateUiEvent
import com.synaxis.android.chatapp.ui.component.InputTextField

@Composable
fun UpdateUsername(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: UsernameUpdateVM = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.uiState.collect { event ->
            when (event) {
                UsernameUpdateUiEvent.NavigateBack -> navigateBack()
            }
        }
    }
    UpdateProfileTemplate(
        navigateBack = navigateBack,
        title = "Username"
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Please choose a new username",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                textAlign = TextAlign.Start
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.9f)
            ) {
                InputTextField(
                    value = state.username,
                    onValueChange = { viewModel.onEvent(UsernameUpdateEvent.UsernameChanged(it)) },
                    label = "Username",
                    imeAction = ImeAction.Done,
                    isError = !state.isUsernameValid,
                    errorMessage = "Please enter valid username"
                )
                Spacer(Modifier.height(50.dp))
                val message = state.errorMessage ?: state.successMessage
                val isError = !state.errorMessage.isNullOrBlank()
                if (!message.isNullOrBlank()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                        text = message,
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                    )
                }
            }
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = {
                        viewModel.onEvent(UsernameUpdateEvent.Update)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .imePadding()
                ) {
                    Text(
                        text = "Update",
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }

        }
    }

}

