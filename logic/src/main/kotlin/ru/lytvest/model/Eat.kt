package ru.lytvest.model

interface Eat {
    fun eat(grass: Grass, world: World): Boolean
}