package com.example.app.ui.composables

import android.provider.Settings.Global.getString
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


@Composable
fun history() {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {

        RecordHistoryScreenWithFilter()
    }

}

@Preview
@Composable
fun record(){
    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(50.dp)
            .background(Color(0xFFF2F2F2))
    ) {
        Row (
            modifier = Modifier.fillMaxSize()
        ){
            name_box()


            Box(
                modifier = Modifier.width(150.dp)
            ){
                name_text(s = "name")
            }

            Box(
                modifier = Modifier.padding(top = 7.dp),
            ){
                percent(0.1f)
            }

            Box(
//                modifier = Modifier.padding(top =  10.dp),
            ){
                val now = ZonedDateTime.now()
                gary_time(dateTime = now)
            }
        }
    }
}



@Composable
fun name_text(s :String){
    Text(
        text = s,
        modifier = Modifier
            .padding(start = 10.dp),
        fontSize = 20.sp,
        color = Color(0xFF000000),
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
fun name_box(){
    var back_color = Color(0xFFA5D6A7)
    var zi_color = Color(0xFF009688)
    var zi = "乳"
    Surface(
        modifier = Modifier
            .padding(3.dp)
            .height(44.dp)
            .width(44.dp),
        shape = CircleShape,
        color = back_color,
//        shadowElevation = 4.dp,
//        tonalElevation = 4.dp,
    ){
        Text(
            text = zi,
            modifier = Modifier,
            fontSize = 28.sp,
            color = zi_color,
            textAlign = TextAlign.Center,
        )
    }
}




@Preview
@Composable
fun time() {
    // 创建一个 ZonedDateTime 对象
    val now = ZonedDateTime.now()

    // 显示格式化的时间
    gary_time(dateTime = now)
}

@Composable
fun white_time(dateTime: ZonedDateTime) {
    val formatter = DateTimeFormatter.ofPattern("M月d日 HH:mm")
    val formattedDateTime = dateTime.format(formatter)
    Text(
        text = formattedDateTime,
        color = Color(0xffffffff),
    )
}

@Composable
fun gary_time(dateTime: ZonedDateTime) {
    val formatter = DateTimeFormatter.ofPattern("M-d HH:mm")
    val formattedDateTime = dateTime.format(formatter)
    Text(
        text = formattedDateTime,
        color = Color(0xFF999999),
    )
}


@Composable
fun percent(value: Float) {
    val (label: String, color: Color) = when {
        value < 0.3 -> stringResource(id = com.example.app.R.string.state_0) to Color(0xFF689F38)
        value in 0.3..0.6 -> stringResource(id = com.example.app.R.string.state_1) to Color(0xFFFBC02D)
        else -> stringResource(id = com.example.app.R.string.state_2) to Color(0xFFC2185B)
    }

    val percentage = (value * 100).toInt()

    Text(
        text = "$label $percentage%",
        color = color,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.background(color = Color.White)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewPercentageDisplay() {
    Column{
        percent(0.25f)
        percent(0.45f)
        percent(0.65f)

    }
}