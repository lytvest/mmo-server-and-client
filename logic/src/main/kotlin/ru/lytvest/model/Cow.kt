package ru.lytvest.model

import kotlin.math.min


class Cow : Animal() {
    var id = Entity.nextId()

    override fun update(world: World) {
        super.update(world)
    }
    override fun eat(item: Item, world: World): Boolean {
        item.find(Grass::class)?.let { grass ->
            if (grass.hp > 0) {
                val eat = min(grass.hp, 5)
                grass.hp -= eat
                hp += eat * 3
                if (grass.hp <= 0)
                    world[x, y] -= grass
                return true
            }
        }
        return false
    }

    override fun toString(): String {
        return "${id % 100}C$hp"
    }

    override fun death(world: World) {
        world.remove(this)
        world.put(this, Meat(100))
    }
}
