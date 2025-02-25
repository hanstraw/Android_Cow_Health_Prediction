package com.example.app.ui.home

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.example.app.room.MainViewModel
import com.example.app.ui.composables.DetailsActivity
import com.example.app.ui.composables.HomeScreen
import com.example.app.ui.old_theme.oldAppTheme
import com.example.app.ui.theme.AppTheme


class HomeActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
//        enableEdgeToEdge()

//        val viewModel = ViewModelProvider(
//            this,
//            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
//        )[MainViewModel::class.java]
        setContent {
            oldAppTheme(){
//                HomeScreen(viewModel)
                HomeScreen()
            }
        }
    }
}