package com.example.app.room

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DatabaseTestScreen(viewModel: MainViewModel = viewModel()) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val userList = remember { mutableStateListOf<User>() }
    // 获取所有用户
    LaunchedEffect(Unit) {
        viewModel.getAllUsers { users ->
            userList.clear()
            userList.addAll(users)
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // 输入用户信息
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("姓名") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("邮箱") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.addUser(name, email)  // 添加用户
                viewModel.getAllUsers { users ->  // 添加后刷新用户列表
                    userList.clear()
                    userList.addAll(users)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("添加用户")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 显示用户列表
        Text("用户列表", style = MaterialTheme.typography.titleLarge)
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(userList) { user ->
                Text(text = "姓名: ${user.name}, 邮箱: ${user.email}")
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

