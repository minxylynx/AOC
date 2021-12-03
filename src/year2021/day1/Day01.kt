package year2021.day1

import readInput

// Windows FTW

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

    val input = readInput("year2021/day1/Day01")
    println(part1(input))
    println(part2(input))
}