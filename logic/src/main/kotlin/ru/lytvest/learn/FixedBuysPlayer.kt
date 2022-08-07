package ru.lytvest.learn

import kotlin.random.Random


class FixedBuysPlayer() : PlayerWithAutoBuys() {

    var array: Array<Int> = arrayOf()
    var iter = 0



    override fun autoBuy(list: List<String>): String {
        val shop = listOf("exit") + list
        if (iter >= array.size)
            iter = 0
        val index = array[iter++]
        return if (index in shop.indices) shop[index] else ""
    }

    override fun toString(): String {
        return "AI[$id]"
    }

    override fun hashCode(): Int {
        return id.toInt()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FixedBuysPlayer

        if (id != other.id) return false

        return true
    }

    override fun preparation() {
        super.preparation()
        iter = 0

        if (array.size < SIZE){
            array = Array(SIZE){ if (array.size <= it) Random.nextInt(countFields) else array[it] }
        }
    }

    companion object{
        val SIZE = 100
        val countFields = 20
    }
}
