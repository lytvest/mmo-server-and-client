package ru.lytvest.learn

import kotlin.random.Random

class TeacherRandomPlayer(db: PlayerDB) : TeacherWithScore(db) {
    val countIterations = 30
    override fun teachOne(id: Long) {
        val player = db.findById(id) as RandomPlayer
        var currSid = player.sid
        var currScore = calcScore(player)

        for(i in 1..countIterations){
            player.sid = Random.nextInt()
            val score = calcScore(player)
            if (score > currScore){
                currScore = score
                currSid = player.sid
//                println("new score $score [$id] sid=${player.sid} ")
            }
            if (score >= 100)
                break
        }
        player.sid = currSid
        db.save(player)
    }
}

fun main() {
    val db = FilesPlayerDB()
    TeacherWithScore(db).apply {
        for (i in 1..100) {
            print("#$i :" + (calcScore(db.findById(i.toLong())!!)* 10).toInt() / 10.0)
        }
    }
    for(j in 1..1000) {
        println("\n\n -------------- Эпоха номер $j ------------- ")
        for (i in 1..100) {
            val teacher = TeacherRandomPlayer(db)
            teacher.teachOne(i.toLong())
            print(".")
        }
        println()

        TeacherWithScore(db).apply {
            for (i in 1..100) {
                print("#$i :" + (calcScore(db.findById(i.toLong())!!)* 10).toInt() / 10.0)
            }
            println()
        }
        TeacherWithScore(db).apply {
            players.clear()
            for(i in 1..100){
                players.add(RandomPlayer())
            }
            println("random scores:")
            for (i in 1..100) {
                print(" #$i :" + (calcScore(db.findById(i.toLong())!!)* 10).toInt() / 10.0)
            }
            println()
        }

    }

}
