package ru.lytvest.aeon

import ru.lytvest.aeon.heroes.Fatty
import ru.lytvest.aeon.heroes.Hero
import ru.lytvest.learn.FilesPlayerDB
import ru.lytvest.learn.FixedBuysPlayer
import ru.lytvest.learn.PlayerWithAutoBuys
import java.util.*
import kotlin.random.Random

class Battle(heroLeftClass: String, heroRightClass: String, userNameLeft: String = "no_name", userNameRight: String = "no_name",) {
    val heroLeft: Hero = Hero.byClass(heroLeftClass, userNameLeft)
    val heroRight: Hero = Hero.byClass(heroRightClass, userNameRight)
    var numberCourse = 0
    var numberGame = 0
    var leftWins = 0
    var rightWins = 0

    init {
        heroLeft.username = userNameLeft
        heroRight.username = userNameRight
    }

    fun start() {
        numberCourse = 0
        numberGame += 1
        heroLeft.startBattle(heroRight, numberGame)
        heroRight.startBattle(heroLeft, numberGame)
        if (numberGame == 2){
            heroLeft.initShopOpt()
            heroRight.initShopOpt()
        }
    }

    fun setSeedRandom(seedL: Long, seedR: Long = seedL + 14){
        heroLeft.random = Random(seedL)
        heroRight.random = Random(seedR)
    }

    fun startCourse(): Course {
        val course = Course()
//        println("start cource hp ${heroLeft.hp}")
        course.you += "hp" to heroLeft.hp
        course.you += "maxHp" to heroLeft.maxHp.toDouble()

        course.enemy += "hp" to heroRight.hp
        course.enemy += "maxHp" to heroRight.maxHp.toDouble()
//        println("cour $course")
        return course
    }

    fun nextCourse(): Course {
        numberCourse += 1

        heroLeft.startCourse(numberCourse)
        heroRight.startCourse(numberCourse)

        val leftAttack = heroLeft.calcAttack()
        val rightAttack = heroRight.calcAttack()

        val leftBlock = heroLeft.calcBlockedDamage(rightAttack)
        val rightBlock = heroRight.calcBlockedDamage(leftAttack)

//        println("attack: $leftAttack -> $rightAttack")
//        println("block: $leftBlock -> $rightBlock")

        heroLeft.minusHp(rightAttack.all() - leftBlock)
        heroRight.minusHp(leftAttack.all() - rightBlock)

        val leftHp = heroLeft.hp
        val rightHp = heroRight.hp

//        println("curr hp $leftHp -> $rightHp")

        heroLeft.endCourse()
        heroRight.endCourse()
//        println("curr hp old ${heroLeft.hp} -> ${heroRight.hp}")

        val course = Course()
        course.you += "hp" to heroLeft.hp
        course.you += "maxHp" to heroLeft.maxHp.toDouble()
        course.you += "minus" to rightAttack.all() - leftBlock
        course.you += "regen" to heroLeft.hp - leftHp

        course.enemy += "hp" to heroRight.hp
        course.enemy += "maxHp" to heroRight.maxHp.toDouble()
        course.enemy += "minus" to leftAttack.all() - rightBlock
        course.enemy += "regen" to heroRight.hp - rightHp

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
        list.add(startCourse())

        while (!isEndBattle()) {
            list += nextCourse()
        }
        endBattle()
        return list
    }

    fun isEndGame(): Boolean {
        return leftWins >= 5 || rightWins >= 5
    }

    fun nextGame(ai: Player, ai1: Player) {
        var courses: List<Course> = listOf()

        ai.preparation()
        ai1.preparation()
        while (numberGame < 10 && !isEndGame()) {

            ai.buys(heroLeft, courses, numberGame)
//            print("left buys:")
//            println((ai as PlayerWithAutoBuys).listBuys)
            ai1.buys(heroRight, courses.map { it.flip() }, numberGame)
//            print("right buys:")
//            println((ai1 as PlayerWithAutoBuys).listBuys)
//            println("hero left ${heroLeft.toMap()}")
//            println("hero right ${heroRight.toMap()}")
            courses = nextBattle()
//            for(course in courses){
//                println(course)
//            }
//            println("-------------------------- end battle ---------------------------")
        }

        //println("game ${heroLeft.name} vs ${heroRight.name}    [${leftWins} - ${rightWins}]")
    }
}

fun main() {
    println("............   Console Aeon   ..............")
    val ai = FilesPlayerDB().findById(4) as PlayerWithAutoBuys
    val battle = Battle("Fatty", ai.heroClass,  "1 Герой", "2 Cупер герой")


    ai.preparation()

    while (!battle.isEndGame()) {
        printHero(battle.heroLeft)
        consoleShop(battle.heroLeft)
        printHero(battle.heroRight)
        ai.buys(battle.heroRight, listOf(), battle.numberGame)
        println("ai buy " + ai.listBuys)
        battle.nextBattle().forEach {
            println(it)
            Thread.sleep(1000)
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
    print("${hero.username}:\n| ")
    val fields = hero.toMap()
    for ((name, count) in fields) {
        print("${Hero.names[name]} : ${printChr(name, count)} | ")
    }
    println()
}

