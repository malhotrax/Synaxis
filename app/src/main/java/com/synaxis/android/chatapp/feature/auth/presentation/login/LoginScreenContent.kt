package com.synaxis.android.chatapp.feature.auth.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.synaxis.android.chatapp.feature.auth.presentation.login.event.LoginEvent
import com.synaxis.android.chatapp.feature.auth.presentation.login.event.LoginUiEvent
import com.synaxis.android.chatapp.feature.auth.presentation.login.state.LoginState
import com.synaxis.android.chatapp.ui.component.InputTextField

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    state: LoginState,
    onEvent: (LoginEvent) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )
        InputTextField(
            value = state.email,
            onValueChange = { onEvent(LoginEvent.EmailChanged(it)) },
            label = "Email",
            isError = !state.isEmailValid,
            errorMessage = "Please enter valid email"
        )
        InputTextField(
            value = state.password,
            onValueChange = { onEvent(LoginEvent.PasswordChanged(it)) },
            label = "Password",
            isError = !state.isPasswordValid,
            trailingIcon = {
                IconButton(onClick = { onEvent(LoginEvent.ToggleShowPassword) }) {
                    val icon =
                        if (state.showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    Icon(
                        icon,
                        "password"
                    )
                }
            },
            visualTransformation = if (state.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            imeAction = ImeAction.Done
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .clickable(onClick = { onEvent(LoginEvent.NavigateToForgetPassword) })
                    .padding(8.dp),
                text = "Forget Password?",
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = { onEvent(LoginEvent.Login) },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(5.dp)
                )
            }

            Text(
                text = "Don't have an account? Sign up",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable(onClick = { onEvent(LoginEvent.NavigateToSignUp)})
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreenContent(
        state = LoginState(),
        onEvent = {}
    )
}