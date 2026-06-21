package com.synaxis.android.chatapp.ui.component

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

@Composable
fun SwipeableSnackBar(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier
    ) { snackBarData ->
        val dismissBoxState = rememberSwipeToDismissBoxState()
        LaunchedEffect(dismissBoxState.currentValue) {
            if(dismissBoxState.currentValue == SwipeToDismissBoxValue.EndToStart ||
                dismissBoxState.currentValue == SwipeToDismissBoxValue.StartToEnd
            ) {
                snackBarData.dismiss()
            }
        }
        SwipeToDismissBox(
            state = dismissBoxState,
            backgroundContent = {},
            content = { Snackbar(snackBarData) }
        )
    }
}