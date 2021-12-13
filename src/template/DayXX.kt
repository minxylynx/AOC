package template

import readInput
import kotlin.system.measureTimeMillis

//  https://adventofcode.com/2021/day/XX

fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput(2021, "XX")
    val part1Time = measureTimeMillis { println(part1(input)) }
    val part2Time = measureTimeMillis { println(part2(input)) }
    println("part1 time to completion: $part1Time ms")
    println("part2 time to completion: $part2Time ms")
}
