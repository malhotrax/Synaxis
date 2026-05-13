package com.synaxis.android.chatapp.feature.user.presentation.search.state

import com.synaxis.android.chatapp.core.common.user.GetUser

data class SearchState(
    val result: List<GetUser> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
