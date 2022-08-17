package ru.lytvest.model

class Grass : Entity(), Hp {
    override var hp: Int = 1
    override fun update(world: World) {
        hp += 1
        if (hp >= MAX_HP) {
            val item = world[ways().random()]
            if (!item.contain(Grass::class)){
                item.add(Grass())
            }
            hp = 5
        }
    }
    companion object {
        val MAX_HP = 10
    }
}