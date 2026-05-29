package com.synaxis.android.chatapp.core.common.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.ChatBubble
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.ChatBubble
import androidx.compose.ui.graphics.vector.ImageVector
import com.synaxis.android.chatapp.navigation.route.Routes

enum class PrimaryTabs(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: Routes
) {
    CHATS(
        title = "Chats",
        selectedIcon = Icons.Filled.ChatBubble,
        unselectedIcon = Icons.Outlined.ChatBubble,
        route = Routes.Chats
    ),
    FRIENDS(
        title = "Friends",
        selectedIcon = Icons.Filled.People,
        unselectedIcon = Icons.Outlined.People,
        route = Routes.Friends
    ),
    PROFILE(
        title = "Settings",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        route = Routes.Profile
    ),

}