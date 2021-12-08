package year2021.day07

import readInput
import splitToInt
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.roundToInt

//  https://adventofcode.com/2021/day/7

fun Array<Int>.fuelUse(endPoint: Int) =
    this.sumOf { start ->
        val diff = IntProgression.fromClosedRange(start, endPoint, 1.takeIf { start <= endPoint } ?: -1).count() - 1
        IntRange(1, diff).sum()
    }

fun main() {
    fun part1(input: List<String>): Int {
        val array = input.splitToInt(",").flatten().toTypedArray().sortedArray()
        val median = array[array.size / 2]
        return array.sumOf { abs(it - median) }
    }

    fun part2(input: List<String>): Int {
        val array = input.splitToInt(",").flatten().toTypedArray()
        val avg = array.average()
        return minOf(array.fuelUse(floor(avg).toInt()), array.fuelUse(avg.roundToInt()))
    }

    val input = readInput(2021, "07")
    println(part1(input))
    println(part2(input))
}
