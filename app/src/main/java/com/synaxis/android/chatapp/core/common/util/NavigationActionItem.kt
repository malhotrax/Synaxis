package com.synaxis.android.chatapp.core.common.util

import androidx.compose.ui.graphics.vector.ImageVector
import com.synaxis.android.chatapp.navigation.route.Routes

data class NavigationActionItem(
    val title: String,
    val isSelected: Boolean,
    val onClick: () -> Unit,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: Routes
)
