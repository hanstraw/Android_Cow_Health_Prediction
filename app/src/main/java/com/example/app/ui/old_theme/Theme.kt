package com.example.app.ui.old_theme

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF396b20),         // 主色调
    onPrimary = Color(0xFFFEFEFE),             // 主色调上显示的内容颜色
    primaryContainer = Color(0xFFB7F197),// 主色调的容器颜色
    onPrimaryContainer = Color(0xFF042300), // 容器内容颜色

    secondary = Color(0xFF55634B),       // 次要色调
    onSecondary = Color.White,           // 次要色调上的内容颜色
    secondaryContainer = Color(0xFFD9E7CB), // 次要色调容器
    onSecondaryContainer = Color(0xFF121F0C),  // 次要色调容器上的内容颜色

    background = Color(0xFFFCFDF3),      // 背景颜色
    onBackground = Color(0xFF000000),    // 背景上的内容颜色

    surface = Color(0xFFFCFDF3),         // 表面颜色
    onSurface = Color(0xFF000000),       // 表面上的内容颜色

    error = Color(0xFFB00020),           // 错误颜色
    onError = Color.White,               // 错误颜色上的内容颜色
)
//private val LightColorScheme = lightColorScheme(

//)


private val DarkColorScheme = LightColorScheme





@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun oldAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false    ,
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(MaterialTheme.colorScheme.surface, darkIcons = true)
    systemUiController.setNavigationBarColor(NavigationBarDefaults.containerColor, darkIcons = true)
    val colorScheme = when {
        dynamicColor -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}