package com.synaxis.android.chatapp.feature.search

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.PersonAdd
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.synaxis.android.chatapp.core.common.user.GetSearchUser
import com.synaxis.android.chatapp.ui.component.HintText

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchVM = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    SearchScreen(modifier = modifier, state = state, onEvent = viewModel::onEvent, onBack = onBack)
}

@Composable
internal fun SearchScreen(
    modifier: Modifier = Modifier,
    state: SearchState,
    onEvent: (SearchEvent) -> Unit,
    onBack: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchHeader(
                query = state.query,
                onQueryChange = { onEvent(SearchEvent.QueryChanged(it)) },
                onBack = onBack,
                focusRequester = focusRequester
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when {
                    state.isLoading -> CircularProgressIndicator()
                    state.errorMessage != null -> {
                        Toast.makeText(LocalContext.current,state.errorMessage, Toast.LENGTH_SHORT).show()
                        onEvent(SearchEvent.ClearMessages)
                    }
                    state.query.isBlank() -> HintText("Search for people to add")
                    state.users.isEmpty() -> HintText("No results for \"${state.query}\"")
                    else -> UserList(
                        users = state.users,
                        onSendRequest = { onEvent(SearchEvent.SendRequest(it)) })
                }
            }
        }
    }
}

@Composable
private fun SearchHeader(
    query: String,
    onQueryChange: (String) -> Unit,
    onBack: () -> Unit,
    focusRequester: FocusRequester
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
        }
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
            decorationBox = { inner ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainerLow)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    if (query.isEmpty()) {
                        Text(
                            text = "Search people...",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    inner()
                }
            }
        )
    }
}

@Composable
private fun UserList(
    users: List<GetSearchUser>,
    onSendRequest: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(users, key = { it.id }) { user ->
            UserItem(user = user, onSendRequest = onSendRequest)
        }
    }
}

@Composable
private fun UserItem(
    user: GetSearchUser,
    onSendRequest: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = user.avatarUrl,
            contentDescription = null,
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceContainerHigh),
            contentScale = ContentScale.Crop,
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = user.username,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            if (!user.fullName.isNullOrBlank()) {
                Text(
                    text = user.fullName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        if (!user.isFriend) {
            IconButton(onClick = { onSendRequest(user.id) }) {
                Icon(
                    imageVector = Icons.Rounded.PersonAdd,
                    contentDescription = "Add friend",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        } else {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = "Already friends",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}


@Composable
private fun ErrorMessage(message: String) {
    Text(
        text = message,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.error
    )
}

@Preview(showSystemUi = true)
@Composable
private fun SearchScreenPreview() {
    SearchScreen(
        state = SearchState(),
        onEvent = {},
        onBack = {}
    )
}