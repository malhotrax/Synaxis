package com.synaxis.android.chatapp.feature.user.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.synaxis.android.chatapp.feature.user.presentation.profile.component.ProfileItem

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navigateToUpdateUsername: () -> Unit,
    navigateToUpdateFullName: () -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Spacer(
                modifier = Modifier.height(50.dp)
            )
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .size(200.dp)
                    .shadow(5.dp,CircleShape)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    tint = Color.Gray
                )
            }
            Spacer(
                modifier = Modifier.height(20.dp)
            )
            ProfileItem("Username", onClick = navigateToUpdateUsername)
            ProfileItem("Full name", onClick = navigateToUpdateFullName)
            ProfileItem("Email", editable = false)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(
        navigateToUpdateUsername = {},
        navigateToUpdateFullName = {}
    )
}