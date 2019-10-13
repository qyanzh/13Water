package com.example.water13

import com.example.water13.bean.Card
import com.example.water13.bean.CardsAI
import com.example.water13.component.toSortedCards
import com.example.water13.source.Network
import com.example.water13.source.UserDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    val scope = CoroutineScope(Dispatchers.IO)

    @Test
    fun test() {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            val cardsString = getCardsString()
            val result = CardsAI(cardsString.toSortedCards()).solve()
            println(result)
        }
        Scanner(System.`in`).nextLine()
    }

    suspend fun getCardsString(): String {
        val token = Network.api.loginAsync(UserDto("zqy1", "zqy")).await().data.token
        return Network.api.openGameAsync(token).await().data.card
    }


    @Test
    fun testing() {
        val cardsString = "*9 &8 &5 #J #4 &6 *10 #A #Q \$7 \$10 \$6 \$3"
        val result = CardsAI(cardsString.toSortedCards()).apply {
            getCount()
            println(this.cardsInFlowerOrder)
        }
    }

    @Test
    fun getShunziTest() {
//            val cardsString =
//                    "*5 *6 *7 "+
//                    "#2 #3 #4 #5 #6 " +
//                    "$6 $7 $8 $9 $10"
//        val cardsString =
//            "*K *Q *J " +
//                    "#2 #3 #4 #5 #6 " +
//                    "$10 \$J \$Q \$K \$A"
        val cardsString =
                    "*5 *6 *7 "+
                    "#2 #3 #4 #5 #6 " +
                    "$3 $4 $5 $6 $7"
        CardsAI(cardsString.toSortedCards()).apply {
            print(isSanshunzi())
        }
    }

    @Test
    fun copyTest() {
        val card = Card("#2")
        print(card === card.copy())
    }

    @After
    fun pause() {
    }
}


