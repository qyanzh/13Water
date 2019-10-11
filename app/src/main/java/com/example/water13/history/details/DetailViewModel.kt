package com.example.water13.history.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.water13.bean.User
import com.example.water13.source.HistoryDetailResponse.Detail
import com.example.water13.source.Repo
import kotlinx.coroutines.*

class DetailViewModel() : ViewModel() {

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val message = MutableLiveData<String>()

    fun onMsgShowed() {
        message.value =""
    }

    var detailsList = MutableLiveData<List<Detail>>()

    fun getHistoryList(id: Int) {
        uiScope.launch {
            try {
                detailsList.value = listOf()
                detailsList.value = Repo.getHistoryDetail(User.instance, id).data.detail
            } catch (e: Exception) {
                message.value = e.message
            } finally {
                delay(800)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}