package ru.lytvest.aeon

import java.lang.reflect.Type
import kotlin.math.max
import kotlin.math.min



open class Hero {

    var name: String = this::class.simpleName ?: "no_name"
    var maxHp: Float = 100f
    var hp: Float = maxHp
    var attack: Float = 15f
    var block: Float = 1f
    lateinit var enemy: Hero

    var money: Float = 100f

    val shop = mutableMapOf<String, Item>()

    init {
        shop["hp"] = Item(10, 22) { maxHp += it; hp += it }
        shop["attack"] = Item(7, 3)
        shop["block"] = Item(4, 2)
    }

    open fun startBattle(enemy: Hero){
        this.enemy = enemy
    }

    open fun startCourse() {}
    
    open fun calcAttack(): Float {
        return attack
    }

    open fun calcBlock(enemyAttack: Float): Float {
        return min(block, enemyAttack)
    }
    
    open fun minusHp(minus: Float){
        hp -= minus
        hp = max(0f, hp)
    }
    
    open fun endCourse() {
        
    }
    
    open fun endBattle() {
        hp = maxHp
        money += 100
    }

    open fun toArray(): ArrayList<Float> {
        return arrayListOf(maxHp, hp, attack, block)
    }

    fun removeGetFromName(name:String) : String{
        return name[3].lowercase() + name.substring(4)
    }
    fun addGetForName(name_: String): String {
        val name = name_.replace("opt-", "")
        return "get" + name[0].uppercase() + name.substring(1)
    }
    fun addSetForName(name_: String): String {
        val name = name_.replace("opt-", "")
        return "set" + name[0].uppercase() + name.substring(1)
    }

    fun fields() : Map<String, Float> {
        val map = mutableMapOf<String, Float>()
        for(elem in this::class.java.methods) {
            val type = elem.genericReturnType.typeName
            if(type == "float" && elem.name.startsWith("get")){
                map[removeGetFromName(elem.name)] = elem.invoke(this) as Float
            }
        }
        return map
    }



    open fun copyFromArray(array: ArrayList<Float>){
        maxHp = array[0]
        hp = array[1]
        attack = array[2]
        block = array[3]
    }


    fun buy(name: String): Boolean {
        val (cost, add, method) = shop[name] ?: return false
        if (money < cost)
            return false

        money -= cost
        if (method != null)
            method(add)
        else
            methodDefault(name, add)
        return true
    }

    fun methodDefault(name: String, add: Int){
        val value = this::class.java.getMethod(addGetForName(name)).invoke(this) as Float
        this::class.java.getMethod(addSetForName(name), Float::class.java).invoke(this, value + add)
    }


}