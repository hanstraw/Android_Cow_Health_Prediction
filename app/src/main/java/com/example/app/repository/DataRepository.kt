package com.example.app.repository

import com.example.app.data.model.LoginRequest
import com.example.app.network.ApiService

class DataRepository(private val apiService: ApiService) {

    // suspend 函数用于执行网络请求
    suspend fun getUser(user: String) = apiService.getUser(user)

    suspend fun login(username: String,password: String) = apiService.login(LoginRequest(username,password))
    suspend fun register(username: String, password: String) {

    }
}