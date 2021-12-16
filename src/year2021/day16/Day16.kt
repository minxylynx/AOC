package year2021.day16

import readInput
import java.math.BigInteger
import kotlin.system.measureTimeMillis

//  https://adventofcode.com/2021/day/16

fun Char.toBinary() = mapOf("0" to "0000", "1" to "0001", "2" to "0010", "3" to "0011", "4" to "0100", "5" to "0101",
    "6" to "0110", "7" to "0111", "8" to "1000", "9" to "1001", "A" to "1010", "B" to "1011", "C" to "1100",
    "D" to "1101", "E" to "1110", "F" to "1111")[this.toString()]!!

fun String.parse() = BigInteger(this, 2).toLong()

fun Long.isLiteral() = this.toInt() == 4

data class Packet(
    val version: Long,
    val type: Long,
    var packetStr: String,
    val subPackets: MutableList<Packet>,
    var literal: Long? = null
) {
    fun subPacketSize() = subPackets.sumOf { it.packetStr.length }

    fun operate() =
        when(type.toInt()) {
            0 -> subPackets.mapNotNull { it.literal }.sum()
            1 -> subPackets.mapNotNull { it.literal }.fold(1, Long::times)
            2 -> subPackets.mapNotNull { it.literal }.minOrNull()!!
            3 -> subPackets.mapNotNull { it.literal }.maxOrNull()!!
            5 -> subPackets.mapNotNull { it.literal }.let { if (it[0] > it[1]) 1 else 0 }
            6 -> subPackets.mapNotNull { it.literal }.let { if (it[0] < it[1]) 1 else 0 }
            7 -> subPackets.mapNotNull { it.literal }.let { if (it[0] == it[1]) 1 else 0 }
            else -> throw Exception("FAILURE")
        }.let { this.literal = it }

    fun getAllVersions(): List<Long> =
        mutableListOf(this.version) + this.subPackets.map { it.getAllVersions() }.flatten()
}

fun processHeader(binary: String): Packet {
    val packetVersion = binary.substring(0,3).parse()
    val idType = binary.substring(3,6).parse()
    val packet = Packet(packetVersion, idType, binary.substring(0,6), mutableListOf())
    return if (idType.isLiteral()) {
        processLiteral(binary.substring(6), "", "").let { (str, lit) ->
            packet.packetStr += str
            packet.literal = lit
        }
        packet
    } else processOperator(binary.substring(6), packet)
}

fun processOperator(binary: String, packet: Packet): Packet {
    when (binary.take(1)) {
        "0" -> {
            var totalSubPacketSize = binary.substring(1, 16).parse()
            var startIndex = 16
            while (totalSubPacketSize > 0) {
                processHeader(binary.substring(startIndex)).let {
                    packet.subPackets.add(it)
                    totalSubPacketSize -= it.packetStr.length
                    startIndex += it.packetStr.length
                }
            }
            packet.packetStr += binary.substring(0, 16 + packet.subPacketSize())
        }
        "1" -> {
            val totalSubPackets = binary.substring(1,12).parse()
            var startIndex = 12
            repeat(totalSubPackets.toInt()) {
                processHeader(binary.substring(startIndex)).let {
                    packet.subPackets.add(it)
                    startIndex += it.packetStr.length
                }
            }
            packet.packetStr += binary.substring(0, 12 + packet.subPacketSize())
        }
    }
    packet.operate()
    return packet
}

fun processLiteral(binary: String, strBuild: String, literal: String): Pair<String, Long> =
    binary.take(5).let {
        val str = it.takeLast(4)
        return if (it.take(1) == "1")
            processLiteral(binary.substring(5), strBuild + str, literal + it)
        else Pair(literal + it, (strBuild + str).parse())
    }

fun main() {
    fun part1(input: List<String>): Long {
        val binaryList = input.first().fold("") { start, char -> start + char.toBinary() }
        return processHeader(binaryList).getAllVersions().sum()
    }

    fun part2(input: List<String>): Long {
        val binaryList = input.first().fold("") { start, char -> start + char.toBinary() }
        return processHeader(binaryList).literal!!
    }

    val input = readInput(2021, "16")
    measureTimeMillis { println(part1(input)) }.let { println("part1 time to completion: $it ms") }
    measureTimeMillis { println(part2(input)) }.let { println("part2 time to completion: $it ms") }
}
