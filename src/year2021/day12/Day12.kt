package year2021.day12

import readInput
import kotlin.system.measureTimeMillis

//  https://adventofcode.com/2021/day/12

fun main() {

    fun List<String>.setup(): Pair<List<String>, Map<String, List<String>>> {
        val pairs = this.map { line -> line.split("-").let { Pair(it.first(), it.last()) } }
        val distinctNodes = this.map { it.split("-") }.flatten().distinct()
        val smallNodes = distinctNodes.filter { it != "start" && it != "end" && it.isLowercase() }
        val map = distinctNodes.associateWith { node ->
            pairs.filter { it.first == node || it.second == node }.map { if (it.first == node) it.second else it.first }
        }
        return Pair(smallNodes, map)
    }

    fun part1(input: List<String>): Int {
        val (smallNodes, map) = input.setup()
        fun buildPath(path: MutableList<String>, node: String): List<MutableList<String>>? {
            if (smallNodes.contains(node) && path.contains(node)) return null
            path.add(node)
            return if (node == "end") mutableListOf(path.copy())
            else map[node]?.filter { it != "start" }?.mapNotNull { buildPath(path.copy(), it) }?.flatten()
        }
        return map["start"]!!.mapNotNull { buildPath(mutableListOf("start"), it) }.flatten().size
    }

    fun part2(input: List<String>): Int {
        val (smallNodes, map) = input.setup()
        fun buildPath(path: MutableList<String>, node: String): List<MutableList<String>>? {
            if (path.visitedSmallNode(node, smallNodes)) return null
            path.add(node)
            return if (node == "end") mutableListOf(path.copy())
            else map[node]?.filter { it != "start" }?.mapNotNull { buildPath(path.copy(), it) }?.flatten()
        }
        return map["start"]!!.mapNotNull { buildPath(mutableListOf("start"), it) }.flatten().size
    }

    val input = readInput(2021, "12")
    measureTimeMillis { println(part1(input)) }.let { println("part1 time to completion: $it ms") }
    measureTimeMillis { println(part2(input)) }.let { println("part2 time to completion: $it ms") }
}

fun String.isLowercase() = this.filter { it.isUpperCase() }.toList().isEmpty()

fun List<String>.copy() = this.toMutableList()

fun List<String>.visitedSmallNode(node: String, smallNodes: List<String>) =
    this.filter { smallNodes.contains(it) }.groupingBy { it }.eachCount().filter { it.value > 1 }
        .let { it.size > 1 || it.entries.singleOrNull()?.key == node }


