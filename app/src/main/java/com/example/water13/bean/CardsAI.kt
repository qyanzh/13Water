package com.example.water13.bean

import com.example.water13.component.toIndex

class CardsAI(val cards: List<Card>) {

    val cardCounts = List(15) { 0 }

    val flowerCounts = List(4) { 0 }

    var redCount = 0
    var blackCount = 0

    val solved = mutableListOf<String>()

    fun getCount() {
        cards.forEach {
            cardCounts[it.num].inc()
            flowerCounts[it.flower.toIndex()].inc()
        }
        redCount = flowerCounts[0] + flowerCounts[1]
        blackCount = 13 - redCount
    }

    /* 至尊青龙 全部同花 */
    fun isZhizun(): Boolean = flowerCounts.any { it == 13 }

    /* 一条龙 2-A */
    fun isYitiaoLong(): Boolean = cardCounts.all { it == 1 }

    /* 十二皇族 12张>10 */
    fun isShierhuangzu(): Boolean = cardCounts.subList(11, 14).sum() == 12


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


