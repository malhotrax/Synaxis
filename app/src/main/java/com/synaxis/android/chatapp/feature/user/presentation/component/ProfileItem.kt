package com.synaxis.android.chatapp.feature.user.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProfileItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    description: String,
    action: () -> Unit,
    title: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
                .shadow(5.dp, RoundedCornerShape(25.dp))
                .clip(RoundedCornerShape(25.dp))
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .border(width = 0.5.dp, color = MaterialTheme.colorScheme.surfaceContainerHighest)
                .clickable(onClick = action)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = description,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .weight(.8f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }

}

@Preview
@Composable
private fun ProfileItemPreview() {
    ProfileItem(
        icon = Icons.Rounded.Person,
        description = "Name",
        action = {},
        title = "Manish Malhotra"
    )
}