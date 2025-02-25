package com.example.app.pytorch
//
//import android.content.Context
//import org.pytorch.IValue
//import org.pytorch.Module
//import org.pytorch.Tensor
//
//fun loadModel(context: Context): Module {
//    // 加载 PyTorch 模型
//    return Module.load(assetFilePath(context, "cow_health_model.pt"))
//}
//
//fun runModelPrediction(context: Context, inputs: FloatArray): String {
//    // 1. 加载模型
//    val module: Module = loadModel(context)
//
//    // 2. 标准化数据
//    val means = floatArrayOf(
//        3.1897619F, 79.83974F, 41.55645F, 2.4332438F, 93.58552F,
//        2.695049F, 646.142F, 66.11444F
//    )  // 替换为提取的均值
//    val scales = floatArrayOf(
//        1.2894292F, 41.834927F, 10.740368F, 2.017099F, 6.5144963F,
//        2.0953205F, 126.76042F, 38.759575F, )   // 替换为提取的标准差
//    val standardizedInput = standardize(inputs, means, scales)
//
//    // 3. 转换为 Tensor
//    val inputTensor = Tensor.fromBlob(standardizedInput, longArrayOf(1, standardizedInput.size.toLong()))
//
//    // 4. 运行模型并获得预测结果
//    val output = module.forward(IValue.from(inputTensor)).toTensor()
//    val outputData = output.dataAsFloatArray
//
//    // 返回预测结果
//    return if (outputData[0] > 0.5) "健康" else "患病"
////    return outputData[0].toString()
//}
//
//// 用于获取 assets 文件路径
//fun assetFilePath(context: Context, assetName: String): String {
//    val file = java.io.File(context.filesDir, assetName)
//    context.assets.open(assetName).use { inputStream ->
//        java.io.FileOutputStream(file).use { outputStream ->
//            val buffer = ByteArray(1024)
//            var length: Int
//            while (inputStream.read(buffer).also { length = it } != -1) {
//                outputStream.write(buffer, 0, length)
//            }
//            outputStream.flush()
//        }
//    }
//    return file.absolutePath
//}
//
//
//fun standardize(input: FloatArray, means: FloatArray, scales: FloatArray): FloatArray {
//    val standardized = FloatArray(input.size)
//    for (i in input.indices) {
//        standardized[i] = (input[i] - means[i]) / scales[i]
//    }
//    return standardized
//}
