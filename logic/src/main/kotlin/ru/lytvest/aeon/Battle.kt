package ru.lytvest.aeon

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
    }

    fun nextCourse(): Course {
        numberCourse += 1

        heroLeft.startCourse()
        heroRight.startCourse()

        val leftAttack = heroLeft.calcAttack()
        val rightAttack = heroRight.calcAttack()

        val leftBlock = heroLeft.calcBlock(rightAttack)
        val rightBlock = heroRight.calcBlock(leftAttack)

        heroLeft.minusHp(rightAttack - leftBlock)
        heroRight.minusHp(leftAttack - rightBlock)

        heroLeft.endCourse()
        heroLeft.endCourse()

        val course = Course()
        course.left += "hp" to heroLeft.hp
        course.left += "maxHp" to heroLeft.maxHp
        course.left += "minus" to rightAttack - leftBlock

        course.right += "hp" to heroRight.hp
        course.right += "maxHp" to heroRight.maxHp
        course.right += "minus" to leftAttack - rightBlock

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

}

fun main() {
    println("............   Console Aeon   ..............")
    val battle = Battle(Warrior(), Hero())
    battle.heroLeft.name = "1 Герой"
    battle.heroRight.name = "2 Cупер герой"
    while (!battle.isEndGame()) {
        printHero(battle.heroLeft)
        consoleShop(battle.heroLeft)
        printHero(battle.heroRight)
        consoleShop(battle.heroRight)
        battle.nextBattle().forEach {
            println(it)
        }
        println("всего ударов:" + battle.numberCourse)
        println("Игра номер : ${battle.numberGame}")
    }
}

private fun consoleShop(hero: Hero) {
    val shop = hero.shop.toList()
    val input = Scanner(System.`in`);
    do {
        for (i in 1..shop.size) {
            val (name, item) = shop[i - 1]
            println("$i] $name +${item.add}   ${item.cost}\$")
        }
        print("Введите номер покупки или 0: ")
        val num = input.nextInt()
        if (num in 1..shop.size) {
            val (name, item) = shop[num - 1]
            if (hero.buy(name))
                println("success buy +${item.add} $name  - ${item.cost}\$")
            else
                println("fail buy $name")
            printHero(hero)
        }
    } while (num != 0)
}

private fun printHero(hero: Hero) {
    print("${hero.name}:\n| ")
    val fields = hero.fields().toList()
    for ((name, count) in fields) {
        print("$name : $count | ")
    }
    println()
}

