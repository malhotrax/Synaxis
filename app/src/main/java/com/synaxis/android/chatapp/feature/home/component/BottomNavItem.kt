package com.synaxis.android.chatapp.feature.home.component

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val isSelected: Boolean = false,
    val action: () -> Unit
)
