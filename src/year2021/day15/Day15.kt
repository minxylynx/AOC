package year2021.day15

import readInput
import kotlin.math.floor
import kotlin.system.measureTimeMillis

//  https://adventofcode.com/2021/day/15

// pathfinding as a function of growing total value.
// Track coords seen, the value at that coord, and when processing, pull from the smallest value first (removing from
// stack) to continue down the smallest route possible to that point.
// No visiting the same coords twice

fun neighbors(list: Int, row: Int, listSize: Int, rowSize: Int): MutableList<Pair<Int, Int>> {
    val new = mutableListOf<Pair<Int, Int>>()
    if (list > 0) new.add(Pair(list - 1, row))
    if (list < listSize) new.add(Pair(list + 1, row))
    if (row > 0) new.add(Pair(list, row - 1))
    if (row < rowSize) new.add(Pair(list, row + 1))
    return new
}

fun pathFinding(graph: List<List<Int>>): Int {
    val (listSize, rowSize) = graph.size - 1 to graph[0].size - 1
    val visited = mutableSetOf<Pair<Int, Int>>()
    val q = ArrayDeque(listOf(Pair(0,Pair(0,0))))

    while (q.isNotEmpty()) {
        val (cost, coord) = q.removeFirst()
        if (visited.contains(Pair(coord.first, coord.second))) continue
        if (coord.first == listSize && coord.second == rowSize)  return cost.also { println(cost) }
        visited.add(Pair(coord.first, coord.second))
        neighbors(coord.first, coord.second, listSize, rowSize).filter { !visited.contains(it) }
            .forEach { q.add(Pair(cost + graph[it.first][it.second], Pair(it.first, it.second))) }
            .also { q.sortBy { it.first } }
    }
    return Int.MAX_VALUE
}

fun Int.wrap() = if (this > 9) this - 9 else this

fun main() {
    fun part1(input: List<String>): Int {
        val graph = input.map { it.toCharArray().map { char -> char.toString().toInt() } }
        return pathFinding(graph)
    }

    fun part2(input: List<String>): Int {
        val graph = input.map { it.toCharArray().map { char -> char.toString().toInt() } }
        val (listSize, rowSize) = graph.size to graph[0].size
        val expansion = (0 until (listSize * 5)).map { list ->
            (0 until (rowSize * 5)).map { row ->
                (graph[list % listSize][row % rowSize] + (list / listSize) + (row / rowSize)).wrap()
            }
        }
        return pathFinding(expansion)
    }

    val input = readInput(2021, "15")
    measureTimeMillis { println(part1(input)) }.let { println("part1 time to completion: $it ms") }
    measureTimeMillis { println(part2(input)) }.let { println("part2 time to completion: $it ms") }
}
