package ru.lytvest.aeon

data class Course(
    val you: MutableMap<String, Double> = mutableMapOf<String, Double>(),
    val enemy: MutableMap<String, Double> = mutableMapOf<String, Double>()
) {
    override fun toString(): String {
        return buildString {
            append("Left: | ")
            for((name, c) in you)
                append(name).append(" : ").append(c).append(" | ")
            append("\n")
            append("Right: | ")
            for((name, c) in enemy)
                append(name).append(" : ").append(c).append(" | ")
            append("\n")
        }
    }

    fun flip(): Course {
        return Course(enemy, you)
    }
}