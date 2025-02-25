package com.example.app.ui.web

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.app.room.Record

object WebData{
    val niuhao =  ""
    var username = ""
    var access_token = ""
    var refresh_token = ""
}
class FormViewModel : ViewModel() {
    // 保存名字和年龄的状态
    var name by mutableStateOf("")
    var age by mutableStateOf("")
}
class HomeViewModel : ViewModel() {
    var navController: NavHostController? = null
}


class RecordFilterViewModel :ViewModel(){
    var cowId by  mutableStateOf(TextFieldValue(""))
    var title by mutableStateOf(TextFieldValue(""))

    // 日期选择器的状态
    var startDate by mutableStateOf<Long?>(null)
    var endDate by  mutableStateOf<Long?>(null)
}


object filter{
    var cowId = ""
    var title = ""
    var startDate  = Long.MIN_VALUE
    var endDate = Long.MAX_VALUE
}

class HistoryViewModel : ViewModel() {
    var deleteMode by mutableStateOf(false)
    var id by mutableStateOf(Long.MIN_VALUE)
    var cowid by mutableStateOf("未命名")
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var value by mutableStateOf("")
    var filteredRecords by mutableStateOf<List<Record>>(emptyList())
}


object details_data{
    var cowid by  mutableStateOf("")
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var value by mutableStateOf("")
    var date by mutableStateOf("")
    var id by mutableStateOf(Long.MIN_VALUE)
}