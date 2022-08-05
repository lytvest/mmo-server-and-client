package ru.lytvest.learn

import com.google.gson.GsonBuilder
import ru.lytvest.aeon.Battle
import ru.lytvest.aeon.Hero
import java.io.PrintWriter
import kotlin.random.Random


fun main() {
    val gson = GsonBuilder().create()
    val gen = object : GeneticAlgorithm<ListBuysAI>() {
        val COUNT = Hero.names.size * 2
        val dashboard = mutableMapOf<ListBuysAI, Double>()

        fun addInDashboard(ai: ListBuysAI){
            dashboard[ai] = 0.0
            //println("add in dash ${ai.id} all:${dashboard.size}")
            for (ai1 in dashboard.keys){
                if (ai == ai1)
                    continue

                val battle = Battle(Hero("#_${ai.id}"), Hero("#_${ai1.id}"))
                battle.nextGame(ai, ai1)
                if (battle.leftWins == 5 && battle.rightWins == 0){
                    dashboard[ai] = dashboard[ai]!! + 1.0
                } else if (battle.leftWins > battle.rightWins){
                    dashboard[ai] = dashboard[ai]!! + 0.9
                    dashboard[ai1] = dashboard[ai1]!! + 0.1
                } else if (battle.leftWins == battle.rightWins){
                    dashboard[ai] = dashboard[ai]!! + 0.5
                    dashboard[ai1] = dashboard[ai1]!! + 0.5
                } else {
                    dashboard[ai1] = dashboard[ai1]!! + 1.0
                }
                // println(dashboard)
            }
        }

        override fun create(): ListBuysAI {
            val ai = ListBuysAI(COUNT)
            addInDashboard(ai)
            return ai
        }

        override fun merge(first: ListBuysAI, second: ListBuysAI): ListBuysAI {
            val ai = ListBuysAI(COUNT)
            for (i in 0 until first.array.size) {
                ai.array[i] = if (Random.nextBoolean()) first.array[i] else second.array[i]
            }
            addInDashboard(ai)
            return ai
        }

        override fun appraisal(chromosomes: List<ListBuysAI>): List<Double> {
            return chromosomes.map { dashboard[it] ?: 0.0 }
        }

        override fun mutation(chr: ListBuysAI): ListBuysAI {
            val ai = ListBuysAI(COUNT)
            ai.array = chr.array.copyOf(chr.array.size) as Array<Int>
            ai.array[Random.nextInt(ai.array.size)] = Random.nextInt(COUNT)
            addInDashboard(ai)
            return ai
        }
    }
    val writer = PrintWriter("test-log-1.txt")
    for( i in 1..5000) {
        writer.println("learn $i")
        gen.learOne()
        val top = gen.chromosomes!!.first()
        top.iter = 0
        top.buys(Hero("#_${top.id}"))
        writer.println(top.listBuys + " raiting " + gen.dashboard[top] + "count chromes ${gen.chromosomes!!.size}")
        gen.dashboard.clear()
        for (ai in gen.chromosomes!!) {
            gen.addInDashboard(ai)
        }
        writer.println(gson.toJson(gen.chromosomes!!.map { it.id to it.array }))
        writer.flush()
    }
    writer.close()



}

