package com.example.app.ui.composables

import android.location.Location
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.app.R
import com.example.app.pytorch.runModelPrediction
import com.example.app.room.MainViewModel
import com.example.app.ui.web.WebData
import com.example.app.ui.web.pre1data
import com.example.app.ui.web.pre1data.elc_avg
import com.example.app.ui.web.pre1data.elc_std
import com.example.app.ui.web.pre1data.lay_avg
import com.example.app.ui.web.pre1data.lay_std
import com.example.app.ui.web.pre1data.prd_avg
import com.example.app.ui.web.pre1data.prd_std
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.lang.Math.sqrt
import java.util.Calendar
import java.util.TimeZone
import kotlin.math.pow

@Preview(showBackground = true)
@Composable
fun Pre1() {
//fun Pre1(dbView: MainViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = Modifier.height(32.dp))
        val context = LocalContext.current
        var niuhao by remember {mutableStateOf("")}
        var lactationPeriod by remember {mutableStateOf("")}
        var lactationDays by remember {mutableStateOf("")}
        var predictionResult by remember { mutableStateOf("") }
        var message by remember { mutableStateOf("") }
        var reload by remember { mutableStateOf(false ) }
        var show by remember { mutableStateOf(true ) }
        var cnt by remember { mutableStateOf(0 ) }

        Text(
            text = stringResource(id = R.string.rufang),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF000000),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))



        OutlinedTextField(
            label = { Text(text = stringResource(id = R.string.cow_id))},
            value = niuhao,
            onValueChange = {
                if (it.length <= 20) { // 限制输入的字符数
                    niuhao = it
                    pre1data.id = niuhao
                }
            },
            modifier = Modifier
        )


        OutlinedTextField(
            label = {Text(text = stringResource(id = R.string.lactation_period))},
            value = lactationPeriod,
            onValueChange = {
                lactationPeriod = it
                pre1data.lactationPeriod = lactationPeriod
                var test = 1
            },
            textStyle = LocalTextStyle.current.copy(
                fontSize = 16.sp,
                lineHeight = 16.sp
            ),
            modifier = Modifier,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )


        OutlinedTextField(
            label = {Text(text = stringResource(id = R.string.lactation_days))},
            value = lactationDays,
            onValueChange = {
                lactationDays = it
                pre1data.lactationDays = lactationDays
                var test = 1
            },
            textStyle = LocalTextStyle.current.copy(
                fontSize = 16.sp,
                lineHeight = 16.sp
            ),
            modifier = Modifier,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )


        if(show){
            newInputDialog(stringResource(id = R.string.conductivity),5){ s1,s2->
                pre1data.elc_avg =s1.toFloatOrNull() ?: pre1data.elc_avg
                pre1data.elc_std =s2.toFloatOrNull() ?: pre1data.elc_std
            }
            newInputDialog(stringResource(id = R.string.production),5){ s1,s2->
                pre1data.prd_avg =s1.toFloatOrNull() ?: pre1data.prd_avg
                pre1data.prd_std =s2.toFloatOrNull() ?: pre1data.prd_std
            }
            newInputDialog(stringResource(id = R.string.lying_time),5){ s1,s2->
                pre1data.lay_avg =s1.toFloatOrNull() ?: pre1data.lay_avg
                pre1data.lay_std =s2.toFloatOrNull() ?: pre1data.lay_std
            }

        }


        val coroutineScope = rememberCoroutineScope()
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ){
            Button(
                onClick = {
//                    niuhao = ""
                    lactationPeriod = ""
                    lactationDays = ""
                    pre1data.prd_avg = 0f
                    pre1data.prd_std = 0f
                    pre1data.elc_std = 0f
                    pre1data.elc_std = 0f
                    pre1data.lay_avg = 0f
                    pre1data.lay_std = 0f
                    predictionResult = ""
                    message = ""
                    coroutineScope.launch {
                        show = false // 第一步，隐藏
                        delay(100) // 延迟
                        show = true // 第二步，重新显示
                    }

                },
                modifier = Modifier
                    .padding(5.dp),

                shape = RoundedCornerShape(5.dp),
            ) {
                Text(stringResource(id = R.string.reset))
            }
            var a = stringResource(id = R.string.makesure)
            Spacer(modifier = Modifier.height(70.dp))
            val state0 = stringResource(id = R.string.state_0)
            val state2 = stringResource(id = R.string.state_2)
            Button(
                onClick = {
                    predictionResult = ""
                    if (lactationPeriod.isEmpty() || lactationDays.isEmpty() ||
                        pre1data.prd_avg == 0.toFloat() || pre1data.prd_std == 0.toFloat() ||
                        pre1data.elc_avg == 0.toFloat() || pre1data.elc_std == 0.toFloat() ||
                        pre1data.lay_avg == 0.toFloat() || pre1data.lay_std == 0.toFloat()) {
                                message = a
//                        predictionResult = "请确保所有输入都已填写。"
                        return@Button
                    }
                    val inputs = floatArrayOf(
                        lactationPeriod.toFloat(),
                        lactationDays.toFloat(),
                        pre1data.prd_avg.toFloat(),
                        pre1data.prd_std.toFloat(),
                        pre1data.elc_std.toFloat(),
                        pre1data.elc_std.toFloat(),
                        pre1data.lay_avg.toFloat(),
                        pre1data.lay_std.toFloat(),
                    )
                    // 调用模型预测
                    message = ""
                    val result = runModelPrediction(context, inputs)
                    predictionResult = result.toString()
                    if(result>0.5){
                        predictionResult = state2
                    }else{
                        predictionResult = state0
                    }
                },
                modifier = Modifier
                    .padding(5.dp),

                shape = RoundedCornerShape(5.dp),
            ) {
                Text(stringResource(id = R.string.predict))
            }
        }
        if(message!=""){
            Box(
                modifier = Modifier
                    .fillMaxWidth() // 使 Box 占满整行
                    .padding(16.dp) // 可选：添加一些内边距
            ) {
                Text(
                    text = message,
                    modifier = Modifier.fillMaxWidth(), // 使 Text 占满整行
                    textAlign = TextAlign.Center, // 文字居中
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
        if (predictionResult != "") {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp), // 圆角矩形
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // 增加卡片的阴影
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.result),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary // 改变颜色
                    )
                    Divider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.this_cow)+" $predictionResult",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface // 改变颜色
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        } else {
            Spacer(modifier = Modifier.height(176.dp))
        }
        Spacer(modifier = Modifier.height(76.dp))
        if (predictionResult != "") {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                // 表头
                Text(stringResource(id = R.string.rufang_form)+"：", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                // 输入数据的列名
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.input),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = stringResource(id = R.string.value),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                }

                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

                // 表格的每一行，显示输入的数据
                TableRow(stringResource(id = R.string.lactation_period), lactationPeriod)
                TableRow(stringResource(id = R.string.lactation_days), lactationDays)
                TableRow(stringResource(id = R.string.average_yield), pre1data.prd_avg.toString())
                TableRow(stringResource(id = R.string.std_of_output), pre1data.prd_std.toString())
                TableRow(stringResource(id = R.string.mean_conductivity), pre1data.elc_avg.toString())
                TableRow(stringResource(id = R.string.std_of_conductivity), pre1data.elc_std.toString())
                TableRow(stringResource(id = R.string.mean_lying_time), pre1data.lay_avg.toString())
                TableRow(stringResource(id = R.string.std_of_lying_time), pre1data.lay_std.toString())

                Spacer(modifier = Modifier.height(16.dp))  // 输入数据与预测结果之间的间距

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.result),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                }

                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

                Row(modifier = Modifier.fillMaxWidth()){
                    Text(
                        text = stringResource(id = R.string.this_cow)+ predictionResult,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                }

                val s1 = stringResource(id = R.string.lactation_period)
                val s2 = stringResource(id = R.string.lactation_days)
                val s3 = stringResource(id = R.string.average_yield)
                val s4 = stringResource(id = R.string.std_of_output)
                val s5 = stringResource(id = R.string.mean_conductivity)
                val s6 = stringResource(id = R.string.std_of_conductivity)
                val s7 = stringResource(id = R.string.mean_lying_time)
                val s8 = stringResource(id = R.string.std_of_lying_time)




                val description = """
                        ${s1}: $lactationPeriod
                        ${s2}: $lactationDays
                        ${s3}: $prd_avg
                        ${s4}: $prd_std
                        ${s5}: $elc_avg
                        ${s6}: $elc_std
                        ${s7}: $lay_avg
                        ${s8}: $lay_std
                    """.trimIndent()

                val calendar = Calendar.getInstance().apply {
                    timeZone = TimeZone.getTimeZone("Asia/Shanghai")
                }
                val timestamp = calendar.timeInMillis


//                dbView.addRecord(
//                    username = WebData.username,
//                    cowid = niuhao,
//                    title = stringResource(id = R.string.rufang),
//                    description = description,
//                    value =  predictionResult,
//                    timestamp = timestamp
//                )
            }
        } else {
            Spacer(modifier = Modifier.height(176.dp))
        }
        Spacer(modifier = Modifier.height(200.dp))

    }
}

