package year2021.day11

import mapOnlyIntstoArray
import readInput
import kotlin.system.measureTimeMillis

//  https://adventofcode.com/2021/day/11

fun main() {
    fun part1(input: List<String>): Int {
        var octos = input.mapOnlyIntstoArray().toTypedArray()
        var flashCount = 0
        repeat(100) {
            octos.flashes().let {
                octos = it.first
                flashCount += it.second
            }
        }
        return flashCount
    }

    fun part2(input: List<String>): Int {
        var octos = input.mapOnlyIntstoArray().toTypedArray()
        var flashCount = 0
        var step = 0
        for (num in 1..1000) {
            val pair = octos.flashes().also {
                octos = it.first
                flashCount += it.second
            }
            if (pair.first.flatMap { it.asIterable() }.sum() == 0) {
                step = num
                break
            }
        }
        return step
    }

    val input = readInput(2021, "11")
    val part1Time = measureTimeMillis {
        println(part1(input))
    }
    val part2Time = measureTimeMillis {
        println(part2(input))
    }
    println("part1 time to completion: $part1Time ms")
    println("part2 time to completion: $part2Time ms")
}

fun Int.increase() = this + 1

fun Array<IntArray>.flashes(): Pair<Array<IntArray>, Int> {
    val listSize = this.size
    val lineSize = this[0].size
    val currentMap = this.copyOf()
    fun increase(listIdx: Int, lineIdx: Int) {
        if (listIdx < 0 || listIdx >= listSize || lineIdx < 0 || lineIdx >= lineSize) return // OOB check

        currentMap[listIdx][lineIdx] += 1
        if (currentMap[listIdx][lineIdx] == 10) {
            increase(listIdx - 1, lineIdx - 1) // upper left
            increase(listIdx - 1, lineIdx)            // upper middle
            increase(listIdx - 1, lineIdx + 1) // upper right
            increase(listIdx, lineIdx - 1)            // left
            increase(listIdx, lineIdx + 1)            // right
            increase(listIdx + 1, lineIdx - 1) // bottom left
            increase(listIdx + 1, lineIdx)            // bottom middle
            increase(listIdx + 1, lineIdx + 1) // bottom right
        }
    }

    this.forEachIndexed { listIdx, line -> line.forEachIndexed { lineIdx, _ -> increase(listIdx, lineIdx) } }
    // get flash count
    val flashCount = currentMap.fold(0) { sum, list -> sum + list.count { it >= 10 } }

    // reset flashed spots
    currentMap.forEachIndexed { listIdx, line ->
        line.forEachIndexed { lineIdx, num ->
            if (num >= 10) currentMap[listIdx][lineIdx] = 0
        }
    }

    return Pair(currentMap, flashCount)
}
