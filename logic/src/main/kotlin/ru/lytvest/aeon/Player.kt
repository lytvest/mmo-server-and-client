package ru.lytvest.aeon

import ru.lytvest.aeon.heroes.Hero

abstract class Player {
    open fun preparation() {}

    abstract fun buys(hero: Hero, info: List<Course>, numberBattle: Int)

    var id: Long = 0L
    var heroClass: String = "Hero"
}