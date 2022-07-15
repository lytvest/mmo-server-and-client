package ru.lytvest.mmo

import kotlin.math.sqrt

open class Circle {
    var x = 0f
    var y = 0f
    var r = 1f

    fun position() = Point(x, y)

    fun left() = x - r
    fun down() = y - r
    fun width() = r * 2
    fun height() = r * 2
}