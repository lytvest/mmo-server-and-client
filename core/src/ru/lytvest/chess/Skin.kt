package ru.lytvest.chess

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.assets.loaders.SkinLoader
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin

class MySkin : Skin() {

    init {
        add("red", Color.RED)
        add("green", Color.GREEN)
        add("black", Color.BLACK)
        add("white", Color.WHITE)
    }


    override fun <T> get(name: String, type: Class<T>): T {
        if (type == BitmapFont::class.java) {
            val res1 = optional(name, type) as T
            val res2 = optional(name, type) as T ?: (createNewFontStyle(name).font as T)
            if (res1 == null)
                add(name, res2)
            return res2
        }
        if (type == Label.LabelStyle::class.java) {
            val res1 = optional(name, type) as T
            val res2 = optional(name, type) as T ?: (createNewFontStyle(name) as T)
            if (res1 == null)
                add(name, res2)
            return res2
        }
        return super.get(name, type)
    }
    fun createNewFontStyle(name: String): Label.LabelStyle {
        val sp = name.split("-")
        if (sp.size < 2)
            throw Exception("no correct font name or size [$name]")
        val n = sp[0]
        val path = "fonts/$n" + if (n[n.length - 4] == '.') "" else ".ttf"
        val size = sp[1].toInt()
        return Label.LabelStyle(FontGenerator.generate(path, size), if(sp.size == 3) optional(sp[2], Color::class.java) else Color.WHITE)
    }
}

class MySkinLoader(resolver: FileHandleResolver?) : SkinLoader(resolver) {

    override fun loadSync(
        manager: AssetManager?,
        fileName: String?,
        file: FileHandle?,
        parameter: SkinParameter?
    ): MySkin {
        return super.loadSync(manager, fileName, file, parameter) as MySkin
    }

    override fun newSkin(atlas: TextureAtlas?): Skin {
        return MySkin().apply { addRegions(atlas) }
    }
}