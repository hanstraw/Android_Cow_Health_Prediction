package com.example.app.ui.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.app.room.MainViewModel
import com.example.app.ui.home.navigation.BottomNavItem
import com.example.app.ui.theme.AppTheme
import com.example.app.ui.web.HomeViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController



// 创建 CompositionLocal 用于 NavController
val LocalNavController = compositionLocalOf<NavController> { error("NavController not provided") }
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun HomeScreen(
    viewModel: MainViewModel = viewModel(),
    viewModel2: HomeViewModel = viewModel()
) {
    val navController = rememberNavController()
    var showBottomBar by rememberSaveable { mutableStateOf(true) }


    navController.addOnDestinationChangedListener { _, destination, _ ->
        showBottomBar = destination.route in listOf(BottomNavItem.Home.screen_route,
            BottomNavItem.Search.screen_route,
            BottomNavItem.Profile.screen_route
            )
    }
    // 获取当前的页面路由
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    // 判断是否为需要隐藏 systemBarsPadding 的页面
    val shouldShowSystemBarsPadding = currentDestination != "search"
//    viewModel2.navController = navController
    Box(
        modifier = Modifier
            .fillMaxSize()
//            .systemBarsPadding(),
    ){
        Scaffold(
            bottomBar = {
                if (showBottomBar){
                    BottomNavigationBar(navController)
                }
            },
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            Box(modifier = Modifier.padding(0.dp)
                .systemBarsPadding()) {
                NavigationGraph(navController = navController)
            }
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    CompositionLocalProvider(LocalNavController provides navController) {
        // 设置导航
        NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
            composable(BottomNavItem.Home.screen_route) {
                homecontent(navController)
            }
            composable(BottomNavItem.Search.screen_route) {
                history()
            }
            composable(BottomNavItem.Profile.screen_route) {
                setting()
            }
            composable(BottomNavItem.Pre1.screen_route) {
                Pre1()
            }
            composable(BottomNavItem.Pre2.screen_route) {
                Pre2()
            }
            composable(BottomNavItem.Pre2_1.screen_route) {
                Pre2_1()
            }
            composable(BottomNavItem.Pre2_2.screen_route) {
                Pre2_2()
            }
            composable(BottomNavItem.detail.screen_route) {
                HistoryDetails()
            }
        }
    }

}