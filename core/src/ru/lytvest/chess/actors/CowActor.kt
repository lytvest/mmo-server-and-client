package ru.lytvest.chess.actors

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import ru.lytvest.chess.scenes.SceneManager
import ru.lytvest.model.Cow
import ru.lytvest.model.Grass

class CowActor : Group() {

    val image: Image = Image(SceneManager.skin, "cow")
    val hp = Label("0hp", SceneManager.skin, "hp")
    var xx: Int = 0
    var yy: Int = 0

    init {
        addActor(image)
        addActor(hp)
    }

    fun update(cow: Cow) {
        xx = cow.x
        yy = cow.y
        hp.setText(cow.hp)
        hp.setPosition(width - hp.prefWidth, height - hp.prefHeight)
    }

    override fun sizeChanged() {
        super.sizeChanged()
        image.setSize(width, height)
    }
}