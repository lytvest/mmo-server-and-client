package ru.lytvest.model

import kotlin.random.Random


open class Block : Entity()



class World {

    val arr = Array<Array<Item>>(width) { Array(height) { Item() } }

    operator fun get(position: Position): Item {
        return arr[position.x][position.y]
    }
    operator fun get(x: Int, y: Int): Item {
        return arr[x][y]
    }

    fun move(entity: Entity, x: Int, y: Int): Boolean {
        if(!arr[x][y].contain(Block::class)){
            arr[x][y] += entity
            arr[entity.x][entity.y] -= entity
            entity.x = x
            entity.y = y
            return true
        }
        return false
    }

    fun move(entity: Entity, position: Position): Boolean {
        return move(entity, position.x, position.y)
    }

    fun update(){
        for (y in 0 until height) {
            for (x in 0 until width) {
                for(item in get(x, y).toList()){
                    item.update(this)
                }
            }
        }
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
    fun put(p: Position, newEntity: Entity){
        arr[p.x][p.y].add(newEntity)
        newEntity.x = p.x
        newEntity.y = p.y
    }

    fun fillWorld() {
        val grassV = 0.4f
        val cowV = 0.1f
        for (y in 0 until height) {
            for (x in 0 until width) {
                val pos = Position(x, y)
                if (Random.nextFloat() < grassV){
                    put(pos, Grass())
                }
                if (Random.nextFloat() < cowV){
                    put(pos, Cow())
                }
            }
        }
    }

    companion object {
        val width = 7
        val height = 7
    }
}

fun main() {
    val world = World()

    world.fillWorld()

    for (i in 1..10) {
        world.printWorld()
        println("____________________________________________________________________________________________________________________")
        world.update()
    }
}
