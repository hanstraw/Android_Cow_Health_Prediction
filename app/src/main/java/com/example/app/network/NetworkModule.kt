package com.example.app.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
//    private const val BASE_URL = "http://10.0.2.2:8000/api/"
    private const val BASE_URL = "http://frp-fly.top:27969/api/"

    // 提供 Retrofit 实例的方法，使用 Builder 模式进行配置
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL) // 设置基础 URL
            .addConverterFactory(GsonConverterFactory.create()) // 添加 Gson 转换器工厂
            .build()
    }

    // 提供 ApiService 实例的方法，利用 Retrofit 实例创建接口服务
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}


object RetrofitClient {
    private const val BASE_URL = "http://frp-fly.top:27969/api/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
