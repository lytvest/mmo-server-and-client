package ru.lytvest.learn

import ru.lytvest.aeon.Course
import ru.lytvest.aeon.heroes.Hero
import ru.lytvest.aeon.Player

abstract class PlayerWithAutoBuys : Player() {
    var errors = 0
    var listBuys = arrayListOf<String>()

    open fun autoBuy(items: List<String>): String {
        return ""
    }

    override fun buys(hero: Hero, info: List<Course>, numberBattle: Int) {
        listBuys.clear()
        errors = 0
        do {
            hero.toMap()
            val buyName = autoBuy(hero.shop.keys.toList())

            if(hero.buy(buyName)){
                listBuys += buyName
            } else {
                errors += 1
            }
        } while (errors < 3 && buyName != "exit")
    }

}