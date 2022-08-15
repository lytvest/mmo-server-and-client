package ru.lytvest.learn

import ru.lytvest.aeon.Battle
import ru.lytvest.aeon.Player
import kotlin.random.Random

open class TeacherWithScore(db: PlayerDB) : Teacher(db) {
    
    val players: MutableList<Player> = mutableListOf()
    val numbers = 100

    init {
        if (db.count() < numbers){
            for(i in db.count()..numbers)
                db.save(RandomPlayer())
        }
        for(i in 1..numbers){
            players += db.findById(i.toLong())!!
        }
    }

    fun calcScore(current: Player): Double {
        var score = 0.0
        for (player in players){
            val battle = Battle(current.heroClass, player.heroClass)
            battle.nextGame(current, player)
            if (battle.leftWins == 5 && battle.rightWins == 0){
                score += 1.0
            } else if (battle.leftWins > battle.rightWins){
                score += 0.9
            } else if (battle.leftWins == battle.rightWins){
                score += 0.5
            } else {
                score -= 0.2
            }
        }
//        println("score= $score")
        return score
    }
}
