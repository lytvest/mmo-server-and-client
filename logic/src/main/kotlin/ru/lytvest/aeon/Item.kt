package ru.lytvest.aeon

typealias Method = ((Double) -> Boolean)

data class Item(val cost: Double, val add: Double, val method: Method)

data class Attack(var damage: Double, var spell: Double) {
    fun all() = damage + spell
}