package com.synaxis.android.chatapp.feature.message.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.Instant

@Composable
fun MessageItem(
    modifier: Modifier = Modifier,
    sent: Boolean = true,
    message: String,
    at: String
) {
    val alignment = if (sent) Alignment.CenterEnd else Alignment.CenterStart
    val containerColor =
        if (sent) MaterialTheme.colorScheme.surfaceContainer else MaterialTheme.colorScheme.primaryContainer
    val textColor =
        if (sent) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = alignment
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(containerColor)
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = textColor
            )
            Text(
                text = at,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.End,
                color = textColor
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun MessageItemPreview() {
    MessageItem(
        message = "Hey, Manish how 're you doing??",
        at = Instant.now().toString(),
        sent = false
    )
}