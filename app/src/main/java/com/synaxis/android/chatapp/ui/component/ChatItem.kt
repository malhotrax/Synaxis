package com.synaxis.android.chatapp.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ChatItem(
    avatarUrl: String? = null,
    name: String,
    lastMessage: String? = null,
    lastActivity: String? = null
) {

}


@Preview
@Composable
private fun ChatItemPreview() {
    ChatItem(
        name = "Manish Malhotra",
        lastMessage = "Hey, how are you doing? Do you have any plans for tonight."
    )
}