package com.example.app.ui.composables

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.example.app.utils.LanguageManager
import com.example.app.utils.LocaleHelper
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Preview(showBackground = true)
@Composable
fun ProfileSettingsScreen0() {
    val currentLanguage = remember { mutableStateOf(LanguageManager.getCurrentLanguage()) }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // 个人信息部分
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            elevation = CardDefaults.cardElevation(8.dp), // 设置阴影
            shape = RoundedCornerShape(16.dp), // 圆角
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp) // Card 内部边距
            ) {
                ProfileHeader()
            }
        }


        Spacer(modifier = Modifier.height(24.dp))

        // 设置选项部分
        SettingsOptionsCard()
        Spacer(modifier = Modifier.height(24.dp))
        SettingsOptionsCard2()
        // 当前语言显示
        Text(
            text = "当前语言：${if (currentLanguage.value == "zh") "中文" else "English"}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 切换语言按钮
        Button(
            onClick = {
                val newLanguage = if (currentLanguage.value == "zh") "en" else "zh"
                LanguageManager.updateLanguage(context, newLanguage)
                LocaleHelper.setLocale(context, newLanguage)

                // 让语言设置立刻生效
                currentLanguage.value = newLanguage
                Toast.makeText(
                    context,
                    if (newLanguage == "zh") "已切换为中文" else "Switched to English",
                    Toast.LENGTH_SHORT
                ).show()
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = if (currentLanguage.value == "zh") "切换为 English" else "Switch to 中文"
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileHeader() {
    Column(
        modifier = Modifier.fillMaxWidth(),
//        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 头像
            Image(
                painter = painterResource(id = R.drawable.p1),
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .border(2.dp, MaterialTheme.colorScheme.onPrimary, CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // 用户名和描述
            Column {
                Text(
                    text = "用户名",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "备注信息：无",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
            }
        }
//        Text(
//            text = "备注信息：无",
//            style = MaterialTheme.typography.bodySmall,
//            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
//        )
    }

}

@Composable
fun SettingsOptionsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp), // 设置阴影
        shape = RoundedCornerShape(16.dp), // 圆角
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp) // Card 内部边距
        ) {
            SettingItem(icon = Icons.Default.Person, title = "账户设置", onClick = { /* 打开账户设置 */ })
            Divider(modifier = Modifier.padding(vertical = 8.dp)) // 分隔线
            SettingItem(icon = Icons.Default.Notifications, title = "通知设置", onClick = { /* 打开通知设置 */ })
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            SettingItem(icon = Icons.Default.Lock, title = "隐私设置", onClick = { /* 打开隐私设置 */ })
//            Divider(modifier = Modifier.padding(vertical = 8.dp))
//            SettingItem(icon = Icons.Default.Language, title = "语言设置", onClick = { /* 打开语言设置 */ })
//            Divider(modifier = Modifier.padding(vertical = 8.dp))
//            SettingItem(icon = Icons.Default.Info, title = "关于", onClick = { /* 打开关于界面 */ })
        }
    }
}

@Composable
fun SettingsOptionsCard2() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp), // 设置阴影
        shape = RoundedCornerShape(16.dp), // 圆角
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp) // Card 内部边距
        ) {
//            SettingItem(icon = Icons.Default.Person, title = "账户设置", onClick = { /* 打开账户设置 */ })
//            Divider(modifier = Modifier.padding(vertical = 8.dp)) // 分隔线
//            SettingItem(icon = Icons.Default.Notifications, title = "通知设置", onClick = { /* 打开通知设置 */ })
//            Divider(modifier = Modifier.padding(vertical = 8.dp))
//            SettingItem(icon = Icons.Default.Lock, title = "隐私设置", onClick = { /* 打开隐私设置 */ })
//            Divider(modifier = Modifier.padding(vertical = 8.dp))
//            SettingItem(icon = Icons.Default.Language, title = "语言设置", onClick = { /* 打开语言设置 */ })
//            Divider(modifier = Modifier.padding(vertical = 8.dp))
            SettingItem(icon = Icons.Default.Info, title = "关于", onClick = { /* 打开关于界面 */ })
        }
    }
}




@Composable
fun SettingItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}


