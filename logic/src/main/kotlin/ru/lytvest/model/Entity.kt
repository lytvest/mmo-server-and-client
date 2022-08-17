package ru.lytvest.model

open class Entity : Position(0, 0){


    open fun update(world: World) {

    }



    companion object {
        var ids = 1

        fun nextId() = ids++
    }

}