package ru.lytvest.model


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

    companion object {
        val width = 100
        val height = 100
    }
}