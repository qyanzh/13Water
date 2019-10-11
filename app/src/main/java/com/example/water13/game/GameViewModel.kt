package com.example.water13.game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.water13.bean.User
import com.example.water13.source.Repo
import kotlinx.coroutines.*
import java.util.*

class GameViewModel : ViewModel() {

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var id = 0

    private val cardsString = MutableLiveData<String>()

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

    val stateString = MutableLiveData<String>()


    val history = MutableLiveData<String>()

    val message = MutableLiveData<String>()

    fun onMsgShowed() {
        message.value = ""
    }

    val auto = MutableLiveData<Boolean>(true)

    fun onNextClicked() {
        cardsString.value?.let {
            history.value = """
            ${Calendar.getInstance().time}
            ${cardsString.value}
            ${history.value ?: ""}
            """
        }
        uiScope.launch {
            openGame()
        }
    }

    private suspend fun openGame() {
        try {
            val data = Repo.open(User.instance)
            cardsString.value = (data.card)
            id = data.id
            stateString.value = "正在出牌"
            submitCards()
            stateString.value = "出牌完成"
        } catch (e: Exception) {
            message.value = e.message
            stateString.value = "出牌异常"
        }
        if (auto.value!!) {
            delay(1000)
            onNextClicked()
        }
    }

    private suspend fun submitCards() {
        val submitList = mutableListOf<String>()
        cardsString.value?.split(" ")?.let { cardsList ->
            submitList.add(cardsList.subList(0, 3).joinToString(" "))
            submitList.add(cardsList.subList(3, 8).joinToString(" "))
            submitList.add(cardsList.subList(8, 13).joinToString(" "))
            Repo.submit(id, submitList, User.instance)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun closeAuto() {
        auto.value = false
        message.value = null
    }
}