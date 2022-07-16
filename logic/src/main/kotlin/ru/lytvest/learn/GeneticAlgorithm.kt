package ru.lytvest.learn

import kotlin.random.Random


abstract class GeneticAlgorithm<CHR>(
    val numberChromosomes: Int = 100,
    val percentBest: Double = 0.05,
    val percentMerge: Double = 0.5,
    val percentMutation: Double = 0.4
) {
    var chromosomes: List<CHR>? = null
    var bestResult: Double = Double.MIN_VALUE


    abstract fun create(): CHR

    abstract fun merge(first: CHR, second: CHR): CHR
    abstract fun appraisal(chromosomes: List<CHR>): List<Double>
    abstract fun mutation(chr: CHR): CHR

    fun learOne(){
        if (chromosomes == null)
            chromosomes = List(numberChromosomes){create()}
        val chromes = chromosomes as List<CHR>
        val counted = appraisal(chromes).zip(chromes).sortedBy { -it.first }
        bestResult = counted[0].first
        val sorted = counted.map{ it.second }
        println("best ${sorted.first()} -> $bestResult")
        val bestNumber = (numberChromosomes * percentBest).toInt()
        val best = sorted.slice(0..bestNumber)
        val forMerge = sorted.slice(bestNumber..(bestNumber * 3))
        val mergeNumber = (numberChromosomes * percentMerge).toInt()
        val mutationNumber = (numberChromosomes * percentMutation).toInt()
        val merges = List(mergeNumber){
            merge(best[Random.nextInt(best.size)], forMerge[Random.nextInt(forMerge.size)])
        }
        val mutations = List(mutationNumber){
            mutation(best[Random.nextInt(best.size)])
        }
        val newNumber = numberChromosomes - mergeNumber - mutationNumber - bestNumber
        val creating = List(newNumber){ create() }
        chromosomes = best + merges + mutations + creating
    }
}