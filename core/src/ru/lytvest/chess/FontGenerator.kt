package ru.lytvest.chess

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator

object FontGenerator {

    private val generators = hashMapOf<String, FreeTypeFontGenerator>()

    private val parameters = FreeTypeFontGenerator.FreeTypeFontParameter().apply {
        characters = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя1234567890.,:;_!?\"'+-*/()[]={}ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
        incremental = true
    }

    fun generate(path: String, fontSize: Int): BitmapFont {
        if (!generators.containsKey(path))
            generators[path] = FreeTypeFontGenerator(Gdx.files.internal(path))

        return generators[path]!!.generateFont(parameters.apply { size = fontSize })
    }

}