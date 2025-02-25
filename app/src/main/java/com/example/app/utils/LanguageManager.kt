package com.example.app.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale

object LanguageManager {
    private const val PREFS_NAME = "language_prefs"
    private const val LANGUAGE_KEY = "language_key"

    // 用于实时监听语言变化
    private val _currentLanguageState = MutableStateFlow(getSavedLanguage())
    val currentLanguageState: StateFlow<String> get() = _currentLanguageState

    private fun getSavedLanguage(): String {
        return Locale.getDefault().language // 默认语言为系统语言
    }

    fun getCurrentLanguage(): String {
        return _currentLanguageState.value
    }

    fun updateLanguage(context: Context, language: String) {
        // 保存语言到 SharedPreferences
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(LANGUAGE_KEY, language).apply()

        // 设置新的语言环境
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        // 更新 StateFlow 的值
        _currentLanguageState.value = language
    }

    fun initLanguage(context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val savedLanguage = prefs.getString(LANGUAGE_KEY, Locale.getDefault().language) ?: "zh"
        _currentLanguageState.value = savedLanguage
    }
}
//object LanguageManager {
//    private const val PREFERENCE_NAME = "app_preferences"
//    private const val LANGUAGE_KEY = "language"
//
//    private var currentLanguage: String = "zh" // 默认语言
//
//    // 初始化，从长期存储中读取语言设置
//    fun initialize(context: Context) {
//        val preferences = getPreferences(context)
//        currentLanguage = preferences.getString(LANGUAGE_KEY, "zh") ?: "zh"
//    }
//
//    // 获取当前语言
//    fun getCurrentLanguage(): String {
//        return currentLanguage
//    }
//
//    // 更新语言并保存到长期存储
//    fun updateLanguage(context: Context, newLanguage: String) {
//        currentLanguage = newLanguage
//        val preferences = getPreferences(context)
//        preferences.edit().putString(LANGUAGE_KEY, newLanguage).apply()
//    }
//
//    // 获取 SharedPreferences 实例
//    private fun getPreferences(context: Context): SharedPreferences {
//        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
//    }
//}