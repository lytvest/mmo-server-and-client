package ru.lytvest.aeon

import kotlin.math.max
import kotlin.math.min


open class Hero() {

    var name: String = this::class.simpleName ?: "no_name"
    var maxHp: Double = 100.0
    var hp: Double = maxHp
    var damage: Double = 15.0
    var armor: Double = 1.0
    var spell: Double = 0.0
    var crit: Double = 1.5
    var critChange: Double = 0.0
    var inc: Double = 0.0
    var regen: Double = 1.0
    var shield: Double = 0.0
    lateinit var enemy: Hero

    lateinit var fieldsGet: LinkedHashMap<String, () -> Double>
    lateinit var fieldsSet: LinkedHashMap<String, (Double) -> Unit>

    var money: Double = 100.0

    val shop = linkedMapOf<String, Item>()

    constructor(name: String) : this() {
        this.name = name
    }

    init {
        fieldsGetAndSetFill()
        shopPut("hp", 22, 10) { maxHp += it; hp += it; true }
        shopPut("damage", 3, 7)
        shopPut("armor", 2, 4)
        shopPut("spell", 7, 15)
        shopPut("regen", 5, 11)
        shopPut("critChange", 0.05, 15.0) {
            if (critChange >= 1.0) {
                false
            } else {
                critChange = min(critChange + it, 1.0); true
            }
        }
        shopPut("crit", 0.5, 50.0)
        shopPut("inc", 0.02, 13.0)
    }

    open fun startBattle(enemy: Hero) {
        this.enemy = enemy
    }

    open fun startCourse() {}

    open fun calcAttack(): Attack {
        return Attack(damage, spell)
    }

    open fun calcArmor(enemyAttack: Attack): Double {
        return min(armor, enemyAttack.damage)
    }

    open fun minusHp(minus: Double) {
        hp -= minus
        hp = max(0.0, hp)
    }

    open fun endCourse() {
        if(hp > 0)
            hp = min(maxHp, hp + regen)
    }

    open fun endBattle() {
        hp = maxHp
        money += 100
    }

    open fun toArray(): DoubleArray {
        return toMap().values.toDoubleArray()
    }

    fun removeGetFromName(name: String): String {
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


    private fun fieldsGetAndSetFill() {
        fieldsGet = linkedMapOf()
        fieldsSet = linkedMapOf()
        for (elem in this::class.java.methods) {
            val type = elem.genericReturnType.typeName
            if (type == "double" && elem.name.startsWith("get")) {
                val nameMethod = removeGetFromName(elem.name)
                fieldsGet[nameMethod] = { elem.invoke(this) as Double }
                val setter = this::class.java.getMethod(addSetForName(nameMethod), Double::class.java)
                fieldsSet[removeGetFromName(elem.name)] = { setter.invoke(this, it) }
            }
        }
    }

    fun shopPut(name: String, add: Double, cost: Double, method: Method? = null) {
        shop[name] = Item(cost, add, method ?: { fieldsSet[name]!!.invoke(fieldsGet[name]!!.invoke() + add); true })
    }

    fun shopPut(name: String, add: Int, cost: Int, method: Method? = null) {
        shopPut(name, add.toDouble(), cost.toDouble(), method)
    }

    fun fields(): Set<String> {
        return fieldsGet.keys
    }

    open fun toMap(): Map<String, Double> {
        return fieldsGet.map { it.key to it.value() }.toMap()
    }


    fun buy(name: String): Boolean {
        val (cost, add, method) = shop[name] ?: return false
        if (money < cost)
            return false

        if (!method(add))
            return false

        money -= cost
        return true
    }

    fun methodDefault(name: String, add: Int) {
        val value = this::class.java.getMethod(addGetForName(name)).invoke(this) as Double
        this::class.java.getMethod(addSetForName(name), Double::class.java).invoke(this, value + add)
    }


}