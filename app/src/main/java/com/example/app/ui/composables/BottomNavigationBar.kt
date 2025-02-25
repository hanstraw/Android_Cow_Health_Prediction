package com.example.app.ui.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.app.ui.home.navigation.BottomNavItem
import com.example.app.ui.theme.AppTheme




@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Profile
    )

    AppTheme{
        NavigationBar(
            containerColor = NavigationBarDefaults.containerColor
        ) {
            val navBackStackEntry = navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry.value?.destination?.route

            items.forEach { item ->
                val selected = currentRoute == item.screen_route
                val selectedColor = MaterialTheme.colorScheme.primary
                val unselectedColor = MaterialTheme.colorScheme.onPrimaryContainer

                NavigationBarItem(
                    icon = {
                        Column (
//                            modifier = Modifier.padding(top = 3.dp, bottom = 0.dp)
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        )
                        {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = stringResource(id = item.title),
                                tint = if (selected) selectedColor else unselectedColor,
                                modifier = Modifier.size(if (selected) 28.dp else 24.dp) // 选中时放大图标
                                    .padding(top =if (selected) 0.dp else 2.dp)
                            )
                            Text(
                                stringResource(id = item.title),
                                color = if (selected) selectedColor else unselectedColor,
                                style = if (selected) MaterialTheme.typography.labelLarge else MaterialTheme.typography.labelSmall // 调整字体
                            )
                        }
                    },
                    label = {

                    },
                    selected = selected,
                    onClick = {
                        navController.navigate(item.screen_route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
//                        indicatorColor = NavigationBarDefaults.containerColor
                    )
                )
            }
        }
    }
}

