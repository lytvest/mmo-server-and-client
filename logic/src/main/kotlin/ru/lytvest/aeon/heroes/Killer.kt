package ru.lytvest.aeon.heroes

import ru.lytvest.aeon.Attack

class Killer : Hero() {

    var added: Float = 0f

    override fun successBuy(name: String, cost: Double, add: Double) {
        if (name.contains("damage")){
            added += add.toFloat()
            if (added >= 150){
                damage += 10
            }
        }
    }

    override fun calcAttack(): Attack {
        return super.calcAttack().apply {
            spellFactor += 0.15
            factor -= 0.15
        }
    }
}
