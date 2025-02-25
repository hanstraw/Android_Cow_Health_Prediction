package com.example.app.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.app.R
import com.example.app.data.model.LoginResponse
import com.example.app.data.model.UserResponse
import com.example.app.network.RegisterRequest
import com.example.app.network.RetrofitClient.apiService
import com.example.app.repository.DataRepository
import retrofit2.HttpException
import java.io.IOException

// 定义登录界面的 ViewModel 类，继承自 ViewModel
class LoginViewModel(private val repository: DataRepository) : ViewModel() {

    // 保存用户名和密码的可变状态
    private val _username = mutableStateOf("")
    val username: State<String> = _username

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    // 保存登录状态的可变状态
    private val _loginState = mutableStateOf<LoginState>(LoginState.Idle)
    val loginState: State<LoginState> = _loginState

    // 更新用户名
    fun onUsernameChanged(newUsername: String) {
        _username.value = newUsername
    }

    // 更新密码
    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    // 执行登录操作
    fun login(context: Context) {
        if (_username.value.isBlank() || _password.value.isBlank()) {
            _loginState.value = LoginState.Error(context.getString(R.string.username_or_password_empty))
            return
        }

        _loginState.value = LoginState.Loading

        viewModelScope.launch {
            try {
                val response = repository.login(_username.value, _password.value)
                _loginState.value = LoginState.Success(response)
            } catch (e: Exception) {
                _loginState.value = when (e) {
                    is IOException -> LoginState.Error(context.getString(R.string.network_error))
                    is HttpException -> {
                        if (e.code() == 401) {
                            LoginState.Error(context.getString(R.string.password_error))
                        } else {
                            LoginState.Error("${context.getString(R.string.login_failed)}: ${e.message}")
                        }
                    }
                    else -> LoginState.Error("${context.getString(R.string.login_failed)}: ${e.message}")
                }
            }
        }
    }

    private val _registerState = mutableStateOf<RegisterState>(RegisterState.Idle)
    val registerState = _registerState

    fun register(context: Context, username: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            try {
                val response = apiService.register(RegisterRequest(username, password, confirmPassword))
                if (response.isSuccessful) {
                    _registerState.value = RegisterState.Success(context.getString(R.string.register_success))
                } else {
                    _registerState.value = RegisterState.Error(context.getString(R.string.register_failed))
                }
            } catch (e: HttpException) {
                _registerState.value = RegisterState.Error(context.getString(R.string.register_failed))
            }
        }
    }
}

// 用于表示登录状态的 sealed class
sealed class LoginState {
    object Idle : LoginState() // 空闲状态
    object Loading : LoginState() // 加载状态
    data class Success(val token: LoginResponse) : LoginState() // 登录成功
    data class Error(val message: String) : LoginState() // 登录失败
}


sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    data class Success(val message: String) : RegisterState()
    data class Error(val message: String) : RegisterState()
}


