package com.synaxis.android.chatapp.core.common.util

import androidx.compose.ui.graphics.vector.ImageVector

data class ActionButton(
    val title: String,
    val icon: ImageVector,
    val action: () -> Unit
)
