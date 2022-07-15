package ru.lytvest.chess

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.ScreenUtils
import ru.lytvest.chess.scenes.LoadingScene
import ru.lytvest.chess.scenes.Scene
import ru.lytvest.chess.scenes.SceneManager
import ru.lytvest.chess.scenes.WorldScene

fun <T : Any> T.log(msg: String) {
    Gdx.app.log(this.javaClass.simpleName, msg)
}

class Starter : ApplicationAdapter() {

    override fun create() {
        SceneManager.push { LoadingScene { WorldScene() } }
        log("started")
    }

    override fun render() {
        ScreenUtils.clear(0.9f, 0.9f,0.9f,1f)
        SceneManager.update(Gdx.graphics.deltaTime)
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        SceneManager.resize(width, height)
    }
}