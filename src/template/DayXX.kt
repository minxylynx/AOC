package template

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput(2021, "XX")
    println(part1(input))
    println(part2(input))
}
