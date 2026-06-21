package com.synaxis.android.chatapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun UserItem(
    username: String,
    modifier: Modifier = Modifier,
    fullName: String? = null,
    avatar: String? = null,
    action: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        onClick = action
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!avatar.isNullOrBlank()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(avatar)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Profile picture of $username",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                // FALLBACK: Circular Initials Badge / Generic Icon
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    val initials = username.trim().take(2).uppercase()

                    if (initials.isNotEmpty()) {
                        Text(
                            text = initials,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Rounded.Person,
                            contentDescription = "Default User Icon",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // USER INFO TEXT
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = fullName ?: username,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                // Only show subtext handle if a full name is actively being provided
                if (!fullName.isNullOrBlank()) {
                    Text(
                        text = "@$username",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "With Full Name")
@Composable
private fun UserItemWithNamePreview() {
    MaterialTheme {
        UserItem(
            username = "alex_w",
            fullName = "Alex Walker",
            avatar = null, // Forces initial badge fallback
            action = {}
        )
    }
}

@Preview(showBackground = true, name = "Username Only")
@Composable
private fun UserItemUsernameOnlyPreview() {
    MaterialTheme {
        UserItem(
            username = "sarah_m",
            fullName = null,
            avatar = null,
            action = {}
        )
    }
}

@Preview(showBackground = true, name = "With Image Url")
@Composable
private fun UserItemWithAvatarPreview() {
    MaterialTheme {
        UserItem(
            username = "david_k",
            fullName = "David Knight",
            avatar = "https://example.com/avatar.jpg",
            action = {}
        )
    }
}