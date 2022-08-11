package ru.lytvest.aeon.heroes

import ru.lytvest.aeon.Attack
import kotlin.math.min

class Master : Hero() {

    var turn: Int = 0
    var lastAttack: Float = 0f

    override fun startBattle(enemy: Hero, numberBattle: Int) {
        super.startBattle(enemy, numberBattle)
        turn = numberBattle
    }

    override fun calcAttack(): Attack {
        val res = super.calcAttack()
        lastAttack = res.all().toFloat()
        return res
    }

    override fun regeneration() {
        hp = min(maxHp.toDouble(), hp + regen + lastAttack * (STEAL + turn * BONUS))
    }

    companion object{
        const val STEAL = 0.15f
        const val BONUS = 0.006f
    }
}