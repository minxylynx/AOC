package year2021.day03

import fromBinary
import readInput

/*
 https://adventofcode.com/2021/day/3
 */


fun main() {
    fun part1(input: List<String>): Int {
        val pairs = IntRange(0, input[0].length - 1).map { i ->
            val map = input.map { it[i] }.groupingBy { it }.eachCount()
            map.maxByOrNull { it.value }!!.key to map.minByOrNull { it.value }!!.key
        }.unzip()

        val gammaInt = pairs.first.fromBinary()
        val epsilonInt = pairs.second.fromBinary()

        return gammaInt * epsilonInt
    }

    fun part2(input: List<String>): Int {
        val ogrList = input.toMutableList()
        val co2List = input.toMutableList()

        for (i in 0 until input[0].length) {
            val common = ogrList.map { it[i] }.groupingBy { it }.eachCount().entries.maxByOrNull { it.value }
            val uncommon = co2List.map { it[i] }.groupingBy { it }.eachCount().entries.minByOrNull { it.value }
            ogrList.retainAll { if (common!!.value == uncommon!!.value) it[i] == '1' else it[i] == common.key }
            co2List.retainAll { if (common!!.value == uncommon!!.value) it[i] == '0' else it[i] == uncommon.key }
        }

        val ogrInt = ogrList.single().toInt(2)
        val co2Int = co2List.single().toInt(2)

        return ogrInt * co2Int
    }

    val input = readInput(2021, "03")
    println(part1(input))
    println(part2(input))
}
