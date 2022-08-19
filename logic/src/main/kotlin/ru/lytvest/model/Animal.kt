package ru.lytvest.model

open class Animal : Block(), Hp, Eat {
    override var hp: Int = 0
    var find : Position? = null
    val commands = Commands()
    init {
        commands.randomize(this, 10)
    }
    override fun update(world: World) {
        super.update(world)

        commands.workAll(world, this)
    }

    override fun eat(grass: Grass, world: World): Boolean {
        return false
    }

}


