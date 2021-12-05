package year2021.day05

import readInput
import kotlin.math.abs

data class Coord(val x: Int, val y: Int)

fun String.toXY() = this.split(",").let { Coord(it.first().toInt(), it.last().toInt()) }

fun Pair<Coord, Coord>.isDiag() = abs(this.first.x - this.second.x) == abs(this.first.y - this.second.y)
fun Pair<Coord, Coord>.isHor() = this.first.x == this.second.x
fun Pair<Coord, Coord>.isVert() = this.first.y == this.second.y

fun Pair<Coord, Coord>.populateLine(): MutableList<Coord> {
    val list = mutableListOf<Coord>()

    if (this.isHor()) {
        val (min, max) = mutableListOf(this.first.y, this.second.y).let { it.minOrNull()!! to it.maxOrNull()!! }
        for (i in min..max) list.add(Coord(this.first.x, i))
    } else if (this.isVert()) {
        val (min, max) = mutableListOf(this.first.x, this.second.x).let { it.minOrNull()!! to it.maxOrNull()!! }
        for (i in min..max) list.add(Coord(i, this.first.y))
    } else if (this.isDiag()) {
        val increase = this.first.y < this.second.y
        var y = this.first.y
        if (this.first.x < this.second.x)
            for (i in this.first.x..this.second.x) {
                list.add(Coord(i, y))
                if (increase) y++ else y--
            }
        else
            for (i in this.first.x downTo (this.second.x)) {
                list.add(Coord(i, y))
                if (increase) y++ else y--
            }
    }
    return list
}

fun process(input: List<String>, addlFilter: ((Pair<Coord, Coord>) -> Boolean)?): Int {
    val coords = input.map { line -> line.split(" -> ").let { Pair(it.first().toXY(), it.last().toXY()) } }
    val filtered =
        if (addlFilter != null) coords.filter { it.isHor() || it.isVert() || addlFilter(it) }.toMutableList()
        else coords.filter { it.isHor() || it.isVert() }.toMutableList()
    val allCoords = filtered.flatMap { it.populateLine() }
    return allCoords.groupingBy { it }.eachCount().entries.filter { it.value > 1 }.size
}

fun main() {
    fun part1(input: List<String>): Int = process(input, null)

    fun part2(input: List<String>): Int = process(input) { it.isDiag() }

    val input = readInput(2021, "05")
    println(part1(input))
    println(part2(input))
}
