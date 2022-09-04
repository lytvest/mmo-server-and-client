package ru.lytvest.chess.actors

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ru.lytvest.chess.scenes.SceneManager
import ru.lytvest.model.Grass

class GrassActor : Group() {

    val image: Image = Image(SceneManager.skin, "grass")

    init {
        addActor(image)
    }

    fun update(grass: Grass) {
        val coef = grass.hp.toFloat() / Grass.MAX_HP
        image.setSize(width, height * coef)
    }

}