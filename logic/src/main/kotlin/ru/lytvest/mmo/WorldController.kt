package ru.lytvest.mmo

class WorldController {
    val world = World().apply { createRandom() }
    var oldTime = -1L
    var startedTime = -1L

    fun update(){
        val time = System.currentTimeMillis()
        if(oldTime == -1L) {
            startedTime = time
            oldTime = time
            return
        }
        val delta = (time - oldTime) / 1000f
        world.update(delta)
        oldTime = time
    }

    fun toWrite() = WorldInfo(startedTime, oldTime, world.entities())

}