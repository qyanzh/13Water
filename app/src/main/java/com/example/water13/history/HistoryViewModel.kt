package com.example.water13.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.water13.bean.User
import com.example.water13.source.HistoryListResponse
import com.example.water13.source.Repo
import kotlinx.coroutines.*

class HistoryViewModel(var gamerId: Int) : ViewModel() {

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
        if(gamerId==0) gamerId = User.instance.id
        if (historyList.value.isNullOrEmpty()) {
            getHistoryList(true)
        }
    }

    fun getHistoryList(restart: Boolean = false) {
        uiScope.launch {
            try {
                isRefreshing.value = true
                if (restart) {
                    page = 0
                    historyList.value =
                        (Repo.getHistoryList(gamerId, User.instance.token, 20, 0)).toMutableList()
                } else {
                    val list = historyList.value?.plus(
                        Repo.getHistoryList(
                            gamerId,
                            User.instance.token,
                            20,
                            page
                        )
                    )
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

    class Factory(val gamerId:Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HistoryViewModel(gamerId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}