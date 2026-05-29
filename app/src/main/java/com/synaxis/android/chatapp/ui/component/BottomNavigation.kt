package com.synaxis.android.chatapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.synaxis.android.chatapp.core.common.util.NavigationActionItem

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    navigationItems: List<NavigationActionItem>
) {
    Row(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .height(70.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            .shadow(5.dp, RoundedCornerShape(30.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .border(width = 0.5.dp, color = MaterialTheme.colorScheme.surfaceContainerHighest,RoundedCornerShape(30.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        navigationItems.forEach { item ->
            BottomNavigationItem(
                navigationActionItem = item
            )
        }
    }
}

@Composable
fun BottomNavigationItem(
    modifier: Modifier = Modifier,
   navigationActionItem: NavigationActionItem
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(25.dp))
            .clickable(onClick = navigationActionItem.onClick)
            .padding(8.dp)
    ) {
        val icon = if(navigationActionItem.isSelected) navigationActionItem.selectedIcon else navigationActionItem.unselectedIcon
        Icon(imageVector = icon, "Icon", modifier = Modifier.size(26.dp))
    }
}