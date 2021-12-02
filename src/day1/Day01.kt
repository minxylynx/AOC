package day1

import readInput

// Windows FTW

fun main() {
    fun part1(input: List<String>): Int {

        val size = input
            .map { it.toInt() }
            .windowed(2).count { it[1] > it[0] }

        println(size)
        return size
    }

    fun part2(input: List<String>): Int {
        val windowed = input.map { it.toInt() }
            .windowed(3) { it.sum() }
            .windowed(2)
            .count { it[1] > it[0] }
        println(windowed)
        return windowed
    }

    val input = readInput("day1/Day01")
    println(part1(input))
    println(part2(input))
}
