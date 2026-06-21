package com.synaxis.android.chatapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage


@Composable
fun ImageContainer(
    modifier: Modifier = Modifier,
    avatarUrl: String? = null,
    size: Int = 100
) {
    avatarUrl?.let { avatarUrl ->
        AsyncImage(
            model = avatarUrl,
            contentDescription = null,
            modifier = modifier
                .size(size.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceContainerHigh),
            contentScale = ContentScale.Crop,
        )
    } ?: Box(
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceContainerHigh),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.AccountCircle,
            contentDescription = "Profile",
            modifier = Modifier.fillMaxSize()
        )
    }
}