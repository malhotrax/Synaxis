package com.synaxis.android.chatapp.feature.auth.presentation.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.synaxis.android.chatapp.feature.auth.presentation.signup.event.SignUpEvent
import com.synaxis.android.chatapp.feature.auth.presentation.signup.state.SignUpState
import com.synaxis.android.chatapp.ui.component.InputTextField

@Composable
fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    state: SignUpState,
    onEvent: (SignUpEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Register",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        InputTextField(
            value = state.username,
            onValueChange = {onEvent(SignUpEvent.UsernameChanged(it))},
            label = "Username",
            isError = !state.isUsernameValid,
            errorMessage = "Please enter valid username",
        )
        InputTextField(
            value = state.email,
            onValueChange = {onEvent(SignUpEvent.EmailChanged(it))},
            label = "Email",
            isError = !state.isEmailValid,
            errorMessage = "Please enter valid email"
        )
        InputTextField(
            value = state.password,
            onValueChange = {onEvent(SignUpEvent.PasswordChanged(it))},
            label = "Password",
            isError = !state.isPasswordValid,
            errorMessage = "Please enter password longer than 6 character",
            trailingIcon = {
                val icon = if(state.showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = {onEvent(SignUpEvent.TogglePasswordVisibility)}) {
                    Icon(icon,"password")
                }
            },
            visualTransformation = if(state.showPassword) VisualTransformation.None else PasswordVisualTransformation()
        )
        InputTextField(
            value = state.confirmPassword,
            onValueChange = {onEvent(SignUpEvent.ConfirmPasswordChanged(it))},
            label = "Confirm password",
            isError = !state.isConfirmPasswordValid,
            errorMessage = "Password didn't matched",
            trailingIcon = {
                val icon = if(state.showConfirmedPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = {onEvent(SignUpEvent.ToggleConfirmedPasswordVisibility)}) {
                    Icon(icon,"password")
                }
            },
            visualTransformation = if(state.showConfirmedPassword) VisualTransformation.None else PasswordVisualTransformation(),
            imeAction = ImeAction.Done
        )
        if(state.isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = {onEvent(SignUpEvent.SignUp)}) {
                Text(
                    text = "Sign Up",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(4.dp)
                )
            }

            Text(
                text = "Already have an account? Login ",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable(onClick = {onEvent(SignUpEvent.NavigateToLogin)})
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SignUpScreenContentPreview() {
    SignUpScreenContent(
        state = SignUpState(),
        onEvent = {}
    )
}