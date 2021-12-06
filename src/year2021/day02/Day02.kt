package year2021.day02

import readInput

/*
 https://adventofcode.com/2021/day/2
 */


fun main() {
    fun part1(input: List<String>): Int {

        var hor = 0
        var vert = 0

       input.map { str -> str.split(" ").let { Pair(it[0], it[1].toInt()) } }
           .forEach {
               when (it.first) {
                   "forward" -> hor += it.second
                   "down" -> vert += it.second
                   "up" -> vert -= it.second
               }
           }
        return hor * vert
    }

    fun part2(input: List<String>): Int {
        var hor = 0
        var vert = 0
        var aim = 0

        input.map { str -> str.split(" ").let { Pair(it[0], it[1].toInt()) } }
            .forEach {
                when (it.first) {
                    "forward" -> {
                        hor += it.second
                        vert += aim * it.second
                    }
                    "down" -> aim += it.second
                    "up" -> aim -= it.second
                }
            }

        return hor * vert
    }

    val input = readInput(2021, "02")
    println(part1(input))
    println(part2(input))
}
