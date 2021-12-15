package year2021.day14

import readInput
import kotlin.system.measureTimeMillis

//  https://adventofcode.com/2021/day/14

// Seriously sucked, but for exponentially growing strings, find some way to group that and count...

fun main() {
    fun part1(input: List<String>): Long = polymerize(input, 10)

    fun part2(input: List<String>): Long = polymerize(input, 40)

    val input = readInput(2021, "14")
    measureTimeMillis { println(part1(input)) }.let { println("part1 time to completion: $it ms") }
    measureTimeMillis { println(part2(input)) }.let { println("part2 time to completion: $it ms") }
}

fun polymerize(input: List<String>, times: Int): Long {
    val initial = input.first()
    val rules = input.drop(2).associate { line ->
        val (pair, add) = line.split(" -> ") // SK -> C
        pair to listOf("${pair.first()}$add", "$add${pair.last()}")  // SK -> [SC, CK]
    }

    var polymer = initial.zip(initial.drop(1)) { a, b -> "$a$b" } // zip the start into pairs
        .groupingBy { it }.eachCount()
        .mapValues { it.value.toLong() } // group by pair and count
    // We dont actually care about the pair, only about the first char of each pair, which is why this works
    // We track the pair as seen in the string so we know what rule to apply, but counting only cares about the first
    // char
    repeat(times) {
        polymer = buildMap {
            for ((pair, count) in polymer) { // for each pair
                for (rule in rules.getValue(pair)) { // find the matching rule, and add each resulting pair to the map
                    put(rule, getOrElse(rule) { 0 } + count)
                }
            }
        }
    }

    // count up the first char of each pair, and add one more for the last char of the initial string
    val test = polymer.map { (pair, n) -> pair.first() to n }
        .groupBy({ it.first }, { it.second })
        .mapValues { it.value.sum() }
        .toMutableMap()
    test[initial.last()] = test.getOrPut(initial.last()) { 0 } + 1

    return test.values.let { it.maxOrNull()!! - it.minOrNull()!! }
}
