package ru.lytvest.aeon.heroes

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import ru.lytvest.aeon.Attack
import ru.lytvest.aeon.Battle


class HeroSpec {

    lateinit var battle: Battle
    val me: Hero get() = battle.heroLeft
    val enemy: Hero get() = battle.heroRight
    
    @BeforeEach
    fun initHeroes(){
        battle = Battle("Hero", "Hero", "me", "enemy")
        battle.start()
    }
    
    @Test
    fun testInitme(){
        assertEquals(100.0, me.hp)
        assertEquals(100, me.maxHp)
        assertEquals(100.0, me.money)
        assertEquals(1.0, me.regen)
    }

    @Test
    fun testBuyHp(){
        me.buy("hp")

        assertEquals(122.0, me.hp)
        assertEquals(122, me.maxHp)
        assertEquals(90.0, me.money)
    }

    @Test
    fun testCalcAttack(){
        me.damage = 10.0
        me.spell = 15.0
        assertEquals(25.0, me.calcAttack().all())
    }

    @ParameterizedTest
    @CsvSource(
        "0, 0, 0",
        "3, 0, 3",
        "0, 0.5, 5",
        "2, 0.5, 7",
        "10, 0, 10"
    )
    fun testCalcBlock(armor: Double, shield: Double, block: Double) {
        me.armor = armor
        me.shield = shield
        assertEquals(block, me.calcBlockedDamage(Attack(10.0,10.0, 1.0, false, 2.0)))
    }

    @ParameterizedTest
    @CsvSource(
        "0, 0, 0",
        "3, 0, 3",
        "0, 0.5, 10",
        "2, 0.5, 12",
        "10, 0, 20"
    )
    fun testCalcBlockWithCritical(armor: Double, shield: Double, block: Double) {
        me.armor = armor
        me.shield = shield

        assertEquals(30.0, Attack(10.0,10.0, 1.0, true, 2.0).all())
        assertEquals(block, me.calcBlockedDamage(Attack(10.0,10.0, 1.0, true, 2.0)))
    }

    @ParameterizedTest
    @CsvSource(
        "2, 2, 2",
    )
    fun testRegen(curr: Double, actual: Double, actual2: Double){

        me.regen = curr
        enemy.regen = curr
        val co1 = battle.nextCourse()

        assertEquals(actual, co1.you["regen"])
        assertEquals(actual, co1.enemy["regen"])

        val co2 = battle.nextCourse()
        assertEquals(actual2, co2.you["regen"])
        assertEquals(actual2, co2.enemy["regen"])

    }


}
