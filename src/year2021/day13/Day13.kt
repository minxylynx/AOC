package year2021.day13

import readInput
import kotlin.system.measureTimeMillis

//  https://adventofcode.com/2021/day/13

data class Coord(var x: Int, var y: Int)

fun fold(fold: Pair<String, Int>, map: MutableList<Coord>) =
    if (fold.first == "y") {
        map.filter { it.y > fold.second }
            .map { it.apply { this.y = it.y - ((it.y - fold.second) * 2) } }
            .dropWhile { it.y < 0 }
    } else {
        map.filter { it.x > fold.second }
            .map { it.apply { this.x = it.x - ((it.x - fold.second) * 2) } }
            .dropWhile { it.x < 0 }
    }.let { map }

fun main() {
    fun setup(dots: List<String>, folds: List<String>): Pair<MutableList<Coord>, List<Pair<String, Int>>> {
        val coords =
            dots.map { dot -> dot.split(",").let { Coord(it.first().toInt(), it.last().toInt()) } }
                .toMutableList()
        val foldPairs =
            folds.map { fold -> fold.split("=").let { Pair(it.first().last().toString(), it.last().toInt()) } }
        return coords to foldPairs
    }

    fun part1(dots: List<String>, folds: List<String>): Int {
        val (coords, foldPairs) = setup(dots, folds)
        return fold(foldPairs.first(), coords).distinct().size
    }

    fun part2(dots: List<String>, folds: List<String>): Int {
        var (coords, foldPairs) = setup(dots, folds)
        foldPairs.forEach { coords = fold(it, coords).distinct().toMutableList() }
        coords.printMap()
        return 0
    }

    val dots = readInput(2021, "13", "_dots")
    val folds = readInput(2021, "13", "_folds")
    measureTimeMillis { println(part1(dots, folds)) }.let { println("part1 time to completion: $it ms") }
    measureTimeMillis { println(part2(dots, folds)) }.let { println("part2 time to completion: $it ms") }
}

fun  MutableList<Coord>.printMap() {
    val sorted = this.sortedWith(compareBy({ it.y }, { it.x }))
    val maxY = sorted.maxOf { it.x }
    sorted.groupBy({ it.y }, { it.x }).values.forEach { ys ->
        val array = Array(maxY + 1) { " " }
        ys.forEach { array[it] = "X" }
        println(array.joinToString(""))
    }
}
