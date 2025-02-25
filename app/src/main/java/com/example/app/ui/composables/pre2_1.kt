package com.example.app.ui.composables

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app.data.model.CowHealthRequest2
import com.example.app.room.MainViewModel
import com.example.app.ui.viewmodel.CowHealthViewModel2
import com.example.app.ui.web.WebData

@Composable
fun Pre2_1(viewModel: CowHealthViewModel2 = viewModel(), dbView: MainViewModel = viewModel()) {
    val context = LocalContext.current
    val predictionResult by viewModel.predictionResult.observeAsState()
    val isLoading by viewModel.loading.observeAsState(false)
    var cowBreedCode by remember { mutableStateOf("1") }
    var calvingYear by remember { mutableStateOf("2021") }
    var calvingSeasons by remember { mutableStateOf("") }
    var parityClass by remember { mutableStateOf("") }
    var dryPeriodLengthClass by remember { mutableStateOf("") }
    var pregnancyLengthClass by remember { mutableStateOf("") }
    var bodyConditionScorePa by remember { mutableStateOf("3.5") }
    var ageAftFirstCalvingDayClass by remember { mutableStateOf("") }
    var twiningStatus by remember { mutableStateOf("") }
    var calfSex by remember { mutableStateOf("") }
    var calfBirthWeightClass by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // 使页面可滑动
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("奶牛子宫炎预测", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))
        var expanded by remember { mutableStateOf(false) } // 控制菜单的展开状态
        var selectedOption by remember { mutableStateOf("选择选项") } // 保存选择的选项
        // 输入表单
        // 奶牛品种代码
        OutlinedTextField(
            value = cowBreedCode,
            onValueChange = { cowBreedCode = it },
            label = { Text("奶牛品种代码") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
            modifier = Modifier.fillMaxWidth(),
        )

        // 产犊年份
        OutlinedTextField(
            value = calvingYear,
            onValueChange = { calvingYear = it },
            label = { Text("产犊年份") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // 产犊季节
        DropdownField(
            label = "产犊季节",
            value = calvingSeasons,
            options = listOf("1: 春季", "2: 夏季", "3: 秋季", "4: 冬季"),
            onValueChange = { calvingSeasons = it }
        )

        // 产次类别
        DropdownField(
            label = "产次类别",
            value = parityClass,
            options = listOf("0: 第一胎次", "1: 第二胎次", "2: 第三胎次", "3: 第四胎次", "4: 第五胎次及以上"),
            onValueChange = { parityClass = it }
        )

        // 干奶期长类别
        DropdownField(
            label = "干奶期长类别",
            value = dryPeriodLengthClass,
            options = listOf("0: 0天", "1: 1-50天", "2: 51-65天", "3: 66-100天", "4: >=101天"),
            onValueChange = { dryPeriodLengthClass = it }
        )

        // 妊娠期长类别
        DropdownField(
            label = "妊娠期长类别",
            value = pregnancyLengthClass,
            options = listOf("1: 0-270天", "2: 271-275天", "3: >=276天"),
            onValueChange = { pregnancyLengthClass = it }
        )

        // 体况评分
        OutlinedTextField(
            value = bodyConditionScorePa,
            onValueChange = { bodyConditionScorePa = it },
            label = { Text("体况评分") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // 产后首次发情日龄类别
        DropdownField(
            label = "产后首次发情日龄类别",
            value = ageAftFirstCalvingDayClass,
            options = listOf("1: 0-686天", "2: 687-716天", "3: 717-747天", "4: >=748天"),
            onValueChange = { ageAftFirstCalvingDayClass = it }
        )

        // 双胞胎状态
        DropdownField(
            label = "双胞胎状态",
            value = twiningStatus,
            options = listOf("1: 单胎", "2: 双胞胎"),
            onValueChange = { twiningStatus = it }
        )

        // 犊牛性别
        DropdownField(
            label = "犊牛性别",
            value = calfSex,
            options = listOf("1: 公", "2: 母"),
            onValueChange = { calfSex = it }
        )

        // 犊牛出生体重类别
        DropdownField(
            label = "犊牛出生体重类别",
            value = calfBirthWeightClass,
            options = listOf(
                "1: 15-35KG", "2: 36-38KG", "3: 39-40KG", "4: 41-42KG",
                "5: 43-44KG", "6: 45-46KG", "7: >=47KG"
            ),
            onValueChange = { calfBirthWeightClass = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 提交按钮
        Button(
            onClick = {
                var ok = true
                val parsedRequest = try {

                    if (cowBreedCode.isEmpty()) {
                        throw IllegalArgumentException("奶牛品种代码不能为空")
                    }
                    if (calvingYear.isEmpty()) {
                        throw IllegalArgumentException("分娩年份不能为空")
                    }
                    if (calvingSeasons.isEmpty()) {
                        throw IllegalArgumentException("分娩季节不能为空")
                    }
                    if (parityClass.isEmpty()) {
                        throw IllegalArgumentException("胎次类别不能为空")
                    }
                    if (dryPeriodLengthClass.isEmpty()) {
                        throw IllegalArgumentException("干奶期长度类别不能为空")
                    }
                    if (pregnancyLengthClass.isEmpty()) {
                        throw IllegalArgumentException("怀孕长度类别不能为空")
                    }
                    if (bodyConditionScorePa.isEmpty()) {
                        throw IllegalArgumentException("体况评分不能为空")
                    }
                    if (ageAftFirstCalvingDayClass.isEmpty()) {
                        throw IllegalArgumentException("初次分娩后年龄类别不能为空")
                    }
                    if (twiningStatus.isEmpty()) {
                        throw IllegalArgumentException("双胞胎状态不能为空")
                    }
                    if (calfSex.isEmpty()) {
                        throw IllegalArgumentException("小牛性别不能为空")
                    }
                    if (calfBirthWeightClass.isEmpty()) {
                        throw IllegalArgumentException("小牛出生体重类别不能为空")
                    }
                    CowHealthRequest2(
                        cow_breed_code = cowBreedCode.toInt(),                // 奶牛品种代码
                        calving_year = calvingYear.toInt(),                  // 分娩年份
                        calving_seasons = calvingSeasons.substring(0,1).toInt(),           // 分娩季节
                        parity_class = parityClass.substring(0,1).toInt(),                  // 胎次类别
                        dry_period_length_class = dryPeriodLengthClass.substring(0,1).toInt(), // 干奶期长度类别
                        pregnancy_length_class = pregnancyLengthClass.substring(0,1).toInt(),   // 怀孕长度类别
                        body_condition_score_pa = bodyConditionScorePa.toFloat(), // 体况评分
                        age_aft_first_calving_day_class = ageAftFirstCalvingDayClass.substring(0,1).toInt(), // 初次分娩后年龄类别
                        twining_status = twiningStatus.substring(0,1).toInt(),              // 双胞胎状态
                        calf_sex = calfSex.substring(0,1).toInt(),                          // 小牛性别
                        calf_birth_weight_class = calfBirthWeightClass.substring(0,1).toInt() // 小牛出生体重类别
                    )
                } catch (e: Exception) {
                    Toast.makeText(context, "请输入有效的数字", Toast.LENGTH_SHORT).show()
                    return@Button

                }
                viewModel.fetchCowHealthPrediction(parsedRequest) // 调用 ViewModel 方法获取预测结果


            },
            modifier = Modifier
        ) {
            Text("提交预测") // 按钮文本
        }

        Spacer(modifier = Modifier.height(16.dp)) // 添加间距
        if(isLoading){
//            CircularProgressIndicator() // 显示加载进度条
            CustomCircularProgressIndicator()
            Spacer(modifier = Modifier.height(7.dp))
            Text(text = "正在等待结果...")
        }

        if (predictionResult != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                var s = predictionResult?.stacking
                s = s?.times(100)
                var s1 = s.toString()
                if (s1.length> 6){
                    s1.substring(0,6)
                }
                Text(text = "预测结果", style = MaterialTheme.typography.titleMedium)
                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
                Text("该牛患病的概率是$s1%", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

//                // 表格的列名
//                Row(modifier = Modifier.fillMaxWidth()) {
//                    Text(
//                        text = "模型",
//                        style = MaterialTheme.typography.bodyMedium,
//                        modifier = Modifier.weight(1f)
//                    )
//                    Text(
//                        text = "结果",
//                        style = MaterialTheme.typography.bodyMedium,
//                        modifier = Modifier.weight(1f)
//                    )
//                }

//                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
//
//                TableRow("患病概率", predictionResult?.stacking)
            }
        } else {
            Spacer(modifier = Modifier.height(176.dp))
        }




        if (predictionResult != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                // 表头
                Text("奶牛子宫炎预测表单：", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                // 输入数据的列名
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "输入数据",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "值",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                }

                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

                // 表格的每一行，显示输入的数据
                TableRow("奶牛品种代码", cowBreedCode)
                TableRow("分娩年份", calvingYear)
                TableRow("分娩季节", calvingSeasons)
                TableRow("胎次类别", parityClass)
                TableRow("干奶期长度类别", dryPeriodLengthClass)
                TableRow("怀孕长度类别", pregnancyLengthClass)
                TableRow("体况评分", bodyConditionScorePa)
                TableRow("初次分娩后年龄类别", ageAftFirstCalvingDayClass)
                TableRow("双胞胎状态", twiningStatus)
                TableRow("小牛性别", calfSex)
                TableRow("小牛出生体重类别", calfBirthWeightClass)

                Spacer(modifier = Modifier.height(16.dp))  // 输入数据与预测结果之间的间距

                // 预测结果的列名
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "模型",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "预测结果",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                }

                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

                // 表格的每一行，显示模型名称和对应的预测结果
                TableRow("堆叠模型", predictionResult?.stacking)

                val description = """
                        奶牛品种代码: $cowBreedCode
                        分娩年份: $calvingYear
                        分娩季节: $calvingSeasons
                        胎次类别: $parityClass
                        干奶期长度类别: $dryPeriodLengthClass
                        怀孕长度类别: $pregnancyLengthClass
                        体况评分: $bodyConditionScorePa
                        初次分娩后年龄类别: $ageAftFirstCalvingDayClass
                        双胞胎状态: $twiningStatus
                        小牛性别: $calfSex
                        小牛出生体重类别: $calfBirthWeightClass
                    """.trimIndent()

                // 获取当前时间戳
                val timestamp = System.currentTimeMillis()


                dbView.addRecord(
                    username = WebData.username,
                    cowid = WebData.niuhao,
                    title = "奶牛子宫炎预测",
                    description = description,
                    value = predictionResult?.stacking.toString(),
                    timestamp = timestamp
                )

            }
        } else {
            Spacer(modifier = Modifier.height(176.dp))
        }

        Spacer(modifier = Modifier.height(176.dp))

        // 表格行的辅助函数
        @Composable
        fun TableRow(label: String, value: String?) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = value ?: "无数据",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )
            }
        }


        Spacer(modifier = Modifier.height(176.dp))
    }
}



@Composable
fun DropdownField(
    label: String,
    value: String,
    options: List<String>,
    onValueChange: (String) -> Unit
) {
    var expanded1 by remember { mutableStateOf(false) }

    // 使用 Box 包裹 OutlinedTextField，使整个区域可点击
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded1 = true } // 点击整个框展开下拉菜单
    ) {
        OutlinedTextField(
            enabled = false,
            value = value,
            onValueChange = { /* 不做任何操作，输入框只通过选择下拉菜单更新值 */ },
            label = { Text(label) },
            readOnly = true, // 设置为只读，使用户无法手动输入
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = Color.Black,
                unfocusedBorderColor = Color.DarkGray,
                disabledBorderColor = Color.DarkGray,
                disabledLabelColor = Color.DarkGray,
            )
        )

        // DropdownMenu 的实现
        DropdownMenu(
            expanded = expanded1,
            onDismissRequest = { expanded1 = false }, // 点击外部时关闭菜单
            modifier = Modifier.fillMaxWidth(0.7F) // 下拉菜单宽度设置为填满
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(option) // 显示选项文本
                    },
                    onClick = {
                        onValueChange(option) // 更新选项
                        expanded1 = false // 选择后关闭菜单
                    }
                )
            }
        }
    }
}
