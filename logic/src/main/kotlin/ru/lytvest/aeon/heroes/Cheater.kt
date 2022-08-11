package ru.lytvest.aeon.heroes

import ru.lytvest.aeon.Attack

class Cheater : Hero() {
    override fun calcAttack(): Attack {
        return super.calcAttack()
    }
}