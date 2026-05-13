package com.synaxis.android.chatapp.feature.chat.presentation.component

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp


@Composable
fun SearchBarSection(
    modifier: Modifier = Modifier,
    value: String,
    onSearch: () -> Unit,
    onValueChange: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .padding(8.dp)
            .shadow(5.dp, RoundedCornerShape(40.dp))
            .clip(RoundedCornerShape(40.dp))
            .height(55.dp)
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceContainer,
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            placeholder = {
                Text(
                    text = "Search..",
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSearch() }
            ),
            value = value,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            leadingIcon = {
                Icon(Icons.Rounded.Search, "Search")
            }
        )
    }
}