package ru.lytvest.model

class Meat : Entity, Hp {
    override var hp: Int = 90


    constructor(hp: Int) : super() {
        this.hp = hp
    }

    constructor() : super()

    override fun update(world: World) {
        super.update(world)
        hp -= 1
        if (hp <= 0){
            world.remove(this)
        }
    }

    override fun toString(): String {
        return when (hp){
            in 75..Int.MAX_VALUE -> "MM"
            in 50..74 -> "Mm"
            in 25..49 -> "M"
            in 1..25 -> "m"
            else -> ""
        }
    }
}
