package year2021.day09

import mapOnlyIntstoInts
import readInput

//  https://adventofcode.com/2021/day/9

fun List<List<Int>>.findLows(): List<Int> =
    this.flatMapIndexed { listIdx, line ->
        line.mapIndexedNotNull { lineIdx, digit ->
            listOfNotNull(
                this.getOrNull(listIdx - 1)?.get(lineIdx), //up
                this.getOrNull(listIdx + 1)?.get(lineIdx), // down
                this[listIdx].getOrNull(lineIdx - 1), // left
                this[listIdx].getOrNull(lineIdx + 1)  // right
            ).let { if (it.minOrNull()!! > digit) digit else null }
        }
    }

fun List<List<Int>>.findBasins(): List<Int> {
    val listSize = this.size
    val lineSize = this[0].size
    val truthMap = Array(listSize) { BooleanArray(lineSize) }

    // recursive function to search expanding out for basins
    fun findBasinExtent(listIdx: Int, lineIdx: Int): Int {
        if (listIdx < 0 || listIdx >= listSize || lineIdx < 0 || lineIdx >= lineSize // out of bounds check
            || truthMap[listIdx][lineIdx]  // record exists in the truth map
            || this[listIdx][lineIdx] == 9 // equals 9 (discard those)
        )
            return 0
        truthMap[listIdx][lineIdx] = true
        return 1 +
            findBasinExtent(listIdx + 1, lineIdx) + //down
            findBasinExtent(listIdx - 1, lineIdx) + // up
            findBasinExtent(listIdx, lineIdx + 1) + //right
            findBasinExtent(listIdx, lineIdx - 1) // left
    }

    return this.flatMapIndexed { listIdx, line ->
        line.mapIndexedNotNull { lineIdx, digit ->
            if (!truthMap[listIdx][lineIdx] && digit != 9) {
                findBasinExtent(listIdx, lineIdx)
            } else null
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val lows = input.mapOnlyIntstoInts().findLows()
        return lows.fold(0) { sum, ele -> sum + (ele + 1) }
    }

    fun part2(input: List<String>): Int {
        val basins = input.mapOnlyIntstoInts().findBasins()
        return basins.sortedDescending().take(3).reduce { product, ele -> product * ele }
    }

    val input = readInput(2021, "09")
    println(part1(input))
    println(part2(input))
}
