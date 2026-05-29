package com.synaxis.android.chatapp.ui.component

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Replay
import androidx.compose.material.icons.rounded.Replay10
import androidx.compose.material.icons.rounded.WifiOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.synaxis.android.chatapp.ui.theme.SynaxisTheme
import okio.IOException
import retrofit2.HttpException
import java.net.ConnectException

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    heading: String,
    message: String,
    retry: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(65.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.error.copy(0.4f),CircleShape)
                .border(width = 0.5.dp, color = MaterialTheme.colorScheme.error, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Error icon",
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(Modifier.height(16.dp))
        Text(
            text = heading,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.surfaceContainerHigh
        )
        Spacer(Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .height(50.dp)
                .width(150.dp)
                .clip(RoundedCornerShape(15.dp))
                .border(width = 1.dp, color = MaterialTheme.colorScheme.onSurface,RoundedCornerShape(15.dp))
                .clickable(onClick = retry)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Rounded.Replay,"")
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Try again",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

        }

    }
}

@Preview(showSystemUi = true)
@Composable
private fun ErrorScreenPreview() {
    SynaxisTheme() {
        ErrorScreen(
            message = "Check your connection and try again",
            heading = "Couldn't load friends",
            icon = Icons.Rounded.WifiOff,
            retry = {}
        )
    }
}
