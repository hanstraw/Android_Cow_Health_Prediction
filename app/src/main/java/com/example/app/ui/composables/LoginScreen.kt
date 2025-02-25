package com.example.app.ui.composables

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.WindowCompat
import com.example.app.R
import com.example.app.network.NetworkModule
import com.example.app.repository.DataRepository
import com.example.app.ui.home.HomeActivity
import com.example.app.ui.old_theme.oldAppTheme
import com.example.app.ui.theme.AppTheme
import com.example.app.ui.viewmodel.LoginState
import com.example.app.ui.viewmodel.LoginViewModel
import com.example.app.ui.web.WebData
import com.example.app.utils.LanguageManager
import com.example.app.utils.LocaleHelper
import com.google.accompanist.systemuicontroller.rememberSystemUiController


class testloginActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
//        enableEdgeToEdge()
        setContent {
            val apiService = NetworkModule.provideApiService(NetworkModule.provideRetrofit())
            val repository = DataRepository(apiService)
            oldAppTheme(){
                LoginPage(LoginViewModel(repository))
            }

        }
    }
}

@Composable
fun LoginPage(viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val context = LocalContext.current
    // 使用 LanguageManager 的状态监听语言变化
    val currentLanguage by LanguageManager.currentLanguageState.collectAsState()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isFirstLaunch = remember { true }

    username = getUsername(context) ?: ""
    password = getPassword(context)?:""
    viewModel.onUsernameChanged(username)
    viewModel.onPasswordChanged(password)
    val loginState by viewModel.loginState

    // 获取系统UI控制器
    val systemUiController = rememberSystemUiController()
    // 设置透明的状态栏和导航栏
    systemUiController.setStatusBarColor(Color.Transparent, darkIcons = true)
    systemUiController.setNavigationBarColor(Color.Transparent, darkIcons = true)


    // 用于强制重组
    val forceRecompose = remember { mutableStateOf(0) }

    LaunchedEffect(currentLanguage) {
        // 通知界面刷新
        forceRecompose.value++
    }
    key(forceRecompose.value){
        Box(modifier = Modifier
            .fillMaxSize()

        ){
            Image(
                painter = painterResource(id = R.drawable.p2),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()

            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 0.dp)
                    .background(Color(0x00FFFFFF), shape = MaterialTheme.shapes.medium), // 半透明背景
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {


                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.welcome),
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp),
                    color = Color(0xFF333333)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = username,
                        onValueChange = {
                            viewModel.onUsernameChanged(it)
                            username = it
                            saveUsername(context, username)

                        }, // 更新 ViewModel 中的 username
                        label = { Text(stringResource(id = R.string.username)) },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            viewModel.onPasswordChanged(it)
                            savePassword(context, password)
                        }, // 更新 ViewModel 中的 password
                        label = { Text(stringResource(id = R.string.password)) },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { viewModel.login(context) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF528347), // 默认背景色
                        contentColor = Color.White, // 默认文字颜色
                        disabledContainerColor = Color.Gray, // 禁用状态背景色
                        disabledContentColor = Color.DarkGray, // 禁用状态文字颜色
                    ) ,
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(48.dp)
                        .border(1.dp, Color(0xB3528347), CircleShape),
                ) {
                    Text(stringResource(id = R.string.login))
                }

                LaunchedEffect(isFirstLaunch) {
                    if (isFirstLaunch) {
                        if(password!="" && username!=""){
                            viewModel.login(context)
                        }
                    }
                    isFirstLaunch = false
                }


                when (loginState) {
                    is LoginState.Loading -> {
                        Text(stringResource(id = R.string.logging_in)+"...")
                    }
                    is LoginState.Error -> Text(stringResource(id = R.string.error)+": ${(loginState as LoginState.Error).message}")
                    is LoginState.Success -> {
                        Text(stringResource(id = R.string.login_welcome))
                        WebData.username = username
                        val intent = Intent(context, HomeActivity::class.java)
                        context.startActivity(intent)
                    }
                    else -> {}
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Text(
                        text = stringResource(id = R.string.register),
                        modifier = Modifier
                            .clickable {
                                val intent = Intent(context, RegisterActivity::class.java)
                                context.startActivity(intent)
                            },
                        color = Color(0xFF000000),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.tourist_trial),
                        modifier = Modifier
                            .clickable {
                                if(true){
                                    val intent = Intent(context, HomeActivity::class.java)
                                    context.startActivity(intent)
                                }
                            },
//                        .border(1.dp, Color(0xFF007BFF)),
                        color = Color(0xFF000000),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }




                Button(
                    onClick = {
                        val newLanguage = if (currentLanguage == "zh") "en" else "zh"
                        LanguageManager.updateLanguage(context, newLanguage)
                        Toast.makeText(
                            context,
                            if (newLanguage == "zh") "已切换为中文" else "Switched to English",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(
                        text = if (currentLanguage == "zh") "切换为 English" else "Switch to 中文"
                    )
                }
            }
        }

    }







}

@Preview(showBackground = true)
@Composable
fun PreviewLoginPage() {
    LoginPage()
}

fun saveUsername(context: Context, username: String) {
    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("username", username)
    editor.apply() // 异步保存
}

fun getUsername(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString("username", null)
}


fun savePassword(context: Context, username: String) {
    val sharedPreferences = context.getSharedPreferences("Password_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("Password", username)
    editor.apply() // 异步保存
}

fun getPassword(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("Password_prefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString("Password", null)
}

