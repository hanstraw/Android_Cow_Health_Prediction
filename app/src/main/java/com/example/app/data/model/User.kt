package com.example.app.data.model

import com.google.gson.annotations.SerializedName


data class User(
    val username: String,
    val password: String
)

// 定义 UserResponse 数据类以匹配 API 返回的数据结构
data class UserResponse(
    @SerializedName("username") val id: Int,
    @SerializedName("password") val name: String,
    @SerializedName("email") val email: String

)

data class LoginResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val tokenType: String,
//    @SerializedName("expires_in") val expiresIn: Int
)

data class LoginRequest(
    val username: String,
    val password: String
)

data class CowHealthRequest(
    val cow_breed_code: Int,                // 奶牛品种代码
    val calving_year: Int,                  // 分娩年份
    val calving_seasons: Int,               // 分娩季节
    val parity_class: Int,                  // 胎次类别
    val dry_period_length_class: Int,       // 干奶期长度类别
    val pregnancy_length_class: Int,        // 怀孕长度类别
    val body_condition_score_pa: Float,     // 体况评分
    val age_aft_first_calving_day_class: Int, // 初次分娩后年龄类别
    val twining_status: Int,                // 双胞胎状态
    val calf_sex: Int,                      // 小牛性别
    val calf_birth_weight_class: Int        // 小牛出生体重类别
)


data class PredictionResponse(
    val logistic_regression: Float,
    val decision_tree: Float,
    val random_forest: Float,
    val xgboost: Float,
    val lightgbm: Float,
    val stacking: Float
)

data class CowHealthRequest2(
    val cow_breed_code: Int,                // 奶牛品种代码
    val calving_year: Int,                  // 分娩年份
    val calving_seasons: Int,               // 分娩季节
    val parity_class: Int,                  // 胎次类别
    val dry_period_length_class: Int,       // 干奶期长度类别
    val pregnancy_length_class: Int,        // 怀孕长度类别
    val body_condition_score_pa: Float,     // 体况评分
    val age_aft_first_calving_day_class: Int, // 初次分娩后年龄类别
    val twining_status: Int,                // 双胞胎状态
    val calf_sex: Int,                      // 小牛性别
    val calf_birth_weight_class: Int        // 小牛出生体重类别
)


data class PredictionResponse2(
    val stacking: Float,
)

data class CowHealthRequest3(
    val user_name: String,
    val cow_id: String,
    val cow_breed_code: Int,                // 奶牛品种代码
    val calving_seasons: Int,               // 分娩季节
    val parity_class: Int,                  // 胎次类别
    val dry_period_length_class: Int,       // 干奶期长度类别
    val pregnancy_length_class: Int,        // 怀孕长度类别
    val body_condition_score_pa: Float,     // 体况评分
    val age_aft_first_calving_day_class: Int, // 初次分娩后年龄类别
    val twining_status: Int,                // 双胞胎状态
    val calf_sex: Int,                      // 小牛性别
    val calf_birth_weight_class: Int        // 小牛出生体重类别
)


data class PredictionResponse3(
    val xgboost: Float,
)