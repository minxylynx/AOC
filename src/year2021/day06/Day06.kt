package year2021.day06

import readInput
import splitToInt

/*
 https://adventofcode.com/2021/day/6
 */


fun List<Int>.countFish(days: Int) =
    LongArray(9)
        .also { array -> this.groupingBy { it }.eachCount().map { array[it.key] = it.value.toLong() } }
        .let { set ->
            repeat(days) {
                set[0].also { zeroes ->
                    set.copyInto(set, 0, 1)
                    set[6] += zeroes
                    set[8] = zeroes
                }
            }
            set.sum()
        }

fun main() {
    fun part1(input: List<String>): Long = input.splitToInt(",").flatten().countFish(80)

    fun part2(input: List<String>): Long = input.splitToInt(",").flatten().countFish(256)

    val input = readInput(2021, "06")
    println(part1(input))
    println(part2(input))
}

