package com.example.app.ui.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.app.data.model.CowHealthRequest
import com.example.app.ui.viewmodel.CowHealthViewModel


@Composable
fun Pre2(viewModel: CowHealthViewModel = viewModel()) {
    val predictionResult by viewModel.predictionResult.observeAsState()
    val isLoading by viewModel.loading.observeAsState(false)
    var cowBreedCode by remember { mutableStateOf("1") }
    var calvingYear by remember { mutableStateOf("2021") }
    var calvingSeasons by remember { mutableStateOf("2") }
    var parityClass by remember { mutableStateOf("3") }
    var dryPeriodLengthClass by remember { mutableStateOf("1") }
    var pregnancyLengthClass by remember { mutableStateOf("2") }
    var bodyConditionScorePa by remember { mutableStateOf("3.5") }
    var ageAftFirstCalvingDayClass by remember { mutableStateOf("4") }
    var twiningStatus by remember { mutableStateOf("0") }
    var calfSex by remember { mutableStateOf("1") }
    var calfBirthWeightClass by remember { mutableStateOf("3") }

    // 使 Column 可滑动
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // 使页面可滑动
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("奶牛子宫炎预测", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // 输入表单
        OutlinedTextField(
            value = cowBreedCode,
            onValueChange = { cowBreedCode = it },
            label = { Text("奶牛品种代码") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number, // 设置数字键盘
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = calvingYear,
            onValueChange = { calvingYear = it },
            label = { Text("分娩年份") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = calvingSeasons,
            onValueChange = { calvingSeasons = it },
            label = { Text("分娩季节") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = parityClass,
            onValueChange = { parityClass = it },
            label = { Text("胎次类别") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = dryPeriodLengthClass,
            onValueChange = { dryPeriodLengthClass = it },
            label = { Text("干奶期长度类别") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = pregnancyLengthClass,
            onValueChange = { pregnancyLengthClass = it },
            label = { Text("怀孕长度类别") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = bodyConditionScorePa,
            onValueChange = { bodyConditionScorePa = it },
            label = { Text("体况评分") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = ageAftFirstCalvingDayClass,
            onValueChange = { ageAftFirstCalvingDayClass = it },
            label = { Text("初次分娩后年龄类别") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = twiningStatus,
            onValueChange = { twiningStatus = it },
            label = { Text("双胞胎状态") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = calfSex,
            onValueChange = { calfSex = it },
            label = { Text("小牛性别") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = calfBirthWeightClass,
            onValueChange = { calfBirthWeightClass = it },
            label = { Text("小牛出生体重类别") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 提交按钮
        Button(
            onClick = {
                // 解析输入字段
                val parsedRequest = try {
                    CowHealthRequest(
                        cow_breed_code = cowBreedCode.toInt(),                // 奶牛品种代码
                        calving_year = calvingYear.toInt(),                  // 分娩年份
                        calving_seasons = calvingSeasons.toInt(),           // 分娩季节
                        parity_class = parityClass.toInt(),                  // 胎次类别
                        dry_period_length_class = dryPeriodLengthClass.toInt(), // 干奶期长度类别
                        pregnancy_length_class = pregnancyLengthClass.toInt(),   // 怀孕长度类别
                        body_condition_score_pa = bodyConditionScorePa.toFloat(), // 体况评分
                        age_aft_first_calving_day_class = ageAftFirstCalvingDayClass.toInt(), // 初次分娩后年龄类别
                        twining_status = twiningStatus.toInt(),              // 双胞胎状态
                        calf_sex = calfSex.toInt(),                          // 小牛性别
                        calf_birth_weight_class = calfBirthWeightClass.toInt() // 小牛出生体重类别
                    )
                } catch (e: NumberFormatException) {
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

        if (predictionResult != null){

        }


        if (predictionResult != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Text("预测结果：", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                // 表格的列名
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "模型",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "结果",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                }

                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

                // 表格的每一行，显示模型名称和对应的预测结果
                TableRow("逻辑回归", predictionResult?.logistic_regression)
                TableRow("决策树", predictionResult?.decision_tree)
                TableRow("随机森林", predictionResult?.random_forest)
                TableRow("XGBoost", predictionResult?.xgboost)
                TableRow("LightGBM", predictionResult?.lightgbm)
                TableRow("堆叠模型", predictionResult?.stacking)
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
                TableRow("逻辑回归", predictionResult?.logistic_regression)
                TableRow("决策树", predictionResult?.decision_tree)
                TableRow("随机森林", predictionResult?.random_forest)
                TableRow("XGBoost", predictionResult?.xgboost)
                TableRow("LightGBM", predictionResult?.lightgbm)
                TableRow("堆叠模型", predictionResult?.stacking)
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
fun TableRow(modelName: String, result: Any?) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = modelName,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = result?.toString() ?: "无结果",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
    }
    Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(vertical = 4.dp))
}


@Composable
fun CustomCircularProgressIndicator(
    modifier: Modifier = Modifier,
    size: Dp = 35.dp,
    strokeWidth: Dp = 7.dp,
    colors: List<Color> = listOf(Color(0xFF71AA64), Color(0xFF90C784), Color(0xFF71AA64))
) {
    // 渐变效果
    val infiniteTransition = rememberInfiniteTransition()
    val animatedRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 436f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Canvas(modifier = modifier.size(size)) {
        // 定义渐变的画笔
        val brush = Brush.sweepGradient(colors)

        // 画圆环
        drawArc(
            brush = brush,
            startAngle = animatedRotation,
            sweepAngle = 270f,
            useCenter = false,
            style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
        )
    }
}
