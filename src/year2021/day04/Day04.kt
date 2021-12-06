package year2021.day04

import readInput

/*
 https://adventofcode.com/2021/day/4
 */



data class Space(
    val rowIdx: Int,
    var colIdx: Int = 0,
    val number: Int,
    var matched: Boolean = false,
    var cardIdx: Int = 0
)

fun main() {
    fun part1(cards: List<String>, callouts: List<String>): Int {

        val flatList = cards.cardSetup()

        var matchedCard: Int? = null
        var callout: Int? = null
        for (co in callouts[0].split(",").map { it.toInt() }) {
            flatList.filter { it.number == co }.forEach { it.matched = true }
            val matches = flatList.filter { it.matched }

            val rowMatch = matches.groupBy { Pair(it.cardIdx, it.rowIdx) }.entries.firstOrNull { it.value.size == 5 }
            val colMatch = matches.groupBy { Pair(it.cardIdx, it.colIdx) }.entries.firstOrNull { it.value.size == 5 }

            val matchCard = rowMatch?.key?.first ?: colMatch?.key?.first

            if (matchCard != null) {
                matchedCard = matchCard
                callout = co
                break
            }
        }

        val unmatchedSum = matchedCard.unmatchedSum(flatList)

        return unmatchedSum * callout!!
    }

    fun part2(cards: List<String>, callouts: List<String>): Int {
        val flatList = cards.cardSetup()

        val cardNumbers = flatList.map { it.cardIdx }.toSet().toList()

        var last: Int? = null
        var callout: Int? = null
        val matched = mutableSetOf<Int>()
        for (co in callouts[0].split(",").map { it.toInt() }) {
            flatList.filter { it.number == co }.forEach { it.matched = true }
            val matches = flatList.filter { it.matched }

            val rowMatch = matches.groupBy { Pair(it.cardIdx, it.rowIdx) }
                .filter { !matched.contains(it.key.first) && it.value.size == 5 }
            val colMatch = matches.groupBy { Pair(it.cardIdx, it.colIdx) }
                .filter { !matched.contains(it.key.first) && it.value.size == 5 }

            val map = rowMatch + colMatch
            callout = co
            if (last != null) {
                if (map.map { it.key.first }.contains(last))
                    break
            } else {
                matched.addAll(map.map { it.key.first })
            }
            if (cardNumbers.size - matched.size == 1) {
                last = cardNumbers.minus(matched)[0]
            }
        }

        val unmatchedSum = last.unmatchedSum(flatList)

        return unmatchedSum * callout!!
    }

    val cards = readInput(2021, "04", "_cards")
    val callouts = readInput(2021, "04", "_callouts")
    println(part1(cards, callouts))
    println(part2(cards, callouts))
}

fun Int?.unmatchedSum(list: List<Space>) = list.filter { it.cardIdx == this }.filter { !it.matched }.sumOf { it.number }

fun List<String>.cardSetup(): List<Space> {
    val cardsMute = this.toMutableList()
    cardsMute.retainAll { it.isNotBlank() }

    val cardList = cardsMute.map { ln ->
        val numbers = ln.split("\\s+".toRegex()).dropWhile { it.isBlank() }
        numbers.mapIndexed { idx, s -> Space(rowIdx = idx, number = s.toInt()) }
    }.chunked(5)
    cardList.forEachIndexed { cIdx, rowList ->
        rowList.forEachIndexed { idx, list ->
            list.forEach {
                it.colIdx = idx
                it.cardIdx = cIdx
            }
        }
    }
    return cardList.flatMap { it.flatten() }.sortedBy { it.cardIdx }
}
