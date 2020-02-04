package com.example.water13

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.example.water13.bean.Card
import com.example.water13.bean.CardsAI
import com.example.water13.component.toSortedCards
import com.example.water13.source.Network
import com.example.water13.source.UserDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Test


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
            val result = CardsAI(cardsString.toSortedCards().toMutableList()).solve()
            println(result)
        }
    }

    suspend fun getCardsString(): String {
        val token = Network.api.loginAsync(UserDto("zqy1", "zqy")).await().data.token
        return Network.api.openGameAsync(token).await().data.card
    }


    @Test
    fun testing() {
        val cardsString = "*9 &8 &5 #J #4 &6 *10 #A #Q \$7 \$10 \$6 \$3"
        val result = CardsAI(cardsString.toSortedCards().toMutableList()).apply {
            getCount()
            println(this.cardsInFlowerOrder)
        }
    }

    @Test
    fun sanshunziCase1() {
        val cardsString =
            "*5 *6 *7 " +
                    "#2 #3 #4 #5 #6 " +
                    "$3 $4 $5 $6 $7"
        CardsAI(cardsString.toSortedCards().toMutableList()).apply {
            this.solve()
            println(this.solved)
        }
    }

    @Test
    fun sanshunziCase2() {
        val cardsString =
            "*2 *3 *4 " +
                    "#2 #3 #4 #5 #6 " +
                    "$5 $6 $7 $8 $9"
        CardsAI(cardsString.toSortedCards().toMutableList().toMutableList()).apply {
            print(isSanshunzi())
        }
    }

    @Test
    fun sanshunziCase3() {
        val cardsString =
            "*2 *3 *5 " +
                    "#2 #3 #4 #5 #6 " +
                    "$4 $5 $6 $7 $8"
        CardsAI(cardsString.toSortedCards().toMutableList().toMutableList()).apply {
            print(isSanshunzi())
        }
    }

    @Test
    fun shunziTest() {
        val s = FileUtil.convertFileToStringList(path)
        s.forEach {
            println("!!!!!!!$it!!!!!!!")
            CardsAI(it.toSortedCards().toMutableList().toMutableList()).apply {
                solve()
            }
        }
    }

    @Test
    fun debug() {
        val s = "*10 #A \$A &J \$10 *5 &2 \$J #9 *6 \$9 #K &8"
        CardsAI(s.toSortedCards().toMutableList().toMutableList()).apply {
            solve()
        }

    }

    @Test
    fun copyTest() {
        val card = Card("#2")
        print(card === card.copy())
    }

    val path = "C:\\Users\\75334\\Desktop\\cards.txt"

    @Test
    fun tonghuashunTest() {
        val cardsString =
            "*2 *3 *4 " +
                    "#2 #3 #4 #5 #6 " +
                    "$5 $6 $7 $8 $9"
        CardsAI(cardsString.toSortedCards().toMutableList().toMutableList()).apply {
            solve()
        }
    }

    @Test
    fun zhaTest() {
        val cardsString =
            "*7 *8 *9 " +
                    "#2 *2 $2 &2 #6 " +
                    "#3 *3 $3 &3 *6"
        CardsAI(cardsString.toSortedCards().toMutableList().toMutableList()).apply {
            solve()
        }
    }

