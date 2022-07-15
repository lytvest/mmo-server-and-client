package ru.lytvest.chess.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.ExtendViewport


open class Scene {

    val stage: Stage = Stage(ExtendViewport(16 * 100f,9 * 100f))
    val skin: Skin = SceneManager.skin



    fun Float.pw(act: Actor): Value = Value.percentWidth( this, act)
    fun Float.pw(): Value = Value.percentWidth( this)
    fun Float.ph(act: Actor): Value = Value.percentHeight( this, act)
    fun Float.ph(): Value = Value.percentHeight( this)
    fun Int.mw(act: Actor): Value = Value.percentWidth( this / 120.0f, act)
    fun Int.mw(): Value = Value.percentWidth( this / 120.0f)
    fun Int.mh(act: Actor): Value = Value.percentHeight( this / 120.0f, act)
    fun Int.mh(): Value = Value.percentHeight( this / 120.0f)

    fun width() = Gdx.graphics.width.toFloat()
    fun height() = Gdx.graphics.height.toFloat()


    open fun show() {
        Gdx.input.inputProcessor = stage
    }

    open fun update(delta: Float) {
        stage.act()
        stage.draw()
    }

    open fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true);
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
