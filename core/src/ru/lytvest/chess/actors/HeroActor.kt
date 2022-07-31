package ru.lytvest.chess.actors

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ru.lytvest.chess.scenes.SceneManager

class HeroActor(name: String = "Hero", val isLeft: Boolean = true) : Group(){
    val skin get() = SceneManager.skin

    val image = Image(skin, name)

    init {
        this.name = name
        addActor(image)
    }

    override fun sizeChanged() {
        super.sizeChanged()
        image.setSize(width, height)
    }
}