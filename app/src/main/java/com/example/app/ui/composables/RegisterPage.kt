package com.example.app.ui.composables

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import com.example.app.ui.viewmodel.RegisterState
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class RegisterActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val apiService = NetworkModule.provideApiService(NetworkModule.provideRetrofit())
            val repository = DataRepository(apiService)
            oldAppTheme{
                RegisterPage(LoginViewModel(repository))
            }
        }
    }
}




@Composable
fun RegisterPage(viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.White, darkIcons = true)
    systemUiController.setNavigationBarColor(Color.Transparent, darkIcons = true)
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordValid by remember { mutableStateOf(false) }
    var isUserNameValid by remember { mutableStateOf(false) }
    val registerState by viewModel.registerState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(132.dp))
        Text(
            text = stringResource(R.string.register_account),
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF333333)
        )

        Spacer(modifier = Modifier.height(32.dp))
        if (!isUserNameValid) {
            Text(text = stringResource(R.string.username_invalid_message))
        }
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                if (isValidUsername(it)) {
                    isUserNameValid = true
                }
            },
            label = { Text(stringResource(R.string.username_hint)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        if (!isPasswordValid) {
            Text(text = stringResource(R.string.password_invalid_message))
        }
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                if (isValidPassword(it)) {
                    isPasswordValid = true
                }
            },
            label = { Text(stringResource(R.string.password_hint)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text(stringResource(R.string.confirm_password_hint)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { viewModel.register(context, username, password, confirmPassword) },
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF528347))
        ) {
            Text(stringResource(R.string.register_button))
        }

        when (registerState) {
            is RegisterState.Loading -> Text(stringResource(R.string.loading))
            is RegisterState.Error -> Text(
                stringResource(
                    R.string.error_message,
                    (registerState as RegisterState.Error).message
                )
            )
            is RegisterState.Success -> {
                Text(stringResource(R.string.success_message))
                val intent = Intent(context, testloginActivity::class.java)
                context.startActivity(intent)
            }
            else -> {}
        }
        Spacer(modifier = Modifier.height(332.dp))
    }
}

fun isValidUsername(username: String): Boolean {
    // 正则表达式：至少3个字符，最多15个字符，只能包含字母、数字和下划线，且不能包含空格
    val usernameRegex = "^[a-zA-Z0-9_]{3,15}$".toRegex()
    return usernameRegex.matches(username)
}

fun isValidPassword(password: String): Boolean {
    // 正则表达式：至少8个字符，必须包含至少一个字母、一个数字，且不包含空格
    val passwordRegex = "^(?=.*[a-zA-Z])(?=.*\\d)[^\\s]{8,}$".toRegex()
    return passwordRegex.matches(password)
}