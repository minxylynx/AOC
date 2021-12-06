package year2021.day01

import readInput

/*
 https://adventofcode.com/2021/day/1
 */

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { it.toInt() }
            .windowed(2).count { it[1] > it[0] }
    }

    fun part2(input: List<String>): Int {
        return input.map { it.toInt() }
            .windowed(3) { it.sum() }
            .windowed(2)
            .count { it[1] > it[0] }
    }

    val input = readInput(2021, "01")
    println(part1(input))
    println(part2(input))
}
