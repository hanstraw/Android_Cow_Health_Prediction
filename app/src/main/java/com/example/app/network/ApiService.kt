package com.example.app.network

import com.example.app.data.model.CowHealthRequest
import com.example.app.data.model.CowHealthRequest2
import com.example.app.data.model.CowHealthRequest3
import com.example.app.data.model.LoginRequest
import com.example.app.data.model.LoginResponse
import com.example.app.data.model.PredictionResponse
import com.example.app.data.model.PredictionResponse2
import com.example.app.data.model.PredictionResponse3
import com.example.app.data.model.UserResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

//suspend 关键字表明这个方法是一个挂起函数，用于协程中执行异步操作。它使得网络请求可以在后台线程中执行，不会阻塞主线程。

interface ApiService {
    // 定义一个 GET 请求方法来获取用户信息，@Path 注解用于替换 URL 中的 {user} 占位符
    //@Path("user") 注解将参数 user 的值替换到 @GET 注解中 URL 路径的 {user} 部分，构成最终请求的 URL。
    @GET("users/{user}")
    suspend fun getUser(@Path("user") user: String): UserResponse

    //

    @POST("token/")
    suspend fun login(
        @Body request: LoginRequest // 使用 @Body 传递请求体
    ): LoginResponse


    @POST("pre2/")
    fun getCowHealthPrediction(@Body request: CowHealthRequest): Call<PredictionResponse>

    @POST("pre2/stacking/")
    fun getCowHealthPrediction2(@Body request: CowHealthRequest2): Call<PredictionResponse2>

    @POST("register/")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("pre2/xgboost/")
    fun getCowHealthPrediction3(@Body request: CowHealthRequest3): Call<PredictionResponse3>

}

data class RegisterRequest(
    val username: String,
    val password: String,
    val confirm_password: String
)

data class RegisterResponse(val message: String)