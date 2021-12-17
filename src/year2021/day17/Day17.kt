package year2021.day17

import readInput
import year2021.day13.Coord
import kotlin.math.absoluteValue
import kotlin.system.measureTimeMillis

//  https://adventofcode.com/2021/day/17

data class Velocity(var x: Int, var y: Int)

data class CoordWithVel(val coord: Coord, val velocity: Velocity, var highY: Int)

fun Coord.beyondTarget(target: List<Coord>) = this.x > target.maxOf { it.x } || this.y < target.minOf { it.y }

fun throwStuff(coord: CoordWithVel, target: List<Coord>): CoordWithVel? {
    coord.apply {
        this.coord.x += this.velocity.x
        this.coord.y += this.velocity.y
        this.velocity.x = when {
            this.velocity.x > 0 -> this.velocity.x - 1
            this.velocity.x < 0 -> this.velocity.x + 1
            else -> this.velocity.x
        }
        this.velocity.y -= 1
        this.highY = this.coord.y.takeIf { it > this.highY } ?: this.highY
    }
    return if (target.contains(coord.coord)) coord
        else if (coord.coord.beyondTarget(target)) null
        else throwStuff(coord, target)
}

fun main() {
    fun List<String>.setup(): List<Coord> {
        val xs = this.first().split("=")[1].split("..").let { Pair(it[0].toInt(), it[1].toInt()) }
        val ys = this.last().split("=")[1].split("..").let { Pair(it[0].toInt(), it[1].toInt()) }
        return (xs.first..xs.second).flatMap { x -> (ys.first..ys.second).map { y -> Coord(x, y) } }
    }

    fun throwABunchOfStuff(input: List<String>): List<CoordWithVel> {
        val target = input.setup()
        val (xs, ys) =
            Pair(0, target.maxOf { it.x }) to Pair(target.minOf { it.y }, target.maxOf { it.y.absoluteValue })
        return (xs.first..xs.second).flatMap { x ->
            (ys.first..ys.second).mapNotNull { y -> throwStuff(CoordWithVel(Coord(0,0), Velocity(x,y), y), target) }
        }
    }

    fun part1(thrown: List<CoordWithVel>): Int = thrown.maxOf { it.highY }
    fun part2(thrown: List<CoordWithVel>): Int = thrown.size

    val input = readInput(2021, "17")
    var thrown: List<CoordWithVel>
    measureTimeMillis { thrown = throwABunchOfStuff(input).also { println("Threw Stuff") } }
            .let { println("part0 time to completion:$it ms") }
    measureTimeMillis { println(part1(thrown)) }.let { println("part1 time to completion: $it ms") }
    measureTimeMillis { println(part2(thrown)) }.let { println("part2 time to completion: $it ms") }
}