// 辅助函数：表格行
@Composable
fun TableRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewInputDialogExample() {
//    InputDialog()
    newInputDialog("电导率", 5){ s1,s2->

    }
}


@Composable
fun newInputDialog(name: String, dayCount: Int,function: (String, String) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    val inputTexts = remember { mutableStateListOf<String>() }
    var pingjun by remember { mutableStateOf("") }
    var biaozhuncha  by remember { mutableStateOf("") }
    // 初始化输入框
    LaunchedEffect(dayCount) {
        inputTexts.clear()
        repeat(dayCount) {
            inputTexts.add("")
        }
    }
    Column(
//        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = name,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 6.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment= Alignment.CenterVertically
        ){

            InputRow2(labelname = stringResource(id = R.string.average),inputValue = pingjun) {
                pingjun = it
                function(pingjun,biaozhuncha)
            }
            InputRow2(labelname = stringResource(id = R.string.standard),inputValue = biaozhuncha) {
                biaozhuncha = it
                function(pingjun,biaozhuncha)
            }


        }

        Button(
            onClick = { showDialog = true },
            modifier = Modifier
                .padding(5.dp)
                .height(50.dp)
                .width(200.dp),
            shape = RoundedCornerShape(5.dp),
            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
        ) {
            Text(stringResource(id = R.string.input_by_day), fontSize = 16.sp)
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(stringResource(id = R.string.input0)+ name +stringResource(id = R.string.message)) },
                text = {
                    LazyColumn {
                        items(dayCount) { index ->
                            InputRow(label = " day${index + 1} ", inputValue = inputTexts[index]) { inputTexts[index] = it }
                        }
                    }
                },
                confirmButton = {
                    Box(contentAlignment = Alignment.Center){
                        Button(
                            onClick = {
                                calculateStatistics(inputTexts, onAverageCalculated = { average ->
                                    pingjun = safeSubstring(average.toString(),0,6)
                                }, onStdDevCalculated = { stdDev ->
                                    biaozhuncha = safeSubstring(stdDev.toString(),0,6)
                                })
                                showDialog = false
                                function(pingjun,biaozhuncha)
                            }
                        ) {
                            Text(stringResource(id = R.string.sure))
                        }
                    }

                },
                dismissButton = {
                    Box( contentAlignment = Alignment.Center){
                        Button(onClick = { showDialog = false }) {
                            Text(stringResource(id = R.string.cancel))
                        }
                    }

                }
            )
        }

    }
}

