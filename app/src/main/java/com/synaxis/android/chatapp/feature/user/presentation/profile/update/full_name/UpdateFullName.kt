package com.synaxis.android.chatapp.feature.user.presentation.profile.update.full_name

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
import com.synaxis.android.chatapp.feature.user.presentation.profile.update.full_name.event.FullNameUpdateEvent
import com.synaxis.android.chatapp.feature.user.presentation.profile.update.full_name.event.FullNameUpdateUiEvent
import com.synaxis.android.chatapp.ui.component.InputTextField

@Composable
fun UpdateFullName(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: FullNameUpdateVM = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.uiState.collect { event ->
            when (event) {
                FullNameUpdateUiEvent.NavigateBack -> navigateBack()
            }
        }
    }
    UpdateProfileTemplate(
        navigateBack = navigateBack,
        title = "Full name"
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Please enter your full name",
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
                    value = state.fullName,
                    onValueChange = { viewModel.onEvent(FullNameUpdateEvent.FullNameChanged(it)) },
                    label = "Full name",
                    imeAction = ImeAction.Done,
                    isError = !state.isFullNameValid,
                    errorMessage = "Please enter a valid name"
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
                        viewModel.onEvent(FullNameUpdateEvent.Update)
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
