package ru.lytvest.mmo

import kotlin.math.roundToInt
import kotlin.math.sqrt


open class Point(val x: Float = 0f, val y: Float = 0f)  {

    fun distanceTo(point: Point): Float {
        return sqrt((x - point.x) * (x - point.x) + (y - point.y) * (y - point.y))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }

    private fun Float.round() = (this * 10).roundToInt() / 10.0
    override fun toString(): String {
        return "(${x.round()}, ${y.round()})"
    }

//    fun compareTo(other: Point): Int {
//        return if (x < other.x) -1 else if (x == other.x) checkY(this, other) else 1
//    }

//    private fun checkY(o1: Point, o2: Point): Int {
//        return if (o1.y < o2.y) -1 else if (o1.y == o2.y) 0 else 1
//    }


}
