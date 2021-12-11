import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(year: Int, day: String, addl: String = "") = File("src/year$year", "day$day/Day$day$addl.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun List<Char>.fromBinary() = this.joinToString("").toInt(2)

fun List<String>.splitToInt(splitter: String) = this.map { line -> line.split(splitter).map { it.toInt() } }

fun List<String>.mapOnlyIntstoInts() = this.map { line -> line.toList().map { it.toString().toInt() } }

fun List<String>.mapOnlyIntstoArray() = this.map { line -> line.toList().map { it.toString().toInt() }.toIntArray() }
