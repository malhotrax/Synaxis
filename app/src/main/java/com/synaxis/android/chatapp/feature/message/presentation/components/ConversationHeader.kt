package com.synaxis.android.chatapp.feature.message.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.synaxis.android.chatapp.ui.component.ImageContainer
import java.time.Instant

@Composable
fun ConversationHeader(
    modifier: Modifier = Modifier,
    name: String,
    avatarUrl: String? = null,
    lastActivity: String? = null,
    navigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = "Navigate back",
                modifier = Modifier
                    .clickable(onClick = navigateBack)
                    .padding(8.dp)
            )
            ImageContainer(
                avatarUrl = avatarUrl,
                size = 45
            )
            Spacer(Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .weight(0.8f)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    overflow = TextOverflow.Ellipsis
                )
                if(!lastActivity.isNullOrEmpty()) {
                    Text(
                        text = lastActivity,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(0.8f),
                        fontStyle = FontStyle.Italic,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            IconButton(
                onClick = {}
            ) {
                Icon(Icons.Rounded.MoreVert,"Options")
            }
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.surfaceContainerHighest,
        )
    }

}


@Preview
@Composable
private fun ConversationHeaderPreview() {
    ConversationHeader(
        name = "Manish Malhotra",
        lastActivity = Instant.now().toString(),
        navigateBack = {}
    )
}