package ru.lytvest.aeon.heroes

import ru.lytvest.aeon.Attack
import ru.lytvest.aeon.Item
import ru.lytvest.aeon.Method
import ru.lytvest.aeon.removeOpt
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random


open class Hero() {

    var username: String = this::class.simpleName ?: "no_name"
    var maxHp: Int = 100
    var hp: Double = maxHp.toDouble()
    var damage: Double = 15.0
    var damageFactor: Float = 1f
    var armor: Double = 1.0
    var spell: Double = 0.0
    var crit: Double = 1.5
    var critChance: Double = 0.0
    var inc: Double = 0.0
    var regen: Double = 1.0
    var shield: Double = 0.0
    lateinit var enemy: Hero

    lateinit var fieldsGet: LinkedHashMap<String, () -> Double>
    lateinit var fieldsSet: LinkedHashMap<String, (Double) -> Unit>

    var money: Double = 100.0
    var random = Random(32)

    val shop = linkedMapOf<String, Item>()

    constructor(name: String) : this() {
        this.username = name
    }
    private val criticalFun: Method = {
        if (critChance < 1.0) {
            critChance = min(critChance + it, 1.0)
            true
        } else
            false
    }
    private val hpFun: Method = { maxHp += it.toInt(); hp += it.toInt(); true }

    init {
        fieldsGetAndSetFill()
        shopPut("hp", 22, 10, hpFun)
        shopPut("damage", 3, 7)
        shopPut("armor", 2, 4)
        shopPut("spell", 7, 15)
        shopPut("regen", 5, 11)
        shopPut("critChance", 0.05, 15.0, criticalFun)
        shopPut("crit", 0.5, 50.0)
        shopPut("inc", 0.02, 13.0)
        shopPut("shield", currentShieldAdd(SHIELD_STEP), 30.0) { shieldFun(SHIELD_STEP) }
    }



    open fun initShopOpt() {
        shopPut("opt-hp", 220, 87, hpFun)
        shopPut("opt-damage", 60, 120)
        shopPut("opt-spell", 46, 90)
        shopPut("opt-armor", 80, 130)
        shopPut("opt-regen", 62, 115)
        shopPut("opt-inc", 0.2, 120.0)
        shopPut("opt-critChance", 0.4, 104.0)
        shopPut("opt-crit", 1.2, 105.0)
        shopPut("opt-shield", currentShieldAdd(OPT_SHIELD_STEP), 120.0) { shieldFun(OPT_SHIELD_STEP) }

    }

    open fun startBattle(enemy: Hero, numberBattle: Int) {
        this.enemy = enemy
    }

    open fun startCourse(numberCourse: Int) {}

    open fun calcAttack(): Attack {

        return Attack(damage, spell, damageFactor.toDouble(), random.nextDouble() <= critChance, crit)
    }

    open fun calcBlockedDamage(enemyAttack: Attack): Double {
        if (enemyAttack.damage <= armor){
            return enemyAttack.physical()
        }
        return min(armor + enemyAttack.physical() * shield, enemyAttack.physical())
    }

    open fun minusHp(minus: Double) {
        hp -= minus
        hp = max(0.0, hp)
    }

    open fun endCourse() {
        if(hp > 0)
            regeneration()
        incomingDamage()
    }

    open fun incomingDamage() {
        damageFactor += inc.toFloat()
    }

    open fun endBattle() {
        hp = maxHp.toDouble()
        money += 100
        damageFactor = 1f
    }

    open fun regeneration() {
        hp = min(maxHp.toDouble(), hp + regen)
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
//                println("add get ${elem.name}")
                val nameMethod = removeGetFromName(elem.name)
                fieldsGet[nameMethod] = { elem.invoke(this) as Double }
                val setter = this::class.java.getMethod(addSetForName(nameMethod), Double::class.java)
                fieldsSet[removeGetFromName(elem.name)] = { setter.invoke(this, it) }
            }
        }
    }



    fun shopPut(name: String, add: Double, cost: Double, method: Method? = null) {
        shop[name] = Item(cost, add, method ?: { fieldsSet[name.removeOpt()]!!.invoke(fieldsGet[name.removeOpt()]!!.invoke() + add); true })
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


    open fun buy(name: String): Boolean {
        val (cost, add, method) = shop[name] ?: return false
        if (money < cost)
            return false

        if (!method(add))
            return false

        money -= cost
        successBuy(name, cost, add)
        return true
    }

    open fun successBuy(name: String, cost: Double, add: Double) {}


    var shieldStep: Float = 0.0f
    fun shieldFun(step: Float): Boolean {
        if (shieldStep >= 1.0f)
            return false
        shieldStep = min(shieldStep + step, 1.0f)
        shield = calculateShield(shieldStep.toDouble())
        val item = shop["shield"] ?: return true
        shopPut("shield", currentShieldAdd(SHIELD_STEP), item.cost, item.method)
        val itemOpt = shop["opt-shield"] ?: return true
        shopPut("opt-shield", currentShieldAdd(OPT_SHIELD_STEP), itemOpt.cost, itemOpt.method)
        return true
    }
    fun currentShieldAdd(step: Float): Double {
        return calculateShield(shieldStep.toDouble() + step) - calculateShield(shieldStep.toDouble())
    }

    companion object {
        const val SHIELD_STEP = 0.05f
        const val OPT_SHIELD_STEP = 0.2f
        fun calculateShield(x: Double): Double {
            return ((2 - 4 * 0.7) * x * x + (4 * 0.7 - 1) * x) * 0.99;
        }

        val names: MutableMap<String, String> = mutableMapOf(
                "hp" to "Здоровье",
                "damage" to "Урон оружием",
                "spell" to "Урон магией",
                "critChance" to "Шанс критической атаки",
                "crit" to "Критический урон",
                "regen" to "Регенерация",
                "inc" to "Ярость",
                "armor" to "Броня",
                "shield" to "Щит",
                "money" to "Деньги",
        )
        val withPercent: MutableSet<String> = mutableSetOf("crit", "critChance", "inc", "shield")

        fun byClass(nameClass: String, username: String = "no_name"): Hero {
            val hero = Class.forName("ru.lytvest.aeon.heroes.$nameClass").newInstance() as Hero
            hero.username = username
            return hero
        }

    }
}

fun String.isOpt(): Boolean = this.startsWith("opt-")