//    @Test
//    fun getCards() {
//        val sb = StringBuilder()
//        scope.launch {
//            for(i in 1..100) {
//                sb.append("\"")
//                sb.append(getCardsString())
//                sb.append("\"").append("\n")
//            }
//            convertStringToFile(sb.toString(),path)
//        }
//        Scanner(System.`in`).nextLine()
//    }

    @Test
    fun tonghuashunZhadanTest() {
        val cardsString =
            "*7 *8 *9 " +
                    "#2 *2 $2 &2 #6 " +
                    "#7 #8 #9 #10 #J"
        CardsAI(cardsString.toSortedCards().toMutableList().toMutableList()).apply {
            solve()
        }
    }

    @Test
    fun tonghuashunHuluTest() {
        val cardsString =
            "*7 *8 *9 " +
                    "#2 *2 $2 &6 #6 " +
                    "#7 #8 #9 #10 #J"
        CardsAI(cardsString.toSortedCards().toMutableList().toMutableList()).apply {
            solve()
        }
    }

    @Test
    fun twoHuluTest() {
        val cardsString =
            "*7 *8 *9 " +
                    "#2 *2 $2 &6 #6 " +
                    "#3 *3 $3 &7 #7"
        CardsAI(cardsString.toSortedCards().toMutableList().toMutableList()).apply {
            solve()
        }
    }

    @Test
    fun tonghuaTest() {
        val cardsString =
            "*7 *8 *9 " +
                    "*J *K *A #K #J " +
                    "#3 *3 $3 &2 #2"
        CardsAI(cardsString.toSortedCards().toMutableList()).apply {
            solve()
        }
    }

    @Test
    fun liangduiTest() {
        val cardsString =
            "*7 #7 *8 " +
                    "&8 *2 *3 #4 &6 " +
                    "#9 &10 &J &Q #A"
        CardsAI(cardsString.toSortedCards().toMutableList()).apply {
            solve()
        }
    }


    @Test
    fun twoTonghuaTest() {
        val cardsString =
            "&10 &9 &6 " +
                    "*A *K *J *5 *4 " +
                    "#4 #6 #10 #Q #A"
        CardsAI(cardsString.toSortedCards().toMutableList()).apply {
            solve()
        }
    }

    @Test
    fun twoTonghuaTest2() {
        val cardsString =
        "$7 #9 $5 $9 #7 #K \$A *3 &6 \$J *2 #4 #10"
        CardsAI(cardsString.toSortedCards().toMutableList()).apply {
            solve()
        }
    }

    @Test
    fun twoTonghuaTest3() {
        val cardsString =
            "#K #Q &A *7 &2 \$Q \$K \$7 #4 \$10 #J \$J #3"
        CardsAI(cardsString.toSortedCards().toMutableList()).apply {
            solve()
        }
    }

    @Test
    fun specialTest() {
        val cardsString =
            "#K #Q &A " +
                    "*7 &2 \$Q \$K \$7 " +
                    "#4 \$10 #J \$J #3"
        CardsAI(cardsString.toSortedCards().toMutableList()).apply {
            solve()
        }
    }

    @Test
    fun specialTest2() {
        val cardsString =
            "#K #Q &A *7 &2 \$Q \$K \$7 #4 \$10 #J \$J #3"
        CardsAI(cardsString.toSortedCards().toMutableList()).apply {
            solve()
        }
    }

    @Test
    fun specialTest3() {
        val cardsString =
            "#2 &3 *3 \$5 *5 #8 *9 #10 #J *J &Q #K *K"
        CardsAI(cardsString.toSortedCards().toMutableList()).apply {
            solve()
        }
    }

    @Test
    fun specialTest4() {
        val cardsString =
            "$6 *7 *Q &10 *A $10 #6 *J &9 #A *10 #K #8"
        CardsAI(cardsString.toSortedCards().toMutableList()).apply {
            solve()
        }
    }
    @Test
    fun specialTest5() {
        val cardsString =
            "&10 *6 #Q *9 #5 &9 *5 #6 *J \$8 #J &7 #8"
        CardsAI(cardsString.toSortedCards().toMutableList()).apply {
            solve()
        }
    }
    @Test
    fun specialTest6() {
        val cardsString =
            "*A &5 *K *2 &A #6 \$9 #2 &8 \$8 \$7 #7 &4"
        CardsAI(cardsString.toSortedCards().toMutableList()).apply {
            solve()
        }
    }

    @Test
    fun utilTest() {
        val cardsString =
            "#K #Q &A *7 &2 \$Q \$K \$7 #4 \$10 #J \$J #3"
        println(ImageUtil.convertCardsToFileNames(cardsString))
        //转为 [c_fangkuaik, c_fangkuaiq, c_hongxina, c_meihua7, c_hongxin2, c_heitaoq,
        // c_heitaok, c_heitao7, c_fangkuai4, c_heitao10, c_fangkuaij, c_heitaoj, c_fangkuai3]
    }

    @Test
    fun subListTest() {
        val cardsString =
            "#K #Q &A *7 &2 \$Q \$K \$7 #4 \$10 #J \$J #3"
        println(cardsString.toSortedCards().subList(0, 3).size)
        println(ImageUtil.convertCardsToFileNames(cardsString))
        //转为 [c_fangkuaik, c_fangkuaiq, c_hongxina, c_meihua7, c_hongxin2, c_heitaoq,
        // c_heitaok, c_heitao7, c_fangkuai4, c_heitao10, c_fangkuaij, c_heitaoj, c_fangkuai3]
    }

    // #方块 $黑桃 &红桃 *梅花
}


