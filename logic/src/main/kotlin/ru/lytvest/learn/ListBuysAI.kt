package ru.lytvest.learn

import ru.lytvest.aeon.Hero
import ru.lytvest.aeon.Shop
import kotlin.random.Random


class ListBuysAI(val countFields: Int = Hero.names.size * 2) : Shop() {
    val id = currId++
    var array = Array<Int>(SIZE){ Random.nextInt(countFields) }
    var iter = 0

    override fun autoBuy(list: List<String>): String {
        if (iter >= array.size)
            iter = 0
        val index = array[iter++]
        return if (index in list.indices) list[index] else ""
    }

    override fun toString(): String {
        return "AI[$id]"
    }

    override fun hashCode(): Int {
        return id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ListBuysAI

        if (id != other.id) return false

        return true
    }

    override fun clear() {
        super.clear()
        iter = 0
    }

    companion object{
        val SIZE = 100
        var currId = 0
    }
}
