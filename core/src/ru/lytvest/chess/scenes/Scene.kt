package ru.lytvest.chess.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.ExtendViewport
import ru.lytvest.chess.log


open class Scene {
    val SIZE = 900f
    val viewport = ExtendViewport(SIZE, SIZE)
    val stage: Stage = Stage(viewport)
    val skin: Skin = SceneManager.skin



    fun Float.pw(act: Actor): Value = Value.percentWidth( this, act)
    fun Float.pw(): Value = Value.percentWidth( this)
    fun Float.ph(act: Actor): Value = Value.percentHeight( this, act)
    fun Float.ph(): Value = Value.percentHeight( this)
    fun Int.w() = width() / 120f * this
    fun Int.h() = height() / 120f * this

    fun width() = viewport.worldWidth
    fun height() = viewport.worldHeight


    open fun show() {
        Gdx.input.inputProcessor = stage
    }

    open fun update(delta: Float) {
        stage.act()
        stage.draw()
    }

    open fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true);
        log("width: ${width()} height: ${height()}")
    }

    fun <T : Actor> add(actor:() -> T) : T{
        val a = actor()
        stage.addActor(a)
        return a
    }

    fun click(clicked: (Actor) -> Unit) = object : ClickListener(){
        override fun clicked(event: InputEvent?, x: Float, y: Float) {
            event?.listenerActor?.let { clicked(it) }
        }
    }
}
