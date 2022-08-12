package ru.lytvest.aeon

data class Attack(var damage: Double, var spell: Double, var factor: Double, var isCritical: Boolean, var criticalFactor: Double, var spellFactor: Double = 1.0) {

    fun all(): Double = physical() + spell * spellFactor

    fun physical() = if (isCritical)
        damage * factor * criticalFactor
    else
        damage * factor

}
