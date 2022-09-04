package ru.lytvest.model

import com.google.gson.GsonBuilder
import ru.lytvest.aeon.Player
import ru.lytvest.learn.PlayerGsonAdapter
import java.io.FileWriter
import kotlin.random.Random
import kotlin.reflect.KClass






class WorldImpl : World(){

    override var width = 20
    override var height = 20

    var arr = Array<Array<Item>>(width) { Array(height) { Item() } }

    override operator fun get(position: Position): Item {
        return arr[position.x][position.y]
    }
    override operator fun get(x: Int, y: Int): Item {
        return arr[x][y]
    }

    override fun move(entity: Entity, x: Int, y: Int): Boolean {
        if(!arr[x][y].contain(Block::class)){
            arr[x][y] += entity
            arr[entity.x][entity.y] -= entity
            entity.x = x
            entity.y = y
            return true
        }
        return false
    }

    override fun move(entity: Entity, position: Position): Boolean {
        return move(entity, position.x, position.y)
    }

    override fun update(){
        arr.flatten().flatten().forEach{ it.update() }
    }

    fun printWorld() {
        for (y in 0 until height) {
            for (x in 0 until width) {
                print(buildString {
                    for(item in get(x, y)){
                        append(item)
                    }
                    while (length < 10){
                        append(' ')
                    }
                    append(' ')
                })
            }
            println()
        }
    }
    override fun put(p: Position, newEntity: Entity){
        arr[p.x][p.y].add(newEntity)
        newEntity.x = p.x
        newEntity.y = p.y
    }

    fun <T : Any> removeAll(p: Position, clazz: KClass<T>){
        get(p).removeAll(clazz)
    }

    fun <T : Entity> removeAll(e: T) {
        get(e).removeAll(e::class)
    }

    override fun <T : Entity> remove(e: T) {
        get(e).remove(e)
    }

    override fun save(file: String) {
        val builder = GsonBuilder()
        builder.setPrettyPrinting()
        builder.registerTypeAdapter(Entity::class.java, EntityGsonAdapter())
        val gson = builder.create()
        val writer = FileWriter("$file.json")

        gson.toJson(ListEntity(arr.flatten().flatten()), ListEntity::class.java, writer)
        writer.flush()
    }

    override fun load(file: String) {

    }


    fun fillWorld() {
        val grassV = 0.4f
        val cowV = 0.1f
        for (y in 0 until height) {
            for (x in 0 until width) {
                val pos = Position(this, x, y)
                if (Random.nextFloat() < grassV){
                    put(pos, Grass(this))
                }
                if (Random.nextFloat() < cowV){
                    put(pos, Cow(this))
                }
            }
        }
    }
}

class ListEntity(list: List<Entity>) : ArrayList<Entity>(list)

fun main() {
    val world = WorldImpl()

    world.fillWorld()

    for (i in 1..10) {
        world.printWorld()
        println("____________________________________________________________________________________________________________________")
        world.update()
    }

    world.save("worlds/file1")
}
