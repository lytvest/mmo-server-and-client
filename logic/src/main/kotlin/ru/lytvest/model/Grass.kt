package ru.lytvest.model

class Grass : Entity(), Hp {
    override var hp: Int = 1
    override fun update(world: World) {
        hp += 1
        if (hp >= MAX_HP) {
            val p = ways().random()
            val item = world[p]
            if (!item.contain(Grass::class)){
                world.put(p, Grass())
            }
            hp = 5
        }
    }

    override fun toString(): String {
        return if (hp < 5) "_" else "'"
    }

    companion object {
        val MAX_HP = 10
    }
}
