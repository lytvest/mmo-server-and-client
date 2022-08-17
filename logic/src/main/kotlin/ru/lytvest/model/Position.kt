package ru.lytvest.model

open class Position(x: Int, y: Int) {
    var x: Int
    var y: Int
    init {
        this.x = if (x < 0) World.width + x else if (x >= World.width) x - World.width else x
        this.y = if (y < 0) World.height + y else if (y >= World.height) y - World.height else y
    }

    fun up(): Position {
        return Position(x, y - 1)
    }
    fun down(): Position {
        return Position(x, y + 1)
    }
    fun left(): Position {
        return Position(x - 1, y)
    }
    fun right(): Position {
        return Position(x + 1, y)
    }

    fun ways(): List<Position>{
        return listOf(up(), right(), down(), left())
    }
}