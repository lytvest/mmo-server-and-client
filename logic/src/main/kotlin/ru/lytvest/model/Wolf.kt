package ru.lytvest.model

class Wolf(world: World) : Animal(world) {
    override fun eat(item: Item): Boolean {
        return item.find(Meat::class)?.let { meat ->
            if (meat.hp > 30){
                meat.hp -= 30
                hp += 30
            } else {
                hp += meat.hp
                meat.hp = 0
                world.remove(meat)
            }
            true
        } ?: false
    }

    override fun death() {
        world.remove(this)
        world.put(this, Meat(world, 50))
    }
}
