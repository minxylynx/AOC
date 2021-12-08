package year2021.day08

import readInput

//  https://adventofcode.com/2021/day/8

fun String.findNum() =
    when (this.length) {
        2 -> 1
        4 -> 4
        3 -> 7
        7 -> 8
        else -> null
    }

val easyNums = listOf(2, 4, 3, 7)

fun String.isUniqueLength() = if (easyNums.contains(this.length)) 1 else 0

fun List<Char>.findPossible(array: Array<List<Char>?>): Array<List<Char>?> =
    when (this.size) {
        5 ->
            if (this.containsAll((array[1]!! + array[7]!!).distinct())) 3
            else if (array[6] != null && array[6]!!.containsAll(this)) 5
            else 2
        6 ->
            if (!this.containsAll(array[1]!!)) 6
            else if (!this.containsAll(array[4]!!)) 0
            else 9
        else -> null
    }
        .also { array[it!!] = this.sorted() }
        .let { array }

// five segs = 2,3,5
// six segs = 0,6,9

// exclude 8
// 0 == 1,7   != 4
// 2 ==       != 1,4,7
// 3 == 1,7   != 4
// 5 ==       != 1,4,7
// 6 ==       != 1,4,7
// 9 == 1,4,7 != 8

fun main() {
    fun part1(input: List<String>): Int =
        input.sumOf {
            it.split(" | ")
                .last().split(" ")
                .fold(0) { sum, str -> sum + str.isUniqueLength() }.toInt()
        }

    fun part2(input: List<String>): Int =
        input.map { it.split(" | ") }.sumOf { start ->
            var numList = arrayOfNulls<List<Char>>(10)
            val (known, unknown) = start.first().split(" ").partition { easyNums.contains(it.length) }
            known.forEach { numList[it.findNum()!!] = it.toList().sorted() }
            unknown.sortedByDescending { it.length }.forEach { numList = it.toList().sorted().findPossible(numList) }

            start.last().split(" ")
                .map { out -> numList.indexOf(out.toList().sorted()) }
                .joinToString("")
                .toInt()
        }

    val input = readInput(2021, "08")
    println(part1(input))
    println(part2(input))
}
