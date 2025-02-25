package com.example.app

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
import com.example.app.ui.composables.testloginActivity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import java.util.Locale
import androidx.profileinstaller.ProfileInstaller
import com.example.app.utils.LanguageManager
import com.example.app.utils.LocaleHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Locale.setDefault(Locale.CHINESE)
        val config = resources.configuration
        config.setLocale(Locale.CHINESE)
        resources.updateConfiguration(config, resources.displayMetrics)
        // 初始化语言设置
        LanguageManager.initLanguage(this)
        LocaleHelper.setLocale(this, LanguageManager.getCurrentLanguage())

        // 写入 ProfileInstaller
        ProfileInstaller.writeProfile(this)
        startActivity(Intent(this, testloginActivity::class.java))
        finish()

    }
}
