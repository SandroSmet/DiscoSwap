package com.example.discoswap.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.discoswap.R

@Composable
fun BottomNavigationBar(currentRoute: String, menuItems: Array<NavigationMenuItem>) {
    NavigationBar {
        menuItems.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = stringResource(R.string.navigation_bar_icon)) },
                label = { Text(stringResource(screen.title)) },
                selected = currentRoute == screen.route,
                onClick = screen.navigationAction,
            )
        }
    }
}
