package ru.lytvest.aeon

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import ru.lytvest.learn.RandomPlayer

class BattleSpec {

    @ParameterizedTest
    @CsvSource(
        "10, Hero, Hero",
    )
    fun testTwoBattleWithRandom(seedL: Long, nameL: String, nameR: String) {
        val battle1 = Battle(nameL, nameR)
        val seedR = seedL + 16
        battle1.setSeedRandom(seedL, seedR)
        val courses1 = battle1.nextBattle()

        val battle2 = Battle(nameR, nameL)
        battle2.setSeedRandom(seedR, seedL)
        val courses2 = battle2.nextBattle().map { it.flip() }

        assertEquals(courses1, courses2)

    }
}
