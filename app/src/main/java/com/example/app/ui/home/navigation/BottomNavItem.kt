package com.example.app.ui.home.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.app.R

sealed class BottomNavItem(var title: Int, var icon: ImageVector, var screen_route: String) {
    object Home : BottomNavItem(R.string.bottom1, Icons.Filled.Home, "home")
    object Search : BottomNavItem(R.string.bottom2, Icons.Filled.DateRange, "search")
    object Profile : BottomNavItem(R.string.bottom3, Icons.Filled.Settings, "profile")
    object Pre1 : BottomNavItem(R.string.bottom3, Icons.Filled.Settings, "pre1")
    object Pre2 : BottomNavItem(R.string.bottom3, Icons.Filled.Settings, "pre2")
    object Pre2_1 : BottomNavItem(R.string.bottom3, Icons.Filled.Settings, "pre2_1")
    object Pre2_2 : BottomNavItem(R.string.bottom3, Icons.Filled.Settings, "pre2_2")
    object detail : BottomNavItem(R.string.bottom3, Icons.Filled.Settings, "detail")
}