package ru.lytvest.model

import kotlin.reflect.KClass

class Item : ArrayList<Entity>(4) {

    fun <T : Any> find(clazz: KClass<T>):T? {
        for(item in this){
            if(clazz.isInstance(item))
                return item as T
        }
        return null
    }

    fun <T : Any> removeAll(clazz: KClass<T>) {
        removeIf {
            clazz.isInstance(it)
        }
    }

    fun <T : Any> contain(clazz: KClass<T>):Boolean {
        return find(clazz) != null
    }
}
