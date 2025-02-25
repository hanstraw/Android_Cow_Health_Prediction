package com.example.app.room

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = AppDatabase.getDatabase(application).userDao()
    private val recordDao = AppDatabase.getDatabase(application).recordDao()

    // 添加用户
    fun addUser(name: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = User(name = name, email = email)
            userDao.insertUser(user)
        }
    }

    // 获取所有用户
    fun getAllUsers(onResult: (List<User>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val users = userDao.getAllUsers()
            withContext(Dispatchers.Main) {
                onResult(users)
            }
        }
    }

    // 添加记录
    fun addRecord(username: String, cowid: String,title: String, description: String, value: String, timestamp: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val record = Record(username = username,cowid = cowid,title=title ,description = description, value = value, timestamp = timestamp)
            recordDao.insertRecord(record)
        }
    }

//    // 获取所有记录
//    fun getRecordsByUserId(username: String, onResult: (List<Record>) -> Unit) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val records = recordDao.getRecordsByUserId(username = username)
//            withContext(Dispatchers.Main) {
//                onResult(records)
//            }
//        }
//    }

    // 返回 Flow，实时更新记录
    fun getRecordsByUserId(username: String): Flow<List<Record>> {
        return recordDao.getRecordsByUserId(username)
    }

    fun deleteRecord(record: Record) {
        viewModelScope.launch {
            recordDao.deleteRecord(record)
        }
    }

    fun deleteRecordById(recordId: Long) {
        viewModelScope.launch {
            recordDao.deleteRecordById(recordId)
        }
    }
}