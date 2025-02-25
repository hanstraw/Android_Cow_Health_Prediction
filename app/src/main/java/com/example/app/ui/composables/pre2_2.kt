package com.example.app.ui.composables

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.app.R
import com.example.app.data.model.CowHealthRequest3
import com.example.app.room.MainViewModel
import com.example.app.ui.viewmodel.CowHealthViewModel3
import com.example.app.ui.web.WebData
import java.util.Calendar
import java.util.TimeZone

@Composable
fun Pre2_2(viewModel: CowHealthViewModel3 = viewModel(), dbView: MainViewModel = viewModel()) {
    val context = LocalContext.current
    val predictionResult by viewModel.predictionResult.observeAsState()
    val isLoading by viewModel.loading.observeAsState(false)
    var cowBreedCode by remember { mutableStateOf("1") }
    var calvingSeasons by remember { mutableStateOf("") }
    var parityClass by remember { mutableStateOf("") }
    var dryPeriodLengthClass by remember { mutableStateOf("") }
    var pregnancyLengthClass by remember { mutableStateOf("") }
    var bodyConditionScorePa by remember { mutableStateOf("3.5") }
    var ageAftFirstCalvingDayClass by remember { mutableStateOf("") }
    var twiningStatus by remember { mutableStateOf("") }
    var calfSex by remember { mutableStateOf("") }
    var calfBirthWeightClass by remember { mutableStateOf("") }
    var cowid by remember { mutableStateOf("") }
    var s1 by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }
    var need_up by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // 使页面可滑动
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(id = R.string.zigong), style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))
        var expanded by remember { mutableStateOf(false) } // 控制菜单的展开状态
        var selectedOption by remember { mutableStateOf("选择选项") } // 保存选择的选项
        // 输入表单
        // 牛号输入框
        OutlinedTextField(
            value = cowid,
            onValueChange = { cowid = it },
            label = { Text(stringResource(R.string.cow_id_label)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // 奶牛品种代码
        OutlinedTextField(
            value = cowBreedCode,
            onValueChange = { cowBreedCode = it },
            label = { Text(stringResource(R.string.cow_breed_code_label)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // 下拉选择框
        DropdownField(
            label = stringResource(R.string.calving_seasons_label),
            value = calvingSeasons,
            options = listOf(
                stringResource(R.string.p2o1_1),
                stringResource(R.string.p2o1_2),
                stringResource(R.string.p2o1_3),
                stringResource(R.string.p2o1_4)
            ),
            onValueChange = { calvingSeasons = it }
        )

        DropdownField(
            label = stringResource(R.string.parity_class_label),
            value = parityClass,
            options = listOf(
                stringResource(R.string.p2o2_0),
                stringResource(R.string.p2o2_1),
                stringResource(R.string.p2o2_2),
                stringResource(R.string.p2o2_3),
                stringResource(R.string.p2o2_4)
            ),
            onValueChange = { parityClass = it }
        )

        DropdownField(
            label = stringResource(R.string.dry_period_length_class_label),
            value = dryPeriodLengthClass,
            options = listOf(
                stringResource(R.string.p2o3_0),
                stringResource(R.string.p2o3_1),
                stringResource(R.string.p2o3_2),
                stringResource(R.string.p2o3_3),
                stringResource(R.string.p2o3_4)
            ),
            onValueChange = { dryPeriodLengthClass = it }
        )

        DropdownField(
            label = stringResource(R.string.pregnancy_length_class_label),
            value = pregnancyLengthClass,
            options = listOf(
                stringResource(R.string.p2o4_1),
                stringResource(R.string.p2o4_2),
                stringResource(R.string.p2o4_3)
            ),
            onValueChange = { pregnancyLengthClass = it }
        )

        OutlinedTextField(
            value = bodyConditionScorePa,
            onValueChange = { bodyConditionScorePa = it },
            label = { Text(stringResource(R.string.body_condition_score_label)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        DropdownField(
            label = stringResource(R.string.age_aft_first_calving_day_class_label),
            value = ageAftFirstCalvingDayClass,
            options = listOf(
                stringResource(R.string.p2o6_1),
                stringResource(R.string.p2o6_2),
                stringResource(R.string.p2o6_3),
                stringResource(R.string.p2o6_4)
            ),
            onValueChange = { ageAftFirstCalvingDayClass = it }
        )

        DropdownField(
            label = stringResource(R.string.twining_status_label),
            value = twiningStatus,
            options = listOf(
                stringResource(R.string.p2o7_1),
                stringResource(R.string.p2o7_2)
            ),
            onValueChange = { twiningStatus = it }
        )

        DropdownField(
            label = stringResource(R.string.calf_sex_label),
            value = calfSex,
            options = listOf(
                stringResource(R.string.p2o8_1),
                stringResource(R.string.p2o8_2)
            ),
            onValueChange = { calfSex = it }
        )


        // 犊牛出生体重类别
        DropdownField(
            label = stringResource(R.string.calf_birth_weight_class_label),
            value = calfBirthWeightClass,
            options = listOf(
                "1: 15-35KG", "2: 36-38KG", "3: 39-40KG", "4: 41-42KG",
                "5: 43-44KG", "6: 45-46KG", "7: >=47KG"
            ),
            onValueChange = { calfBirthWeightClass = it }
        )
        val a = stringResource(R.string.error_fill_all_fields)
        Spacer(modifier = Modifier.height(16.dp))
        if(!isLoading){
            // 提交按钮
            Button(
                onClick = {
                    need_up = true
                    var ok = true
                    val parsedRequest = try {

                        if (cowBreedCode.isEmpty()) {
                            throw IllegalArgumentException("奶牛品种代码不能为空")
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
                        CowHealthRequest3(
                            user_name = WebData.username,
                            cow_id = cowid,                                // 奶牛ID
                            cow_breed_code = cowBreedCode.toInt(),                // 奶牛品种代码
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

                        Toast.makeText(context,a , Toast.LENGTH_SHORT).show()
                        return@Button

                    }
                    viewModel.fetchCowHealthPrediction(parsedRequest) // 调用 ViewModel 方法获取预测结果


                },
                modifier = Modifier
            ) {
                Text(stringResource(R.string.submit_prediction)) // 按钮文本
            }
        }


        Spacer(modifier = Modifier.height(16.dp)) // 添加间距
        if(isLoading){
//            CircularProgressIndicator() // 显示加载进度条
            CustomCircularProgressIndicator()
            Spacer(modifier = Modifier.height(7.dp))
            Text(text = stringResource(R.string.waiting_result))
        }

        if (predictionResult != null) {
            val s = predictionResult?.xgboost

            if (s != null) {
                s1 = when {
                    s > 0.5f -> stringResource(R.string.high_probability)
                    s >= 0.3f -> stringResource(R.string.possible_probability)
                    else -> stringResource(R.string.low_probability)
                }
            }

            // 使用 Card 组件
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp), // 圆角矩形
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // 增加阴影
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.result),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Divider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Text(
                        text = stringResource(R.string.this_cow)+ s1,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        } else {
            Spacer(modifier = Modifier.height(176.dp))
        }

        val s01 = stringResource(R.string.cow_breed_code_label)
        val s2 = stringResource(R.string.calving_seasons_label)
        val s3 = stringResource(R.string.parity_class_label)
        val s4 = stringResource(R.string.dry_period_length_class_label)
        val s5 = stringResource(R.string.pregnancy_length_class_label)
        val s6 = stringResource(R.string.body_condition_score_label)
        val s7 = stringResource(R.string.age_aft_first_calving_day_class_label)
        val s8 = stringResource(R.string.twining_status_label)
        val s9 = stringResource(R.string.calf_sex_label)
        val s10 = stringResource(R.string.calf_birth_weight_class_label)


        if (predictionResult != null && need_up){
            need_up = false
            val description = """
                        ${s01}: $cowBreedCode
                        ${s2}: $calvingSeasons
                        ${s3}: $parityClass
                        ${s4}: $dryPeriodLengthClass
                        ${s5}: $pregnancyLengthClass
                        ${s6}: $bodyConditionScorePa
                        ${s7}: $ageAftFirstCalvingDayClass
                        ${s8}: $twiningStatus
                        ${s9}: $calfSex
                        ${s10}: $calfBirthWeightClass
                    """.trimIndent()

            val calendar = Calendar.getInstance().apply {
                timeZone = TimeZone.getTimeZone("Asia/Shanghai")
            }
            val timestamp = calendar.timeInMillis


            dbView.addRecord(
                username = WebData.username,
                cowid = cowid,
                title = stringResource(R.string.zigong),
                description = description,
                value = s1,
                timestamp = timestamp
            )

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
                Text(stringResource(R.string.zigong_form)+"：", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                // 输入数据的列名
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(R.string.input),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = stringResource(R.string.value),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                }

                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

                // 表格的每一行，显示输入的数据
                TableRow(s01, cowBreedCode)
                TableRow(s2, calvingSeasons)
                TableRow(s3, parityClass)
                TableRow(s4, dryPeriodLengthClass)
                TableRow(s5, pregnancyLengthClass)
                TableRow(s6, bodyConditionScorePa)
                TableRow(s7, ageAftFirstCalvingDayClass)
                TableRow(s8, twiningStatus)
                TableRow(s9, calfSex)
                TableRow(s10, calfBirthWeightClass)

                Spacer(modifier = Modifier.height(16.dp))  // 输入数据与预测结果之间的间距

                // 预测结果的列名
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(R.string.result),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                }

                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
                var k =(predictionResult?.xgboost?.times(100)).toString()

                if(k.length>4) {
                    k = k.substring(0, 4)
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(

                        text = stringResource(R.string.the_cow)+" $k%",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                }



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
                    text = value ?: stringResource(id = R.string.No_data),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )
            }
        }


        Spacer(modifier = Modifier.height(176.dp))
    }
}




