package com.example.water13.rank

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.water13.source.RankResponse
import com.example.water13.source.Repo
import kotlinx.coroutines.*

class RankViewModel : ViewModel() {

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val message = MutableLiveData<String>()

    init {
        getRankList()
    }

    fun onMsgShowed() {
        message.value = ""
    }

    var rankList = MutableLiveData<List<RankResponse>>()

    fun getRankList() {
        uiScope.launch {
            try {
                rankList.value = listOf()
                rankList.value = Repo.getRank()
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