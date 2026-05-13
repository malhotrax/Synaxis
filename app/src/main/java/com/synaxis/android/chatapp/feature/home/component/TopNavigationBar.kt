package com.synaxis.android.chatapp.feature.home.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(
    modifier: Modifier = Modifier,
    title: String = "SynAxis"
) {
    TopAppBar(
        title = {
            Text(text = title)
        }
    )
}