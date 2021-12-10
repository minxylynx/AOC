package year2021.day10

import readInput
import java.math.BigInteger

//  https://adventofcode.com/2021/day/10

val pairs = mapOf(")" to "(", "}" to "{", "]" to "[", ">" to "<")
val outValueMap = mapOf(")" to 3, "]" to 57, "}" to 1197, ">" to 25137)
val matchedValueMap = mapOf(")" to 1, "]" to 2, "}" to 3, ">" to 4)

fun main() {
    val invalidLines = mutableListOf<Int>()

    fun part1(input: List<String>): Int {
        val ins = pairs.values
        val invalidChars = mutableListOf<Char>()
        input.forEachIndexed { idx, line ->
            val inList = mutableListOf<Char>()
            for (char in line.toList()) {
                if (ins.contains(char.toString())) inList.add(char)
                else if (inList.last().toString() == pairs[char.toString()]) inList.removeLast()
                else {
                    invalidChars.add(char)
                    invalidLines.add(idx)
                    break
                }
            }
        }

        return invalidChars.sumOf { outValueMap[it.toString()]!! }
    }

    fun part2(input: List<String>): BigInteger {
        val ins = pairs.values
        val outsMap = pairs.entries.associateBy({ it.value }) { it.key }
        val incompleteLines = mutableListOf<List<String>>()
        input.filterIndexed { idx, _ -> !invalidLines.contains(idx) }
            .forEach { line ->
                val inList = mutableListOf<Char>()
                for (char in line.toList()) {
                    if (ins.contains(char.toString())) inList.add(char)
                    else if (inList.last().toString() == pairs[char.toString()]) inList.removeLast()
                    else break
                }
                incompleteLines.add(inList.reversed().map { outsMap[it.toString()]!! })
            }

        // already reversed and mapped to the out value
        val total = incompleteLines
            .map {
                it.fold(BigInteger.ZERO) { acc, ele ->
                    acc.times(BigInteger.valueOf(5)) + matchedValueMap[ele]!!.toBigInteger()
                }
            }.sorted()

        return total[total.size / 2]
    }

    val input = readInput(2021, "10")
    println(part1(input))
    println(part2(input))
}
