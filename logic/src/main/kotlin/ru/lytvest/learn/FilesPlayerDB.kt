package ru.lytvest.learn

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import ru.lytvest.aeon.Player
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.FilenameFilter
import java.nio.file.Files
import kotlin.random.asJavaRandom

class FilesPlayerDB : PlayerDB{
    private var count = 0
    private val gson: Gson
    private val path = "players"
    init {
        val builder = GsonBuilder()
        builder.setPrettyPrinting()
        builder.registerTypeAdapter(Player::class.java, PlayerGsonAdapter())
        gson = builder.create()
        val dir = File(path)
        val files = dir.list { _, name -> name.endsWith(".json") }!!
        println("load db info ${files.size} players.")
        count = files.size
    }
    override fun findById(id: Long): Player? {
        try {
            return gson.fromJson(FileReader("$path/player_$id.json"), Player::class.java)
        } catch (e: Exception){
            println("file $path/player_$id.json not loaded! : ${e.message}")
        }
        return null
    }

    override fun save(player: Player) {

        if (player.id == 0L) {
            count++
            player.id = count.toLong()
        }
        val writer = FileWriter("$path/player_${player.id}.json")

        gson.toJson(player, Player::class.java, writer)
        writer.flush()
    }

    override fun count(): Int {
        return count
    }
}

fun main(){
    // test files db

    val db = FilesPlayerDB()
    val pl = RandomPlayer()
    pl.preparation()
    println("${pl.id}, ${pl.sid} ${pl.random.nextInt(100)}")

    db.save(pl)

    println("${pl.id}, ${pl.sid}")

    val pl1 = db.findById(pl.id)!! as RandomPlayer
    pl1.preparation()
    println("${pl1.id}, ${pl1.sid} ${pl1.random.nextInt(100)}")

}