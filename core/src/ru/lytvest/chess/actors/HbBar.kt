package ru.lytvest.chess.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import ru.lytvest.chess.scenes.SceneManager

class HbBar(val isLeft: Boolean = true) : Group() {

    var currentHp: Float = 10f
    var maxHp: Float = 100f

    val skin get() = SceneManager.skin

    val green = Image(skin, "white").apply { color = Color.GREEN }
    val red = Image(skin, "white").apply { color = Color.RED }
    val hpLabel = Label("$currentHp / $maxHp", skin)

    init {
        addActor(red)
        addActor(green)
        addActor(hpLabel)
    }

    fun updateHp(hp: Float, max: Float = maxHp){
        currentHp = hp
        maxHp = max
        hpLabel.setText("${currentHp.toInt()} / ${maxHp.toInt()}")
        sizeChanged()
    }

    private fun percent(): Float = currentHp / maxHp

    override fun sizeChanged() {
        super.sizeChanged()
        red.setSize(width, height)
        if (isLeft) {
            green.setSize(width * percent(), height)
        } else {
            green.setBounds(width * (1f - percent()), 0f, width * percent(), height)
        }
        hpLabel.setPosition(width / 2 - hpLabel.prefWidth / 2, height / 2 - hpLabel.prefHeight / 2)
    }
}