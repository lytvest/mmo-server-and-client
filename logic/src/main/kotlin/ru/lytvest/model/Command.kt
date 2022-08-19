package ru.lytvest.model

import kotlin.math.abs
import kotlin.random.Random

enum class Ifs {
    NONE,
    ALWAYS,
    LEFT_GRASS_EQ,
    LEFT_GRASS_MORE,
    LEFT_GRASS_LESS,
    IF_FIND_GRASS,
    IF_NO_FIND_GRASS
}

enum class Execute {
    MOVE,
    EAT,
    RUN_AT,
    FIND_GRASS
}

enum class Ways {
    UP, RIGHT, DOWN, LEFT, FIND;

    companion object {
        fun from(x: Int): Ways {
            return values().get(x % values().size)
        }
    }
}

data class Command(var ifs: Ifs, var ifValue: Int, var execute: Execute, var executeValue: Int){

    override fun toString(): String {
        return "$ifs $ifValue $execute ${if (execute == Execute.MOVE) Ways.from(executeValue) else executeValue}"
    }
}

class Commands() {
    var list = arrayOf(Command(Ifs.ALWAYS, 0, Execute.MOVE, 0), Command(Ifs.ALWAYS, 0, Execute.MOVE, 0))
    val alreadyRun = mutableSetOf<Int>()

    fun workAll(world: World, animal: Animal) {

        for (i in list.indices) {
            if (work(i, world, animal)) {
                println("$animal -> ${list[i]}")
                break
            }
        }
        alreadyRun.clear()
    }

    fun cowExecutes() = arrayOf(Execute.MOVE, Execute.EAT, Execute.FIND_GRASS, Execute.RUN_AT)
    fun randomize(animal: Animal, size: Int = list.size) {
        val executes = executes(animal)
        val ifs = Ifs.values()
        list = Array(size) {
            Command(ifs.random(), Random.nextInt(100), executes.random(), Random.nextInt(100))
        }
    }

    fun mutation(animal: Animal) {
        val comm = list.random()

        when(Random.nextInt(4)){
            0 -> comm.ifs = Ifs.values().random()
            1 -> comm.ifValue = Random.nextInt(100)
            2 -> comm.execute = executes(animal).random()
            else -> comm.ifValue = Random.nextInt(100)
        }
    }

    private fun executes(animal: Animal) = when (animal) {
        is Cow -> cowExecutes()
        else -> Execute.values()
    }

    fun work(index: Int, world: World, entity: Animal): Boolean {
        val comm = list[index]

        fun findGrassAndExecute(poss: List<Position>) =
            poss.find { world[it].contain(Grass::class) }?.let { executeCommands(comm, world, entity, index) } ?: false

        val len = comm.ifValue % LEN_FIND

        return when (comm.ifs) {
            Ifs.NONE -> false
            Ifs.ALWAYS -> executeCommands(comm, world, entity, index)
            Ifs.LEFT_GRASS_EQ -> {
                val p = Position(entity.x - len, entity.y)
                findGrassAndExecute(listOf(p))
            }

            Ifs.LEFT_GRASS_MORE -> {
                val poss = List(10 - len) { Position(entity.x - it - len, entity.y) }
                findGrassAndExecute(poss)
            }

            Ifs.LEFT_GRASS_LESS -> {
                val poss = List(len + 1) { Position(entity.x - it, entity.y) }
                findGrassAndExecute(poss)
            }

            Ifs.IF_FIND_GRASS -> {
                entity.find?.let { findGrassAndExecute(listOf(it)) } ?: false
            }

            Ifs.IF_NO_FIND_GRASS -> {
                entity.find?.let { if (world[it].contain(Grass::class)) false else null } ?: executeCommands(
                    comm,
                    world,
                    entity,
                    index
                )
            }

            else -> false
        }

    }


    private fun executeCommands(
        command: Command,
        world: World,
        entity: Animal,
        index: Int
    ) = when (command.execute) {
        Execute.MOVE -> movies(world, entity, command.executeValue)
        Execute.EAT -> cooking(entity, world)
        Execute.RUN_AT -> runAt(index, command.executeValue, world, entity)
        Execute.FIND_GRASS -> findGrass(entity, world)
    }

    private fun findGrass(entity: Animal, world: World): Boolean {
        val all = entity.allInRadiusSorted(LEN_FIND)
        entity.find = null
        all.find { world[it].contain(Grass::class) }?.let { entity.find = it }
        return true
    }

    private fun runAt(index: Int, executeValue: Int, world: World, entity: Animal) =
        if (alreadyRun.contains(index))
            false
        else {
            alreadyRun.add(index)
            work(executeValue % list.size, world, entity)
        }

    private fun cooking(entity: Animal, world: World) = when (entity) {
        is Eat -> {
            val grass = world[entity].find(Grass::class)
            grass?.let { entity.eat(it, world) } ?: false
        }

        else -> false
    }

    private fun movies(world: World, animal: Animal, executeValue: Int):Boolean {
        println("move ${Ways.from(executeValue)} start ${animal.x} ${animal.y} ${world[animal]}")
        val a = when (Ways.from(executeValue)) {
            Ways.UP -> world.move(animal, animal.up())
            Ways.LEFT -> world.move(animal, animal.left())
            Ways.RIGHT -> world.move(animal, animal.right())
            Ways.DOWN -> world.move(animal, animal.down())
            Ways.FIND -> moveToFind(animal, world)
        }
        println("move ${Ways.from(executeValue)} end ${animal.x} ${animal.y} ${world[animal]}")
        return a
    }

    private fun moveToFind(entity: Animal, world: World) = entity.find?.let { p ->
        if (p.x == entity.x && p.y == entity.y) false
        else if (abs(entity.x - p.x) > abs(entity.y - p.y)) {
            if (p.x < entity.x) world.move(entity, entity.left()) else world.move(entity, entity.right())
        } else {
            if (p.y < entity.y) world.move(entity, entity.up()) else world.move(entity, entity.down())
        }
    } ?: false

    override fun toString(): String {
        return list.joinToString("\n    ", "Commands {\n    ", "\n}\n")
    }

    companion object {
        val LEN_FIND = 10
    }
}


fun main(){
    println(Commands().apply { randomize(Cow(), 10) })
}
