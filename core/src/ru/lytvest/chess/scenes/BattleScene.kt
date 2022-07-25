package ru.lytvest.chess.scenes

import ru.lytvest.chess.actors.HbBar
import ru.lytvest.chess.actors.HeroActor

class BattleScene(val leftNameHero: String = "Hero", val rightNameHero: String = "Hero") : Scene() {

    val leftBar = HbBar()
    val rightBar = HbBar(false)

    val leftHeroActor = HeroActor(leftNameHero)
    val rightHeroActor = HeroActor(rightNameHero, false)

    init {
        add { leftBar }
        add { rightBar }
        add { leftHeroActor }
        add { rightHeroActor }

        leftBar.setBounds(5f, height() - 105f, width() * 0.4f, 100f)
        rightBar.setBounds(width() * 0.6f - 5f, height() - 105f, width() * 0.4f, 100f)
        val size = height() * 0.3f
        leftHeroActor.setBounds(30f,30f, size, size)
        rightHeroActor.setBounds(width() - size - 30f, 30f, size, size)

    }


}