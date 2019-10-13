package com.example.water13.bean

import com.example.water13.component.toIndex

class CardsAI(val cards: List<Card>) {

    val CardsInNumOrder = List(15) { mutableListOf<Card>() }

    val cardsInFlowerOrder = List(4) { mutableListOf<Card>() }

    var redCount = 0

    var blackCount = 0

    var duiziCount = 0

    var sanCount = 0

    var zhaCount = 0

    val solved = mutableListOf<String>()

    fun getCount() {
        cards.forEach {
            CardsInNumOrder[it.num].add(it)
            cardsInFlowerOrder[it.flower.toIndex()].add(it)
        }
        redCount = cardsInFlowerOrder[0].size + cardsInFlowerOrder[1].size
        blackCount = 13 - redCount
        CardsInNumOrder.forEach {
            when (it.size) {
                2 -> duiziCount++
                3 -> sanCount++
                4 -> zhaCount++
            }
        }
    }

    /* 1.至尊青龙 全部同花 */
    fun isZhizun(): Boolean = cardsInFlowerOrder.any { it.size == 13 }

    /* 2.一条龙 2-A */
    fun isYitiaoLong(): Boolean = CardsInNumOrder.all { it.size == 1 }

    /* 3.十二皇族 12张>10 */
    fun isShierhuangzu(): Boolean = CardsInNumOrder.subList(11, 15).sumBy { it.size } == 12

    /* 4.三同花顺 */
    fun isSantonghuashun(): Boolean = cardsInFlowerOrder.filter { it.size != 0 }.all { issShunzi(it) }

    /* 5.三个炸 */
    fun isSangezha(): Boolean = zhaCount == 3

    /* 6.全>=8 */
    fun isQuanda(): Boolean = CardsInNumOrder.subList(1, 8).all { it.size == 0 }

    /* 7.全<8 */
    fun isQuanxiao(): Boolean = CardsInNumOrder.subList(8, 15).all { it.size == 0 }

    /* 全同色 */
    fun isCouyise(): Boolean = blackCount == 0 || redCount == 0

    /* 9.双怪冲三 2葫芦 + 1对子 */
    fun isShuangguaichongsan() = sanCount == 2 && duiziCount == 3

    /* 10.四套三条 4三条 */
    fun isSitaosantiao() = sanCount == 4

    /* 11.五对三条 5对子1三条 */
    fun isWuduisantiao() = duiziCount == 5 && sanCount == 1

    /* 12.六对半 6对子 */
    fun isLiuduiban() = duiziCount == 6

    fun getShunzi(): List<List<Card?>> {
        var copy: MutableList<Card?> = cards.toMutableList()
        val cutList = mutableListOf<List<Card?>>()
        while (copy.size != 0) {
            val temp = mutableListOf<Card?>()
            temp.add(copy[0]?.copy())
            copy[0] = null
            for (i in 1..copy.lastIndex) {
                if (copy[i]!!.num == temp.last()!!.num + 1) {
                    temp.add(copy[i])
                    copy[i] = null
                }
            }
            cutList.add(temp)
            copy = copy.filterNotNull().toMutableList()
        }
        outer@ for (i in 0..cutList.lastIndex) {
            if (cutList[i].size > 5) {
                for (j in i + 1..cutList.lastIndex) {
                    val sumSize = cutList[i].size + cutList[j].size
                    if (sumSize == 8 || sumSize == 10) {
                        if (cutList[j].map { it?.num }.any {
                                cutList[i].map { it?.num }.contains(it)
                            }) {
                            cutList.add(cutList[i] + cutList[j])
                            cutList[i] = listOf()
                            cutList[j] = listOf()
                            break@outer
                        }
                    }
                }
            }
        }
        return cutList.filter { it.isNotEmpty() }
    }

    /* 13.三顺子 3顺子 */
    fun isSanshunzi(): Boolean =
        getShunzi().filter { it.isNotEmpty() }.all { it.size == 3 || it.size == 5 || it.size == 8 || it.size == 10 || it.size == 13 }


    /* 14.三同花 3同花 */
    fun isSantonghua() = cardsInFlowerOrder.all { it.size == 3 || it.size == 5 }


    /* 1.同花顺 */
    fun issTonghuashun(cards: List<Card>): Boolean {
        return issTonghua(cards) && issShunzi(cards)
    }

    /* 2.炸弹 */

    /* 3.葫芦 */

    /* 4.同花 */
    fun issTonghua(cards: List<Card>): Boolean {
        val flower = cards[0].flower
        return cards.all { it.flower == flower }
    }

    /* 5.顺子 */
    fun issShunzi(cards: List<Card>): Boolean {
        var min = cards[0].num
        return cards.all { it.num == min++ }
    }

    /* 6.三条 */

    /* 7.连对 */

    /* 8.二对 */

    /* 9.一对 */


    fun solve(): List<String> {
        val result = mutableListOf<String>()


        return result
    }


    override fun toString(): String {
        return "origin: " + cards.joinToString(" ") {
            it.origin
        } + "\nsolved: " + solved
    }
}


