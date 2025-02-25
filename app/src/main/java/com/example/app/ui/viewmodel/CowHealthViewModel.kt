package com.example.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.app.data.model.CowHealthRequest
import com.example.app.data.model.CowHealthRequest2
import com.example.app.data.model.CowHealthRequest3
import com.example.app.data.model.PredictionResponse
import com.example.app.data.model.PredictionResponse2
import com.example.app.data.model.PredictionResponse3
import com.example.app.network.NetworkModule
import com.example.app.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class CowHealthViewModel : ViewModel() {

    private val _predictionResult = MutableLiveData<PredictionResponse?>()
    val predictionResult: LiveData<PredictionResponse?> = _predictionResult

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun fetchCowHealthPrediction(request: CowHealthRequest) {
        viewModelScope.launch {
            _loading.postValue(true)  // 开始加载

            val call = RetrofitClient.apiService.getCowHealthPrediction(request)
            call.enqueue(object : Callback<PredictionResponse> {
                override fun onResponse(call: Call<PredictionResponse>, response: Response<PredictionResponse>) {
                    _loading.postValue(false)  // 停止加载
                    if (response.isSuccessful) {
                        _predictionResult.postValue(response.body())
                    } else {
                        _predictionResult.postValue(null)
                    }
                }

                override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                    _loading.postValue(false)  // 停止加载
                    _predictionResult.postValue(null)
                }
            })
        }
    }
}


class CowHealthViewModel2 : ViewModel() {

    private val _predictionResult = MutableLiveData<PredictionResponse2?>()
    val predictionResult: LiveData<PredictionResponse2?> = _predictionResult

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun fetchCowHealthPrediction(request: CowHealthRequest2) {
        viewModelScope.launch {
            _loading.postValue(true)  // 开始加载

            val call = RetrofitClient.apiService.getCowHealthPrediction2(request)
            call.enqueue(object : Callback<PredictionResponse2> {
                override fun onResponse(call: Call<PredictionResponse2>, response: Response<PredictionResponse2>) {
                    _loading.postValue(false)  // 停止加载
                    if (response.isSuccessful) {
                        _predictionResult.postValue(response.body())
                    } else {
                        _predictionResult.postValue(null)
                    }
                }

                override fun onFailure(call: Call<PredictionResponse2>, t: Throwable) {
                    _loading.postValue(false)  // 停止加载
                    _predictionResult.postValue(null)
                }
            })
        }
    }
}

class CowHealthViewModel3 : ViewModel() {

    private val _predictionResult = MutableLiveData<PredictionResponse3?>()
    val predictionResult: LiveData<PredictionResponse3?> = _predictionResult

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun fetchCowHealthPrediction(request: CowHealthRequest3) {
        viewModelScope.launch {
            _loading.postValue(true)  // 开始加载

            val call = RetrofitClient.apiService.getCowHealthPrediction3(request)
            call.enqueue(object : Callback<PredictionResponse3> {
                override fun onResponse(call: Call<PredictionResponse3>, response: Response<PredictionResponse3>) {
                    _loading.postValue(false)  // 停止加载
                    if (response.isSuccessful) {
                        _predictionResult.postValue(response.body())
                    } else {
                        _predictionResult.postValue(null)
                    }
                }

                override fun onFailure(call: Call<PredictionResponse3>, t: Throwable) {
                    _loading.postValue(false)  // 停止加载
                    _predictionResult.postValue(null)
                }
            })
        }
    }
}

