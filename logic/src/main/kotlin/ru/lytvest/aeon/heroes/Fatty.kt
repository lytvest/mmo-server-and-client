package ru.lytvest.aeon.heroes

class Fatty : Hero() {

    init {
        val item = shop["hp"]!!
        shop["hp"] = item.copy(add = (item.add * 1.1).toInt().toDouble())
    }

    override fun initShopOpt() {
        super.initShopOpt()
        val item = shop["opt-hp"]!!
        shop["opt-hp"] = item.copy(add = (item.add * 1.1).toInt().toDouble())
    }
}
