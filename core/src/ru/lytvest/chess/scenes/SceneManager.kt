package ru.lytvest.chess.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import ru.lytvest.chess.MySkin
import ru.lytvest.chess.log

object SceneManager {


    val scenes = mutableListOf<Scene>()
    var skin: Skin = MySkin()

    fun <T : Scene> push(scene: () -> T) {
        scenes.add(scene())
        scenes.last().show()
    }

    fun pop() {
        if (scenes.isNotEmpty())
            scenes.removeLast()

        if (scenes.isNotEmpty())
            scenes.last().show()
    }

    fun <T : Scene> open(scene: () -> T){
        pop()
        push(scene)
    }

    fun update(delta: Float){
        if(scenes.isEmpty()){
            log("Error scenes is empty!")
            return
        }
        scenes.last().update(delta)
    }

    fun resize(width: Int, height: Int) {
        if(scenes.isEmpty()){
            log("Error scenes is empty!")
            return
        }
        scenes.last().resize(width, height)
    }
}