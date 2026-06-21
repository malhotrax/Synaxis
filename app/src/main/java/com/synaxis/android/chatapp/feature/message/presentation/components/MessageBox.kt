package com.synaxis.android.chatapp.feature.message.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MessageBox(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    sendMessage: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .weight(1f)
                .padding(6.dp)
                .shadow(3.dp, RoundedCornerShape(40.dp))
                .clip(RoundedCornerShape(40.dp))
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .border(
                    width = 0.5.dp,
                    MaterialTheme.colorScheme.surfaceContainerHighest,
                    RoundedCornerShape(40.dp)
                ),
            value = value,
            onValueChange = { onValueChange(it) },
            placeholder = {
                Text("Message")
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            maxLines = 3
        )
        Box(
            modifier = Modifier
                .padding(end = 6.dp)
                .size(55.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .clickable(onClick = sendMessage),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.Send,
                contentDescription = "Send Message",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(35.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun MessageBoxPreview() {
    MessageBox(
        value = "",
        onValueChange = {},
        sendMessage = {}
    )
}