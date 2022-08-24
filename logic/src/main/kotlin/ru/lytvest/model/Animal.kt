package ru.lytvest.model

abstract class Animal(world: World) : Block(world), Hp, Eat {
    override var hp: Int = 90
    var find : Position? = null
    val commands = Commands()
    init {
        commands.randomize(this, 10)
    }
    override fun update() {
        super.update()
        hp -= 1
        commands.workAll(this)
        if (hp <= 0) {
            death()
        }
    }

    override fun eat(item: Item): Boolean {
        return false
    }

    abstract fun death()

}


