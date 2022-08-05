package ru.lytvest.aeon

import ru.lytvest.learn.ListBuysAI
import java.util.*

class Battle(val heroLeft: Hero, val heroRight: Hero) {

    var numberCourse = 0
    var numberGame = 0
    var leftWins = 0
    var rightWins = 0

    fun start() {
        numberCourse = 0
        numberGame += 1
        heroLeft.startBattle(heroRight)
        heroRight.startBattle(heroLeft)
        if (numberGame == 2){
            heroLeft.initShopOpt()
            heroRight.initShopOpt()
        }
    }

    fun nextCourse(): Course {
        numberCourse += 1

        heroLeft.startCourse()
        heroRight.startCourse()

        val leftAttack = heroLeft.calcAttack()
        val rightAttack = heroRight.calcAttack()

        val leftBlock = heroLeft.calcBlockedDamage(rightAttack)
        val rightBlock = heroRight.calcBlockedDamage(leftAttack)

        heroLeft.minusHp(rightAttack.all() - leftBlock)
        heroRight.minusHp(leftAttack.all() - rightBlock)

        val leftHp = heroLeft.hp
        val rightHp = heroRight.hp

        heroLeft.endCourse()
        heroLeft.endCourse()

        val course = Course()
        course.left += "hp" to heroLeft.hp
        course.left += "maxHp" to heroLeft.maxHp.toDouble()
        course.left += "minus" to rightAttack.all() - leftBlock
        course.left += "regen" to heroLeft.hp - leftHp

        course.right += "hp" to heroRight.hp
        course.right += "maxHp" to heroRight.maxHp.toDouble()
        course.right += "minus" to leftAttack.all() - rightBlock
        course.right += "regen" to heroRight.hp - rightHp

        return course
    }

    fun endBattle() {
        if (heroLeft.hp <= 0 && heroRight.hp > 0) {
            heroRight.money += 20
            rightWins += 1
        }
        if (heroRight.hp <= 0 && heroLeft.hp > 0) {
            heroLeft.money += 20
            leftWins += 1
        }

        heroLeft.endBattle()
        heroRight.endBattle()
    }

    fun isEndBattle(): Boolean {
        return heroLeft.hp <= 0 || heroRight.hp <= 0 || numberCourse >= 100
    }

    fun nextBattle(): List<Course> {
        val list = arrayListOf<Course>()
        start()
        while (!isEndBattle()) {
            list += nextCourse()
        }
        endBattle()
        return list
    }

    fun isEndGame(): Boolean {
        return leftWins >= 5 || rightWins >= 5
    }

    fun nextGame(ai: Shop, ai1: Shop) {
        ai.clear()
        ai1.clear()
        while (numberGame < 10 && !isEndGame()) {
            ai.buys(heroLeft)
            ai1.buys(heroRight)
            nextBattle()
        }
        //println("game ${heroLeft.name} vs ${heroRight.name}    [${leftWins} - ${rightWins}]")
    }
}

fun main() {
    println("............   Console Aeon   ..............")
    val battle = Battle(Fatty(), Hero())
    battle.heroLeft.name = "1 Герой"
    battle.heroRight.name = "2 Cупер герой"
    val ai = ListBuysAI(battle.heroRight.shop.size)
    while (!battle.isEndGame()) {
        printHero(battle.heroLeft)
        consoleShop(battle.heroLeft)
        printHero(battle.heroRight)
        ai.buys(battle.heroRight)
        println("ai buy " + ai.listBuys)
        battle.nextBattle().forEach {
            println(it)
        }
        println("всего ударов:" + battle.numberCourse)
        println("Игра номер : ${battle.numberGame}")
    }
}

fun String.removeOpt(): String {
    return if (this.startsWith("opt-")) this.substring(4) else this
}

fun printChr(chr: String, value: Double): String {
    if(Hero.withPercent.contains(chr.removeOpt()))
        return "${(value*100).toInt()}%"
    return value.toInt().toString()
}

private fun consoleShop(hero: Hero) {
    val shop = hero.shop.toList()
    val input = Scanner(System.`in`);
    do {
        for (i in 1..shop.size) {
            val (name, item) = shop[i - 1]
            println("$i] ${Hero.names[name.removeOpt()]} +${printChr(name, item.add)}   ${item.cost.toInt()}\$")
        }
        print("Введите номер покупки или 0: ")
        val num = input.nextInt()
        if (num in 1..shop.size) {
            val (name, item) = shop[num - 1]
            if (hero.buy(name))
                println("Куплено +${printChr(name, item.add)} ${Hero.names[name.removeOpt()]}  - ${item.cost.toInt()}\$")
            else
                println("нельзя купить ${Hero.names[name.removeOpt()]}")
            printHero(hero)
        }
    } while (num != 0)
}

private fun printHero(hero: Hero) {
    print("${hero.name}:\n| ")
    val fields = hero.toMap()
    for ((name, count) in fields) {
        print("${Hero.names[name]} : ${printChr(name, count)} | ")
    }
    println()
}

