package com.synaxis.android.chatapp.feature.user.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.synaxis.android.chatapp.ui.component.ImageContainer


@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
    avatarUrl: String? = null,
    username: String,
    fullName: String?,
    logout: () -> Unit
) {
    var showDropDownMenu by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier,
                fontWeight = FontWeight.Bold,
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Box {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .shadow(5.dp, CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .border(
                            width = 0.5.dp,
                            color = MaterialTheme.colorScheme.surfaceContainerHighest,
                            CircleShape
                        )
                        .clickable(onClick = {
                            showDropDownMenu = !showDropDownMenu
                        }),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Rounded.MoreVert, "More Options")
                }
                DropdownMenu(
                    expanded = showDropDownMenu,
                    onDismissRequest = {
                        showDropDownMenu = false
                    }
                ) {
                    DropdownMenuItem(
                        text = {Text("Logout")},
                        onClick = logout
                    )
                }
            }
        }


        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surfaceContainer),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ImageContainer(modifier = Modifier.padding(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val name = fullName ?: username
                Text(
                    text = name,
                    modifier = Modifier,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                fullName?.let {
                    Text(
                        text = username,
                        modifier = Modifier,
                        style = MaterialTheme.typography.bodyMedium,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }

}
