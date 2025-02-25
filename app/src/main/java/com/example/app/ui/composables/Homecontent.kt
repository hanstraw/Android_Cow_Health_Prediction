package com.example.app.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.app.R
import com.example.app.ui.home.navigation.BottomNavItem
import com.example.app.ui.theme.AppTheme
import com.example.app.ui.web.HomeViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController




@Composable
fun homecontent(navController: NavHostController,viewModel2: HomeViewModel = viewModel()) {
    val isVisible = remember { true }
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(Color.Transparent, darkIcons = true)
        systemUiController.setNavigationBarColor(Color.Transparent, darkIcons = true)
    }
//    val systemUiController = rememberSystemUiController()
//    val c1 = MaterialTheme.colorScheme.surface
//    val c2 = MaterialTheme.colorScheme.surface
//    SideEffect {
//        systemUiController.setStatusBarColor(c1, darkIcons = true)
//        systemUiController.setNavigationBarColor(c2, darkIcons = true)
//    }
    Box {
        Column (
            modifier = Modifier.padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // 使页面可滑动
        ){
            Text(
                text = stringResource(id = R.string.predict_list),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
//                color = Color(0xFF000000)
            )
            Spacer(modifier = Modifier.height(16.dp))

            ////////////////////////////////////////////////////////////////////////
            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                modifier = Modifier
                    .padding(16.dp)
                    .height(130.dp)
                    .fillMaxWidth()
                    .clickable {
                        // 点击后导航到预测界面
                        navController.navigate(BottomNavItem.Pre1.screen_route)
                    },
                color = Color(0xFF009688),
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 4.dp,
                tonalElevation = 4.dp
            ) {
                Box(
                    contentAlignment = Alignment.Center, // 让内容居中对齐
                    modifier = Modifier.fillMaxSize() // 让 Box 填充 Surface
                ) {
                    Image(
                        painter = painterResource(R.drawable.p7),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(), // 让 Image 填充整个 Box
                        contentScale = ContentScale.Crop // 保证图片裁剪效果
                    )
                    Box(
                        modifier = Modifier
                            .height(40.dp)
                            .width(240.dp)
                            .shadow(10.dp, RoundedCornerShape(16.dp))
                            .graphicsLayer {
                                // 使用 graphicsLayer 以启用模糊效果
                                shadowElevation = 8.dp.toPx() // 设置阴影高度
                                shape = RoundedCornerShape(30.dp) // 圆角形状
                                clip = true // 确保背景和阴影效果应用于圆角区域
                                alpha = 0.8f // 设置透明度以模拟毛玻璃效果
                                renderEffect = BlurEffect(16.dp.toPx(), 16.dp.toPx()) // 使用模糊效果
                            }
                            .background(Color(0xFF1976D2), shape = RoundedCornerShape(30.dp)) // 设置背景颜色和圆角0xFF159287
                            .padding(horizontal = 30.dp, vertical = 3.dp) // 内边距
                            .align(Alignment.Center)
                    )
                    Text(
                        text = stringResource(id = R.string.rufang),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }

            }


            ////////////////////////////////////////////////////////////////////////

//            Surface(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .height(130.dp)
//                    .fillMaxWidth()
//                    .clickable {
//                        // 点击后导航到预测界面
//                        navController.navigate(BottomNavItem.Pre2.screen_route)
//                    },
//                color = Color(0xFF009688),
//                shape = RoundedCornerShape(16.dp),
//                shadowElevation = 4.dp,
//                tonalElevation = 4.dp
//            ) {
//                Box(
//                    contentAlignment = Alignment.Center, // 让内容居中对齐
//                    modifier = Modifier.fillMaxSize() // 让 Box 填充 Surface
//                ) {
//                    Image(
//                        painter = painterResource(R.drawable.p4),
//                        contentDescription = null,
//                        modifier = Modifier.fillMaxSize(), // 让 Image 填充整个 Box
//                        contentScale = ContentScale.Crop // 保证图片裁剪效果
//                    )
//                    Box(
//                        modifier = Modifier
//                            .height(40.dp)
//                            .width(240.dp)
//                            .graphicsLayer {
//                                // 使用 graphicsLayer 以启用模糊效果
//                                shadowElevation = 8.dp.toPx() // 设置阴影高度
//                                shape = RoundedCornerShape(30.dp) // 圆角形状
//                                clip = true // 确保背景和阴影效果应用于圆角区域
//                                alpha = 0.8f // 设置透明度以模拟毛玻璃效果
//                                renderEffect = BlurEffect(16.dp.toPx(), 16.dp.toPx()) // 使用模糊效果
//                            }
//                            .background(Color(0xFF159287), shape = RoundedCornerShape(30.dp)) // 设置背景颜色和圆角
//                            .padding(horizontal = 30.dp, vertical = 3.dp) // 内边距
//                            .align(Alignment.Center)
//                    )
//                    Text(
//                        text = "奶牛子宫炎预测",
//                        fontSize = 24.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.White,
//                        modifier = Modifier
//                            .align(Alignment.Center)
//                    )
//                }
//
//            }

            ////////////////////////////////////////////////////////////////////////

//            Surface(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .height(130.dp)
//                    .fillMaxWidth()
//                    .clickable {
//                        // 点击后导航到预测界面
//                        navController.navigate(BottomNavItem.Pre2_1.screen_route)
//                    },
//                color = Color(0xFF009688),
//                shape = RoundedCornerShape(16.dp),
//                shadowElevation = 4.dp,
//                tonalElevation = 4.dp
//            ) {
//                Box(
//                    contentAlignment = Alignment.Center, // 让内容居中对齐
//                    modifier = Modifier.fillMaxSize() // 让 Box 填充 Surface
//                ) {
//                    Image(
//                        painter = painterResource(R.drawable.p4),
//                        contentDescription = null,
//                        modifier = Modifier.fillMaxSize(), // 让 Image 填充整个 Box
//                        contentScale = ContentScale.Crop // 保证图片裁剪效果
//                    )
//                    Box(
//                        modifier = Modifier
//                            .height(40.dp)
//                            .width(240.dp)
//                            .graphicsLayer {
//                                // 使用 graphicsLayer 以启用模糊效果
//                                shadowElevation = 8.dp.toPx() // 设置阴影高度
//                                shape = RoundedCornerShape(30.dp) // 圆角形状
//                                clip = true // 确保背景和阴影效果应用于圆角区域
//                                alpha = 0.8f // 设置透明度以模拟毛玻璃效果
//                                renderEffect = BlurEffect(16.dp.toPx(), 16.dp.toPx()) // 使用模糊效果
//                            }
//                            .background(Color(0xFF159287), shape = RoundedCornerShape(30.dp)) // 设置背景颜色和圆角
//                            .padding(horizontal = 30.dp, vertical = 3.dp) // 内边距
//                            .align(Alignment.Center)
//                    )
//                    Text(
//                        text = "奶牛子宫炎预测",
//                        fontSize = 24.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.White,
//                        modifier = Modifier
//                            .align(Alignment.Center)
//                    )
//                }
//
//            }

            ////////////////////////////////////////////////////////////////////////

            Surface(
                modifier = Modifier
                    .padding(16.dp)
                    .height(130.dp)
                    .fillMaxWidth()
                    .clickable {
                        // 点击后导航到预测界面
                        navController.navigate(BottomNavItem.Pre2_2.screen_route)
                    },
                color = Color(0xFF009688),
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 4.dp,
                tonalElevation = 4.dp
            ) {
                Box(
                    contentAlignment = Alignment.Center, // 让内容居中对齐
                    modifier = Modifier.fillMaxSize() // 让 Box 填充 Surface
                ) {
                    Image(
                        painter = painterResource(R.drawable.p6),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(), // 让 Image 填充整个 Box
                        contentScale = ContentScale.Crop // 保证图片裁剪效果
                    )
                    Box(
                        modifier = Modifier
                            .height(40.dp)
                            .width(240.dp)
                            .shadow(10.dp, RoundedCornerShape(16.dp))
                            .graphicsLayer {
                                // 使用 graphicsLayer 以启用模糊效果
                                shadowElevation = 8.dp.toPx() // 设置阴影高度
                                shape = RoundedCornerShape(30.dp) // 圆角形状
                                clip = true // 确保背景和阴影效果应用于圆角区域
                                alpha = 0.8f // 设置透明度以模拟毛玻璃效果
                                renderEffect = BlurEffect(16.dp.toPx(), 16.dp.toPx()) // 使用模糊效果
                            }
                            .background(Color(0xFF1976D2), shape = RoundedCornerShape(30.dp)) // 设置背景颜色和圆角
                            .padding(horizontal = 30.dp, vertical = 3.dp) // 内边距
                            .align(Alignment.Center)
                    )
                    Text(
                        text = stringResource(id = R.string.zigong),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }

            }

            ////////////////////////////////////////////////////////////////////////

        }

    }
}