package ru.lytvest.mmo

class Entity : Circle() {
    val name = "entity"
    var speed = 1f
    var dx = 0f
    var dy = 0f


    fun update(delta: Float){
        x += dx * speed * delta
        y += dy * speed * delta
    }
}