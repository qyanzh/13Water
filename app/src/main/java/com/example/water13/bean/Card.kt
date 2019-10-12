package com.example.water13.bean

data class Card(
    val origin: String
) : Comparable<Card> {
    val flower: Char = origin[0]
    val color = if (flower == '&' || flower == '#') 0 else 1
    val num: Int = when (val s = origin.substring(1, origin.length)) {
        "J" -> 11
        "Q" -> 12
        "K" -> 13
        "A" -> 14
        else -> s.toInt()
    }

    fun sameColorTo(other: Card) = this.color == other.color

    fun sameFlowerTo(other:Card) = this.flower == other.flower

    fun sameNumTo(other: Card) = this.num == other.num

    override fun compareTo(other: Card): Int {
        return if (this.num != other.num) {
            this.num - other.num
        } else {
            this.flower - other.flower
        }
    }
}