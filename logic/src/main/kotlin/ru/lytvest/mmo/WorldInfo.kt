package ru.lytvest.mmo

class WorldInfo(var startTime: Long = 0, var currTime: Long = 0, var entities: List<Entity> = listOf()){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WorldInfo

        if (startTime != other.startTime) return false
        if (currTime != other.currTime) return false
        if (entities != other.entities) return false

        return true
    }

    override fun hashCode(): Int {
        var result = startTime.hashCode()
        result = 31 * result + currTime.hashCode()
        result = 31 * result + entities.hashCode()
        return result
    }

    override fun toString(): String {
        return "WorldInfo(startTime=$startTime, currTime=$currTime, entities=$entities)"
    }
}