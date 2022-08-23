package ru.lytvest.model

class Wolf : Animal() {
    override fun eat(item: Item, world: World): Boolean {
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

    override fun death(world: World) {
        world.remove(this)
        world.put(this, Meat(50))
    }
}
