package com.synaxis.android.chatapp.feature.search

import com.synaxis.android.chatapp.core.common.user.GetSearchUser

data class SearchState(
    val isLoading: Boolean = false,
    val users: List<GetSearchUser> = emptyList(),
    val query: String = "",
    val errorMessage: String? = null,
    val successMessage: String? = null
)
