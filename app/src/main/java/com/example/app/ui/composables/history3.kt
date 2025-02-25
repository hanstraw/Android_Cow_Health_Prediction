package com.example.app.ui.composables


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app.R
import com.example.app.room.MainViewModel
import java.text.SimpleDateFormat
import java.util.*
import com.example.app.room.Record
import com.example.app.ui.home.HomeActivity
import com.example.app.ui.web.HistoryViewModel
import com.example.app.ui.web.HomeViewModel
import com.example.app.ui.web.RecordFilterViewModel
import com.example.app.ui.web.WebData.username
import com.example.app.ui.web.details_data
import com.example.app.ui.web.filter
import com.example.app.utils.LanguageManager
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun RecordHistoryScreenWithFilter(
    viewModel: MainViewModel = viewModel(),
    viewModel2: HistoryViewModel =viewModel(),
    viewModel3: HomeViewModel = viewModel()
) {
    val navController = LocalNavController.current
    val context = LocalContext.current
    Column (
        modifier = Modifier.fillMaxSize()
            ,
        horizontalAlignment = Alignment.CenterHorizontally,

    ){
        var show1 by remember { mutableStateOf(false) }
        // 从 ViewModel 中获取所有记录
        val records by viewModel.getRecordsByUserId(username).collectAsState(initial = emptyList())

        // 初始筛选记录状态
//        var filteredRecords by remember { mutableStateOf<List<Record>>(emptyList()) }

        // 当 records 变化时，初始化 filteredRecords 为所有记录，按时间降序排序
        LaunchedEffect(records) {
            viewModel2.filteredRecords = records.filter { record ->
                // 检查牛号、标题、日期是否匹配筛选条件
                (filter.cowId.isEmpty() || record.cowid.contains(filter.cowId, ignoreCase = true)) &&
                        (filter.title.isEmpty() || record.title.contains(filter.title, ignoreCase = true)) &&
                        (filter.startDate == Long.MIN_VALUE || record.timestamp >= filter.startDate) &&
                        (filter.endDate == Long.MAX_VALUE || record.timestamp < filter.endDate+86400000)
            }.sortedByDescending { it.timestamp }
//            filteredRecords = records.sortedByDescending { it.timestamp }
        }
        // 显示筛选后的记录
        LazyColumn {
            item{
                if(show1){

                        Column {
                            // 筛选器组件
                            RecordFilter( { cowId, title, startDate, endDate ->
                                // 根据条件筛选记录
                                viewModel2.filteredRecords = records.filter { record ->
                                    // 检查牛号、标题、日期是否匹配筛选条件
                                    (cowId.isEmpty() || record.cowid.contains(cowId, ignoreCase = true)) &&
                                            (title.isEmpty() || record.title.contains(title, ignoreCase = true)) &&
                                            (startDate == null || record.timestamp >= startDate) &&
                                            (endDate == null || record.timestamp <= endDate)
                                }.sortedByDescending { it.timestamp }
                            })
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ){
                                IconButton(onClick = { show1 = false }) {
                                    Icon(
                                        imageVector = Icons.Filled.KeyboardArrowUp,
                                        contentDescription = "收起"
                                    )
                                }
                            }
                            Divider(color = Color.Gray, thickness = 2.dp, modifier = Modifier.padding(vertical = 0.dp, horizontal = 0.dp))

                        }


                }
                if(!show1){
                    // 向下的按钮
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.surface),
                        horizontalArrangement = Arrangement.Center
                    ){
                        IconButton(onClick = { show1 = true}) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowDown,
                                contentDescription = "展开"
                            )
                        }

                    }
                    Divider(color = Color.Gray, thickness = 2.dp, modifier = Modifier.padding(vertical = 0.dp, horizontal = 0.dp))
                }
            }
            items(viewModel2.filteredRecords) { record ->
                RecordItem(
                    record = record,
                    onDeleteClick = { viewModel.deleteRecordById(record.id) },
                    onClick = {
                        if(!viewModel2.deleteMode){
                            val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).apply {
//                                timeZone = TimeZone.getTimeZone("Asia/Shanghai")
                            }
                            details_data.date = dateFormatter.format(record.timestamp)
                            details_data.description = record.description
                            details_data.id =record.id
                            details_data.cowid=record.cowid
                            details_data.title=record.title
                            details_data.value=record.value
                            val intent = Intent(context, DetailsActivity::class.java)
                            context.startActivity(intent)
                        }
                    }
                )
            }
        }

    }

}

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordFilter(
    onFilter: (String, String, Long?, Long?) -> Unit = { s: String, s1: String, l: Long?, l1: Long? -> },
    viewmodel: RecordFilterViewModel= viewModel(),
    viewModel2: HistoryViewModel = viewModel()
) {
//    // 牛号和标题的状态
//    var cowId by remember { mutableStateOf(TextFieldValue("")) }
//    var title by remember { mutableStateOf(TextFieldValue("")) }
//
//    // 日期选择器的状态
//    var startDate by remember{ mutableStateOf<Long?>(null) }
//    var endDate by remember { mutableStateOf<Long?>(null) }

    // 日期格式化工具
    val dateFormatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
//        timeZone = TimeZone.getTimeZone("Asia/Shanghai") // 设置为中国时区
    } }
    Column(modifier = Modifier.padding(16.dp)) {
        // 输入框 - 牛号
        OutlinedTextField(
            value = viewmodel.cowId,
            onValueChange = { viewmodel.cowId = it },
            label = { Text(stringResource(id = R.string.cow_id)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 输入框 - 标题
        var expanded1 by remember { mutableStateOf(false) }
        var all = stringResource(id = R.string.all)
        var selectedOption by remember { mutableStateOf(all) }
        val options = listOf(all, stringResource(id = R.string.rufang), stringResource(id = R.string.zigong))

        ExposedDropdownMenuBox(
            expanded = expanded1,
            onExpandedChange = { expanded1 = !expanded1 } // 当点击时展开或收起下拉菜单
        ) {
            // TextField 显示已选择的选项
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {
                        selectedOption = it

                },
                readOnly = true, // 禁止手动输入，使用下拉选择
                label = { Text(stringResource(id = R.string.type)) },
                modifier = Modifier
                    .menuAnchor() // 下拉菜单锚点
                    .fillMaxWidth()
            )

            // 下拉菜单
            ExposedDropdownMenu(
                expanded = expanded1,
                onDismissRequest = { expanded1 = false } // 点击外部区域时收起菜单
            ) {
                // 遍历选项并显示在菜单中
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedOption = option // 选择某一项时更新已选中的值
                            if(selectedOption == all){
                                viewmodel.title= TextFieldValue("")
                            }else{
                                viewmodel.title= TextFieldValue(selectedOption)
                            }
                            expanded1 = false // 关闭菜单
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(3.dp))

        Column (
            modifier = Modifier.fillMaxWidth(),

        ) {
            // 选择起始日期
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                var s1 = stringResource(id = R.string.start_day)
                var s2 = stringResource(id = R.string.No_choice)
                Text(
                    "${s1}：${viewmodel.startDate?.let { dateFormatter.format(Date(it)) } ?: s2}",
                    style = TextStyle(fontSize = 16.sp)
                )
                DatePicker { selectedDate ->
                    viewmodel.startDate = selectedDate
                }
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                var s1 = stringResource(id = R.string.end_day)
                var s2 = stringResource(id = R.string.No_choice)
                // 选择结束日期
                Text("${s1}：${viewmodel.endDate?.let { dateFormatter.format(Date(it)) } ?: s2}",
                    style = TextStyle(fontSize = 16.sp))
                DatePicker { selectedDate ->
                    viewmodel.endDate = selectedDate
                }
            }
        }
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                stringResource(id = R.string.delete_mode),
                style = TextStyle(fontSize = 16.sp))
            Checkbox(checked = viewModel2.deleteMode, onCheckedChange = { viewModel2.deleteMode = it })
        }

        // 筛选按钮
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Button(
                onClick = {
                    // 触发筛选逻辑
                    onFilter(viewmodel.cowId.text, viewmodel.title.text, viewmodel.startDate, viewmodel.endDate)
                    filter.cowId = viewmodel.cowId.text
                    filter.title = viewmodel.title.text
                    filter.startDate = if(viewmodel.startDate!= null) viewmodel.startDate!! else Long.MIN_VALUE
                    filter.endDate = if(viewmodel.endDate!= null) viewmodel.endDate!! else Long.MAX_VALUE
                },
                modifier = Modifier.fillMaxWidth(0.7f),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(stringResource(id = R.string.filter))
            }
        }
    }
}



@Composable
fun DatePicker(
    onDateSelected: (Long) -> Unit // 选择日期后的回调
) {
    // 使用手机当前的时区
    val calendar = Calendar.getInstance(TimeZone.getDefault())
    val context = LocalContext.current

    // 从保存的语言设置获取当前语言
    val currentLanguage = LanguageManager.getCurrentLanguage()

    // 创建与当前语言一致的 Locale
    val currentLocale = when (currentLanguage) {
        "zh" -> Locale("zh", "CN") // 中文
        "en" -> Locale("en", "US") // 英文
        else -> Locale.getDefault() // 默认语言
    }

    // 创建 DatePickerDialog 时设置为当前语言
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, day: Int ->
                calendar.set(year, month, day)
                // 设置时、分、秒和毫秒都为 0
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)

                onDateSelected(calendar.timeInMillis) // 返回设置时分秒后的时间戳
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            // 设置 DatePickerDialog 的语言环境
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                this.context.createConfigurationContext(
                    context.resources.configuration.apply {
                        setLocale(currentLocale)
                    }
                )
            }
        }
    }

    Button(
        onClick = { datePickerDialog.show() },
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(stringResource(id = com.example.app.R.string.choose_date))
    }
}



