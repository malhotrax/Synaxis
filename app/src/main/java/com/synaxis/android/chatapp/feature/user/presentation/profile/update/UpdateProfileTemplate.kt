package com.synaxis.android.chatapp.feature.user.presentation.profile.update

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.synaxis.android.chatapp.feature.user.presentation.profile.component.TopNavBar

@Composable
fun UpdateProfileTemplate(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    title: String,
    content: @Composable () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopNavBar(
                title = title,
                navigateBack = navigateBack
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
           content()
        }
    }
}