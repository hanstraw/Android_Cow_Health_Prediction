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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.example.app.ui.web.WebData.username
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.example.app.room.Record

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DropdownMenu() {
    var a = stringResource(id = com.example.app.R.string.all)
    var expanded1 by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(a) }
    val options = listOf(stringResource(id = com.example.app.R.string.all), stringResource(id = com.example.app.R.string.rufang), stringResource(id = com.example.app.R.string.zigong))

    // ExposedDropdownMenuBox 是一个用于组合TextField和DropdownMenu的容器
    ExposedDropdownMenuBox(
        expanded = expanded1,
        onExpandedChange = { expanded1 = !expanded1 } // 当点击时展开或收起下拉菜单
    ) {
        // TextField 显示已选择的选项
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {

            },
            readOnly = true, // 禁止手动输入，使用下拉选择
            label = { Text(stringResource(id = com.example.app.R.string.type)) },
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
                        expanded1 = false // 关闭菜单
                    }
                )
            }
        }
    }
}