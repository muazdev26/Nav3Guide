package com.plcoding.nav3_guide.navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val icon: ImageVector,
    val title: String,
)

val TOP_LEVEL_DESTINATIONS = mapOf(
    Route.TodoList to BottomNavItem(
        icon = Icons.Outlined.Checklist,
        title = "Todos"
    ),
    Route.TodoFavorites to BottomNavItem(
        icon = Icons.Outlined.Favorite,
        title = "Favorites"
    ),
    Route.Settings to BottomNavItem(
        icon = Icons.Outlined.Settings,
        title = "Settings"
    ),
)