package com.example.water13.game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.water13.bean.User
import com.example.water13.source.Repo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class GameViewModel : ViewModel() {

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        uiScope.launch {
            openGame()
        }
    }

    val cardsString = MutableLiveData<String>()

    val cardsImage = Transformations.map(cardsString) {
        it.split(" ").map { card ->
            val color = when (card[0]) {
                '&' -> "hongxin"
                '$' -> "heitao"
                '#' -> "fangkuai"
                '*' -> "meihua"
                else -> ""
            }
            val num = when (val originNum = card.substring(1)) {
                "A" -> "a"
                "J" -> "j"
                "Q" -> "q"
                "K" -> "k"
                else -> originNum
            }
            "c_$color$num"
        }
    }

    val state = MutableLiveData<String>().apply { value = "正在出牌" }

    val history = MutableLiveData<String>()

    val message = MutableLiveData<String>()

    fun onNextClicked() {
        cardsString.value?.let {
            history.value = """
                ${Calendar.getInstance().time}
                ${cardsString.value}
                ${history.value?:""}
            """
        }
        uiScope.launch {
            openGame()
        }
    }

    private suspend fun openGame() {
        try {
            cardsString.postValue(Repo.open(User.instance).card)
        } catch (e: Exception) {
            message.value = e.toString()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}