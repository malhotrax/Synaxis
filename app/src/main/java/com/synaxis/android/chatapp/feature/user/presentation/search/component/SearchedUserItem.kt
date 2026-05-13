package com.synaxis.android.chatapp.feature.user.presentation.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun SearchedUserItem(
    username: String,
    fullName: String? = null,
    avatarUrl: String? = null,
    isFriend: Boolean = false,
    sendRequest: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(70.dp),
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
        shadowElevation = 5.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            ) {
                if (avatarUrl.isNullOrBlank()) {
                    Icon(
                        imageVector = Icons.Rounded.AccountCircle,
                        contentDescription = "Account profile",
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    )
                }
            }
            Column(
                modifier = Modifier
                    .weight(0.7f)
                    .padding(8.dp)
            ) {
                val name = fullName ?: username
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                if (!fullName.isNullOrBlank()) {
                    Text(
                        text = username,
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
            if (!isFriend) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxHeight()
                        .shadow(elevation = 5.dp, RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable(onClick = sendRequest),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Send request",
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(8.dp)
                    )
                }

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchedUserItemPreview() {
    SearchedUserItem(
        "manish",
    )
}