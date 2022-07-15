package ru.lytvest.chess.scenes

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ru.lytvest.mmo.World
import ru.lytvest.world.WorldUpdater

class WorldScene : Scene() {

    val batch = stage.batch
    val img = skin.getRegion("settings")

    var world = World()
    val camera = stage.camera

    val updater = WorldUpdater { world = it }

    fun worldRender(){
        batch.begin()
        for (entity in world.entities()) {
            batch.draw(img, entity.left(), entity.down(), entity.width(), entity.height())
        }
        batch.end()
    }

    override fun show() {
        super.show()
        camera.viewportHeight = world.height
        camera.viewportWidth = world.width
        camera.update()
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0f);
    }

    override fun update(delta: Float) {
        world.update(delta)
        worldRender()
        super.update(delta)
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
    }
}