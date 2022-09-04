package ru.lytvest.model


import kotlin.math.abs

open class Position (world: World, _x: Int, _y: Int) {

    var x: Int
    var y: Int

    @Transient
    var world: World


    init {
        x = _x
        y = _y
        while (x < 0) x += world.width
        while (y < 0) y += world.height
        while (x >= world.width) x -= world.width
        while (y >= world.height) y -= world.height
        this.world = world
    }

    fun up(): Position {
        return Position(world, x, y - 1)
    }
    fun down(): Position {
        return Position(world, x, y + 1)
    }
    fun left(): Position {
        return Position(world,x - 1, y)
    }
    fun right(): Position {
        return Position(world,x + 1, y)
    }

    fun ways(): List<Position>{
        return listOf(up(), right(), down(), left())
    }

    fun allInRadius(r: Int): Set<Position>{
        val set = mutableSetOf<Position>()
        for(nx in -r..r){
            for(ny in -r..r){
                if (abs(nx) + abs(ny) <= r){
                    set.add(Position(world,x + nx, y + ny))
                }
            }
        }
        return set
    }

    fun allInRadiusSorted(r: Int): List<Position>{
        val set = mutableSetOf<Pair<Int, Int>>()
        for(nx in -r..r){
            for(ny in -r..r){
                if (abs(nx) + abs(ny) <= r){
                    set.add(nx to ny)
                }
            }
        }
        return set.toList().sortedBy { abs(it.first) + abs(it.second) }.map { Position(world,it.first + x, it.second + y) }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Position

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    override fun toString(): String {
        return "$x $y"
    }
}
