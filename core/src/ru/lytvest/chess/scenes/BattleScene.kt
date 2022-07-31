package ru.lytvest.chess.scenes

import ru.lytvest.chess.actors.HbBar
import ru.lytvest.chess.actors.HeroActor

class BattleScene(val leftNameHero: String = "wolf", val rightNameHero: String = "cow") : Scene() {

    val leftBar = HbBar()
    val rightBar = HbBar(false)

    val leftHeroActor = HeroActor(leftNameHero)
    val rightHeroActor = HeroActor(rightNameHero, false)

    init {
        add { leftBar }
        add { rightBar }
        add { leftHeroActor }
        add { rightHeroActor }

        setSizes()

    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        setSizes()
    }

    private fun setSizes() {
        leftBar.setBounds(5f, height() - 105f, 50.w(), 100f)
        rightBar.setBounds(70.w() - 5f, height() - 105f, 50.w(), 100f)
        val size = 40.h()
        leftHeroActor.setBounds(30f, 30f, size, size)
        rightHeroActor.setBounds(width() - size - 30f, 30f, size, size)
    }


}