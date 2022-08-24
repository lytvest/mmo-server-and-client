package ru.lytvest.model

abstract class World {

    open operator fun get(position: Position): Item = get(position.x, position.y)

    abstract open operator fun get(x: Int, y: Int): Item

    abstract fun move(entity: Entity, x: Int, y: Int): Boolean

    open fun move(entity: Entity, position: Position): Boolean = move(entity, position.x, position.y)

    abstract fun update()

    abstract fun put(p: Position, newEntity: Entity)

    abstract fun <T : Entity> remove(e: T)

    abstract fun save(file: String)

    abstract fun load(file: String)

    abstract val width: Int

    abstract val height: Int
}
