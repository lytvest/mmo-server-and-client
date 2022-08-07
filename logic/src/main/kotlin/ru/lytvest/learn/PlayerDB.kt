package ru.lytvest.learn

import ru.lytvest.aeon.Player

interface PlayerDB {
    fun findById(id:Long): Player?

    fun save(player: Player)

    fun count(): Int
}