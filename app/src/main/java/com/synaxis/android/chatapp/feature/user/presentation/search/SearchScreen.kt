package com.synaxis.android.chatapp.feature.user.presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.synaxis.android.chatapp.feature.chat.presentation.component.SearchBarSection
import com.synaxis.android.chatapp.feature.user.presentation.search.component.ErrorBox
import com.synaxis.android.chatapp.feature.user.presentation.search.component.SearchedUserItem
import com.synaxis.android.chatapp.feature.user.presentation.search.event.SearchEvent

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchVM = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 32.dp)
        ) {
            SearchBarSection(
                value = state.query,
                onSearch = { viewModel.onEvent(SearchEvent.Search) },
                onValueChange = { viewModel.onEvent(SearchEvent.QueryChanged(it)) }
            )
            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                !state.errorMessage.isNullOrBlank() -> {
                    ErrorBox(
                        errorMessage = state.errorMessage!!
                    )
                }

                state.result.isEmpty() -> {
                    ErrorBox(
                        errorMessage = "No user found",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.result, key = { it.username }) { user ->
                            SearchedUserItem(
                                username = user.username,
                                fullName = user.fullName
                            )
                        }
                    }
                }
            }
        }
    }
}