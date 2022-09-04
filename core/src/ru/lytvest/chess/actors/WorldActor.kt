package ru.lytvest.chess.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import ru.lytvest.chess.scenes.SceneManager
import ru.lytvest.model.*
import kotlin.math.abs


class WorldActor(world: World) : Group() {

    val skin = SceneManager.skin
    val cow = skin.getRegion("cow")
    var sizeBlock = 100f

    init {
        setSize(world.width * sizeBlock, world.height * sizeBlock)
    }

    val grasses = mutableMapOf<Position, GrassActor>()
    val cows = mutableMapOf<Int, CowActor>()
    val setCows = mutableSetOf<Int>()

    val first = Actor()
    val second = Actor()
    fun initial() {
        addActor(first)
        addActor(second)
    }

    fun update(world: World) {
        setCows.clear()

        for (x in 0 until world.width) {
            for (y in 0 until world.height) {
                val pos = Position(world, x, y)
                val item = world[pos]
                updateGrass(item, pos)
                updateCows(item, pos)
            }
        }

        for (i in cows.keys.toList()) {
            if (!setCows.contains(i)) {
                cows[i]?.let {
                    removeActor(it)
                }
                cows.remove(i)
            }
        }
    }

    private fun updateCows(item: Item, pos: Position) {

        item.find(Cow::class)?.let { cow ->
            setCows.add(cow.id)
            if (cows.containsKey(cow.id)) {
                cows[cow.id]?.apply {
                    if ((xx != cow.x || yy != cow.y) && (abs(cow.x - xx) + abs(cow.y - yy) < 3)) {
                        addAction(actionMove(cow.x * sizeBlock, cow.y * sizeBlock))
                    }
                    update(cow)
                }
            } else {
                addActorAfter(second, CowActor().apply {
                    setPosition(pos.x * sizeBlock, pos.y * sizeBlock)
                    setSize(sizeBlock, sizeBlock)
                    update(cow)
                    cows[cow.id] = this
                })
            }
        }
    }

    private fun actionMove(x: Float, y: Float): MoveToAction {
        return MoveToAction().apply {
            setPosition(x, y)
            duration = 1f
            interpolation = Interpolation.pow2In
        }
    }

    private fun updateGrass(item: Item, pos: Position) {
        val grass = item.find(Grass::class)
        if (grass != null) {
            if (grasses.containsKey(pos)) {
                grasses[pos]!!.update(grass)
            } else {
                addActorAt(0, GrassActor().apply {
                    setPosition(pos.x * sizeBlock, pos.y * sizeBlock)
                    setSize(sizeBlock, sizeBlock)
                    update(grass)
                    grasses[pos] = this
                })
            }
        } else {
            grasses.remove(pos)?.let {
                removeActor(it)
            }
        }

    }

    fun addSize(world: World) {
        sizeBlock += 5f
        cows.clear()
        grasses.clear()
        clearChildren()
        initial()
        println(world.width * sizeBlock)
        setSize(world.width * sizeBlock, world.height * sizeBlock)
    }

    fun minusSize(world: World) {
        if (sizeBlock > 10f) {
            sizeBlock -= 5f
            cows.clear()
            grasses.clear()
            clearChildren()
            initial()
            println(world.width * sizeBlock)
            setSize(world.width * sizeBlock, world.height * sizeBlock)

        }
    }
}