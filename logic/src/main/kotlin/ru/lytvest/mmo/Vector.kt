package ru.lytvest.mmo

class Vector(val dx: Float, val dy: Float) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Vector

        if (dx != other.dx) return false
        if (dy != other.dy) return false

        return true
    }

    override fun hashCode(): Int {
        var result = dx.hashCode()
        result = 31 * result + dy.hashCode()
        return result
    }

    override fun toString(): String {
        return "Vector(dx=$dx, dy=$dy)"
    }

    companion object {
        val ZERO = Vector(0f, 0f);
    }

}