@Composable
fun InputRow2(labelname: String ="", inputValue: String, onValueChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .width(150.dp)
    ) {
        OutlinedTextField(
            label={ Text(labelname) },
            value = inputValue,
            onValueChange = { newText: String ->
                if (newText.all { char -> char.isDigit()or (char == '.') }) {
                    onValueChange(newText)
                }
            },
//            textStyle = LocalTextStyle.current.copy(
//                fontSize = 16.sp,
//                lineHeight = 16.sp
//            ),
            modifier = Modifier,
//                .height(70.dp)
//                .padding(0.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
    }
}

@Composable
fun InputRow(label: String, inputValue: String, onValueChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(2f),
            fontSize = 20.sp
        )
        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                // 只允许输入数字
                if (it.all { char -> char.isDigit()or (char == '.') }) {
                    onValueChange(it)
                }
            },
            textStyle = LocalTextStyle.current.copy(
                fontSize = 16.sp,
                lineHeight = 16.sp
            ),
            modifier = Modifier
                .padding(0.dp)
                .weight(5f)
                .height(50.dp)
                .padding(0.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
    }
}

fun calculateStatistics(inputTexts: List<String>, onAverageCalculated: (Double) -> Unit, onStdDevCalculated: (Double) -> Unit) {
    val values = inputTexts.mapNotNull { it.toDoubleOrNull() } // 转换为 Double，忽略无效输入

    if (values.isNotEmpty()) {
        // 计算平均值
        val average = values.average()
        onAverageCalculated(average)

        // 计算标准差
        val stdDev = sqrt(values.map { (it - average).pow(2) }.average())
        onStdDevCalculated(stdDev)
    } else {
        // 如果没有有效值，则设置为 0
        onAverageCalculated(0.0)
        onStdDevCalculated(0.0)
    }
}

fun Double.pow(exp: Int): Double = this.pow(exp.toDouble())

fun safeSubstring(input: String, beginIndex: Int, endIndex: Int): String {
    return if (input.length >= endIndex && beginIndex < endIndex) {
        input.substring(beginIndex, endIndex)
    } else {
        input
    }
}