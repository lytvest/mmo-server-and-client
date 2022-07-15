package ru.lytvest.mmo

import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random

class World(val width: Float = 100f, val height: Float = 100f) {
    constructor(entities: List<Entity>) : this(){
        for (entity in entities) {
            add(entity)
        }
    }

    val map = ConcurrentHashMap<Point, Entity>(20)


    fun createRandom(){
        fun entity() = Entity().apply {
            x = Random.nextFloat() * width
            y = Random.nextFloat() * height
            r = Random.nextFloat() * 2f
            dx = (Random.nextFloat() - 0.5f) * 2f
            dy = (Random.nextFloat() - 0.5f) * 2f
        }
        for(i in 1..100){
            add(entity())
        }
    }

    fun update(delta: Float){
        for (old in map.keys.toList()) {
            val entity = map[old]!!
            entity.update(delta)
            val next = entity.position()
            if (old != next){
                move(old, next, entity)
            }
        }
    }
    fun add(entity: Entity){
        map[entity.position()] = entity
    }

    fun move(old:Point, next:Point, entity: Entity){
        map.remove(old)
        if (next.x >= width || next.x < 0f || next.y >= height || next.y < 0f)
            return
        map[next] = entity
    }

    fun entities() = map.values.toList()
}