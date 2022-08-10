package ru.lytvest.aeon.heroes

class Fell : Hero() {

    override fun startBattle(enemy: Hero, numberBattle: Int) {
        super.startBattle(enemy, numberBattle)
        for(i in 1..(STACK + numberBattle * BONUS).toInt())
            incomingDamage()
    }

    companion object {
        const val STACK = 2
        const val BONUS = 0.1
    }
}