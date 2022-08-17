package ru.lytvest.learn

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import ru.lytvest.aeon.Battle
import java.util.Random

class RandomPlayer : PlayerWithAutoBuys() {
    var sid = Random().nextInt()
    @Transient
    var random = Random(sid.toLong())

    override fun autoBuy(items: List<String>): String {
        return items[random.nextInt(items.size)]
    }

    override fun preparation() {
        super.preparation()
        random = Random(sid.toLong())
    }
}

fun main(){
    val pl1 = RandomPlayer()
    val pl2 = RandomPlayer()
    pl1.sid = 10
    pl2.sid = 15
    val gson = GsonBuilder().setPrettyPrinting().create()

    val battle1 = Battle("Hero", "Hero")

    battle1.nextGame(pl1, pl2)
    println(gson.toJson(pl1.random))
    println(gson.toJson(pl2.random))

    println("${battle1.leftWins} ${battle1.rightWins} ${pl1.listBuys} ${pl2.listBuys}" )

    val battle2 = Battle("Hero", "Hero")

    battle2.nextGame(pl2, pl1)
    println(gson.toJson(pl1.random))
    println(gson.toJson(pl2.random))
    println("${battle2.leftWins} ${battle2.rightWins} ${pl1.listBuys} ${pl2.listBuys}" )

}
