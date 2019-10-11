package com.example.water13.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.water13.bean.User
import com.example.water13.source.HistoryListResponse
import com.example.water13.source.Repo
import kotlinx.coroutines.*

class HistoryViewModel : ViewModel() {

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val isRefreshing = MutableLiveData<Boolean>(false)

    val message = MutableLiveData<String>()

    fun onMsgShowed() {
        message.value = ""
    }

    val historyList = MutableLiveData<MutableList<HistoryListResponse.Data>>(mutableListOf())

    var page = 0

    init {
        if (historyList.value.isNullOrEmpty()) {
            getHistoryList(true)
        }
    }

    fun getHistoryList(restart:Boolean = false) {
        uiScope.launch {
            try {
                isRefreshing.value = true
                if(restart) {
                    page = 0
                    historyList.value = (Repo.getHistoryList(User.instance, 10, 0)).toMutableList()
                } else {
                    val list = historyList.value?.plus(Repo.getHistoryList(User.instance,10,page))
                    historyList.value = list?.toMutableList()
                }
                page++
            } catch (e: Exception) {
                message.value = e.message
            } finally {
                delay(800)
                isRefreshing.value = false
            }
        }
    }



    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}