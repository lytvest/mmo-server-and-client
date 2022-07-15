package ru.lytvest.chess.scenes

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import ru.lytvest.chess.MySkinLoader
import kotlin.math.roundToInt

class LoadingScene(val nextScene: () -> Scene) : Scene() {

    val assets = AssetManager()
    val label = add { Label("Download... 0%", Label.LabelStyle(BitmapFont(), Color.BLACK)) }
    init {
        assets.setLoader(Skin::class.java, MySkinLoader(assets.fileHandleResolver))
        assets.load("skin.json", Skin::class.java)
        label.let {
            it.setPosition(width() / 2f - it.prefWidth / 2f, height() / 2f - it.prefWidth / 2f)
        }

    }

    override fun update(delta: Float) {
        if (assets.update()){
            SceneManager.skin = assets.get("skin.json", Skin::class.java)
            SceneManager.open(nextScene)
        } else {
            label.setText("Download... ${(assets.progress * 1000f).roundToInt() / 10f}%")
        }
        super.update(delta)
    }
}