package ru.lytvest.aeon.heroes

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

    // 15% проходят сквось броню

}