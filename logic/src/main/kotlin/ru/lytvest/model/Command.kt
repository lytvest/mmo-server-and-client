package ru.lytvest.model

enum class Ifs {
    NONE,
    ALWAYS,
    LEFT_GRASS_EQ,
    LEFT_GRASS_MORE,
    LEFT_GRASS_LESS,
}

enum class Execute {
    MOVE,
    EAT,
    RUN_AT,
}

enum class Ways {
    UP, RIGHT, DOWN, LEFT;

    companion object {
        fun from(x: Int): Ways {
            return values().get(x % values().size)
        }
    }
}

data class Command(val ifs: Ifs, val ifValue:Int, val execute: Execute, val executeValue: Int)

class Commands() {
    var list = arrayOf(Command(Ifs.ALWAYS, 0, Execute.MOVE, 0), Command(Ifs.ALWAYS, 0, Execute.MOVE, 0))
    val alreadyRun = mutableSetOf<Int>()

    fun work(index: Int, world: World, entity: Entity): Boolean {
        val arr = list[index]
        return when (arr.ifs) {
            Ifs.NONE -> false
            Ifs.ALWAYS -> executeCommands(arr, world, entity, index)
            Ifs.LEFT_GRASS_EQ -> {
                val p = Position(entity.x - arr.ifValue % LEN_FIND, entity.y)
                if (world[p].contain(Grass::class)) executeCommands(arr, world, entity, index) else false
            }
            else -> false
        }

    }

    private fun executeCommands(
        command: Command,
        world: World,
        entity: Entity,
        index: Int
    ) = when (command.execute) {
        Execute.MOVE -> movies(world, entity, command.executeValue)
        Execute.EAT -> cooking(entity, world)
        Execute.RUN_AT -> runAt(index, command.executeValue, world, entity)
    }

    private fun runAt(index: Int, executeValue: Int, world: World, entity: Entity) =
        if (alreadyRun.contains(index))
            false
        else {
            alreadyRun.add(index)
            work(executeValue % list.size, world, entity)
        }

    private fun cooking(entity: Entity, world: World) = when (entity) {
        is Eat -> {
            val grass = world[entity].find(Grass::class)
            grass?.let { entity.eat(it, world) } ?: false
        }
        else -> false
    }

    private fun movies(world: World, entity: Entity, executeValue: Int) = when (Ways.from(executeValue)) {
        Ways.UP -> world.move(entity, entity.up())
        Ways.LEFT -> world.move(entity, entity.left())
        Ways.RIGHT -> world.move(entity, entity.right())
        Ways.DOWN -> world.move(entity, entity.down())
    }

    companion object {
        val LEN_FIND = 10
    }
}