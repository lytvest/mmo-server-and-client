package ru.lytvest.aeon

data class Attack(val damage: Double, val spell: Double, val factor: Double, val isCritical: Boolean, val criticalFactor: Double) {

    fun all(): Double = physical() + spell

    fun physical() = if (isCritical)
        (damage * factor) * (criticalFactor)
    else
        (damage * factor)

}
