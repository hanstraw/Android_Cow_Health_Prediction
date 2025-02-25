package com.example.app.ui.composables


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.app.room.MainViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app.R
import com.example.app.ui.web.WebData.username
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.example.app.room.Record
import com.example.app.ui.web.HistoryViewModel
import com.example.app.ui.web.details_data
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.util.Calendar
import java.util.TimeZone


@Preview
@Composable
fun RecordItem(
    record: Record = Record(0,"","奶牛乳房炎预测","114514","","患病",System.currentTimeMillis()),
    onDeleteClick: () -> Unit = {},
    onClick:  () -> Unit = {},
    viewModel: HistoryViewModel =viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp, 0.dp, 0.dp)
            .background(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(4.dp))
            .clickable { onClick() },
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            var color = Color(0xFF388E3C)
            if (record.value in bad){
                color = badColor
            }
            if (record.value in mid){
                color = midColor
            }
            if(record.value in good){
                color = goodColor
            }
            shutiao(color)
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = record.title,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                ) {
                    val temp = if(record.cowid!="") record.cowid else stringResource(id = R.string.No_name)
                    Text(text = stringResource(id = com.example.app.R.string.cow_id)+": ${record.cowid}")
                }

            }

            val date = remember(record.timestamp) {
                java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault()).apply {
                    timeZone = java.util.TimeZone.getTimeZone("Asia/Shanghai") // 设置为中国时区
                }.format(java.util.Date(record.timestamp))
            }
            Spacer(modifier =Modifier.width(30.dp))
            Text(text = "$date", style = MaterialTheme.typography.bodyMedium)

            // 删除按钮
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                if(viewModel.deleteMode){
                    IconButton(onClick = onDeleteClick) {
                        Icon(Icons.Default.Delete, contentDescription = stringResource(id = com.example.app.R.string.delete_record))
                    }
                }else{
                    IconButton(onClick = {}, enabled = false ) {
                        Icon(Icons.Default.KeyboardArrowRight, contentDescription = stringResource(id = com.example.app.R.string.show_details))
                    }
                }
            }
        }
        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 0.dp, horizontal = 10.dp))
    }
}
