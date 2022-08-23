package ru.lytvest.model

interface Eat {
    fun eat(item: Item, world: World): Boolean
}
