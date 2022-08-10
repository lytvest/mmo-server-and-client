package ru.lytvest.aeon.heroes

class Banker : Hero() {

    var countOptBuys = 0

    override fun successBuy(name: String, cost: Double, add: Double) {
        if (name.isOpt() && countOptBuys < MAX_BUYS) {
            countOptBuys++
            updatesShop()
            if (countOptBuys % SUPER_SELL == 0){
                updatesShop()
            }
        }
    }

    private fun updatesShop() {
        for(name in shop.keys){
            if (name.isOpt()){
                val elem = shop[name]!!
                shopPut(name, elem.add, elem.cost - 1, elem.method)
            }
        }
    }

    companion object {
        const val MAX_BUYS = 60
        const val SUPER_SELL = 3
    }

}

fun main(){
    val h1 = Banker()
    h1.initShopOpt()

    h1.buy("opt-spell")
    println(h1.shop)
}