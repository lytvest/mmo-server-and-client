package ru.lytvest.aeon

data class Course(
    val you: MutableMap<String, Double> = mutableMapOf(),
    val enemy: MutableMap<String, Double> = mutableMapOf()
) {
    override fun toString(): String {
        return buildString {
            append("You: | ")
            for((name, c) in you)
                append(name).append(" : ").append(c).append(" | ")
            append("\n")
            append("Enemy: | ")
            for((name, c) in enemy)
                append(name).append(" : ").append(c).append(" | ")
            append("\n")
        }
    }

    fun flip(): Course {
        return Course(enemy, you)
    }
}
