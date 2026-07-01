package com.synaxis.android.chatapp.feature.chat.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.synaxis.android.chatapp.ui.component.ImageContainer
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun ChatItem(
    modifier: Modifier = Modifier,
    avatarUrl: String? = null,
    name: String,
    lastMessage: String? = null,
    lastActivity: String? = null,
    navigateToConversation: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
//                .shadow(5.dp, shape = RoundedCornerShape(20.dp))
//                .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(20.dp))
                .clickable(onClick = navigateToConversation)
//                .border(width = 0.5.dp, color = MaterialTheme.colorScheme.surfaceContainerHighest, RoundedCornerShape(20.dp))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImageContainer(avatarUrl = avatarUrl, size = 50)
            Spacer(Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .weight(0.7f)
                    .padding(5.dp)
            ) {
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(Modifier.height(8.dp))
                lastMessage?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        overflow = TextOverflow.Ellipsis,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
            lastActivity?.let {
                Text(
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.widthIn(max = 70.dp),
                    text = it,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ChatItemPreview() {
    val lastActivity = Instant.now()
    val formatter =
        DateTimeFormatter.ofPattern("hh:mm a dd MMM yyyy", LocalLocale.current.platformLocale)
    val lastAct = lastActivity.atZone(ZoneId.systemDefault()).format(formatter)
    ChatItem(
        name = "Manish Malhotra",
        lastActivity = lastAct,
        navigateToConversation = {},
        lastMessage = "Hey, manish how 're you doing"
    )
}