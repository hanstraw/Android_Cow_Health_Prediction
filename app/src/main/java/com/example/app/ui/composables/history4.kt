package com.example.app.ui.composables

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview(showBackground = true)
@Composable
fun shutiao(color: Color= Color.Green){
    Surface (
        color = color,
        modifier =  Modifier.height(60.dp)
            .padding(7.dp)
            .width(4.dp),
        contentColor = color,
        shape = CircleShape,
    ){

    }
}

val bad = arrayOf("患病", "极大概率患病")
val mid = arrayOf( "有可能患病")
val good = arrayOf("健康", "患病概率较低")

val badColor = Color(0xFFE53935)
val midColor = Color(0xFFFDD835)
val goodColor = Color(0xFF7CB342)