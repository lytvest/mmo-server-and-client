package ru.lytvest.aeon

open class Shop {

    var errors = 0
    var listBuys = arrayListOf<String>()

    open fun autoBuy(items: List<String>): String {
        return ""
    }

    fun buys(hero: Hero){
        listBuys.clear()
        errors = 0
        do {
            hero.toMap()
            val buyName = autoBuy(listOf("exit") + hero.shop.keys.toList())

            if(hero.buy(buyName)){
                listBuys += buyName
            } else {
                errors += 1
            }
        } while (errors < 3 && buyName != "exit")
    }

    open fun clear() {
    }
}