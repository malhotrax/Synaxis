package com.synaxis.android.chatapp.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    errorMessage: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (()-> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    corderRadius: Int = 15

) {
    OutlinedTextField(
        modifier = modifier.padding(8.dp).fillMaxWidth(),
        shape = RoundedCornerShape(corderRadius.dp),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(label) },
        supportingText = if (isError && errorMessage != null) {
            { Text(text = errorMessage) }
        } else null,
        isError = isError,
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        )

    )
}


@Preview(showBackground = true)
@Composable
private fun InputBoxPreview() {
    InputTextField(
        value = "username",
        onValueChange = {},
        label ="Username"
    )
}