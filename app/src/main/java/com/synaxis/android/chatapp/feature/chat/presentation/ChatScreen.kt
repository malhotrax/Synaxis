package com.synaxis.android.chatapp.feature.chat.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.synaxis.android.chatapp.feature.chat.presentation.component.FeatureChats
import com.synaxis.android.chatapp.feature.chat.presentation.component.SearchBarItem
import com.synaxis.android.chatapp.feature.chat.presentation.component.SearchBarSection

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    navigateToSearch: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            SearchBarItem(
                onClick = navigateToSearch
            )
            FeatureChats()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ChatScreenPreview() {
    ChatScreen(navigateToSearch = {})
}