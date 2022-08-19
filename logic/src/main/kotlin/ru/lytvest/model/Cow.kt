package ru.lytvest.model

import kotlin.math.min


class Cow : Animal() {
    var id = Entity.nextId()
    override var hp = 100
    override fun update(world: World) {
        hp -= 1
        super.update(world)
    }
    override fun eat(grass: Grass, world: World): Boolean {
        if (grass.hp > 0) {
            val eat = min(grass.hp, 5)
            grass.hp -= eat
            hp += eat * 3
            if (grass.hp <= 0)
                world[x, y] -= grass
            return true
        }
        return false
    }

    override fun toString(): String {
        return "${id % 100}C$hp"
    }
}
