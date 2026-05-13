package com.synaxis.android.chatapp.feature.home.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    bottomNavItems: List<BottomNavItem>
) {
    NavigationBar() {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                label = { Text(item.title) },
                selected = item.isSelected,
                onClick = item.action,
                icon = {
                    val icon = if(item.isSelected) item.selectedIcon else item.unselectedIcon
                    Icon(icon,"")
                },
            )
        }
    }
}