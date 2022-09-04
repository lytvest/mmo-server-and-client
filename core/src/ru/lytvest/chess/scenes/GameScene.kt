package ru.lytvest.chess.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import ru.lytvest.chess.actors.CowActor
import ru.lytvest.chess.actors.GrassActor
import ru.lytvest.chess.actors.WorldActor
import ru.lytvest.model.Grass
import ru.lytvest.model.World
import ru.lytvest.model.WorldImpl
import java.util.concurrent.atomic.AtomicBoolean

class GameScene : Scene() {

    val world: World
    val worldActor : WorldActor
    var pane: ScrollPane

    init {
        world = WorldImpl().apply {
            fillWorld()
        }
        worldActor = WorldActor(world)
        pane = ScrollPane(worldActor)
        pane.setSize(stage.width, stage.height)
        stage.addActor(pane)
        worldActor.update(world)

        stage.addActor(Button(skin, "plus").let {
            it.clicked {
                worldActor.addSize(world)
                pane.remove()
                pane = ScrollPane(worldActor)
                pane.setSize(stage.width, stage.height)
                stage.root.addActorAt(0, pane)
            }
            it.setSize(80f, 80f)
            it.setPosition(10f, 10f)
            it
        })
        stage.addActor(Button(skin, "minus").let {
            it.clicked {
                worldActor.minusSize(world)
                pane.remove()
                println(worldActor.width)
                pane = ScrollPane(worldActor)
                pane.setSize(stage.width, stage.height)
                stage.root.addActorAt(0, pane)
            }
            it.setSize(80f, 80f)
            it.setPosition(100f, 10f)
            it
        })

        val thread = object : Thread() {
            val check : AtomicBoolean = AtomicBoolean(true)
            override fun run() {
                while (!isInterrupted){

                    sleep(1000)

                    while (!check.get()) sleep(10)

                    check.set(false)
                    world.update()
                    Gdx.app.postRunnable {
                        worldActor.update(world)
                        check.set(true)
                    }
                }
            }
        }
        thread.start()

    }


}

fun Actor.clicked(f: () -> Unit) {
    this.addListener(object : ClickListener() {
        override fun clicked(event: InputEvent?, x: Float, y: Float) {
            f()
        }
    } )
}