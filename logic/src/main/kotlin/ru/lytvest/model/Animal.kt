package ru.lytvest.model

abstract class Animal : Block(), Hp, Eat {
    override var hp: Int = 0
    var find : Position? = null
    val commands = Commands()
    init {
        commands.randomize(this, 10)
    }
    override fun update(world: World) {
        super.update(world)
        hp -= 1
        commands.workAll(world, this)
        if (hp <= 0) {
            death(world)
        }
    }

    override fun eat(item: Item, world: World): Boolean {
        return false
    }

    abstract fun death(world: World)

}


