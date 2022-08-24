package ru.lytvest.model

open class Entity(world: World) : Position(world,0, 0){


    open fun update() {

    }

    companion object {
        var ids = 1

        fun nextId() = ids++
    }
}
