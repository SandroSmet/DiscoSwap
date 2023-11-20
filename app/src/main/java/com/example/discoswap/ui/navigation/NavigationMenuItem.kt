package com.example.discoswap.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

class NavigationMenuItem(val route: String, @StringRes val title: Int, val icon: ImageVector, val navigationAction: () -> Unit)
