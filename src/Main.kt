import java.io.File
import java.io.InputStream
import kotlin.math.absoluteValue

fun main() {
    solveFirstPart()
    solveSecondPart()
}

private fun solveSecondPart() {
    val (leftList, rightList) = readInput()

    val totalSimilarity = leftList.sumOf { leftNumber -> leftNumber * rightList.count { it == leftNumber } }

    println("Total similarity: $totalSimilarity")
}

private fun solveFirstPart() {
    val (leftList, rightList) = readInput()

    leftList.sort()
    rightList.sort()

    val totalDistance =
        leftList.withIndex().sumOf { (index, leftNumber) -> (rightList[index] - leftNumber).absoluteValue }

    println("Total distance: $totalDistance")
}

private fun readInput(): Pair<MutableList<Int>, MutableList<Int>> {
    val inputStream: InputStream = File("day-01.txt").inputStream()

    val leftList = mutableListOf<Int>()
    val rightList = mutableListOf<Int>()

    inputStream.bufferedReader().forEachLine { line ->
        val parts = line.split("\\s+".toRegex())
        leftList.add(parts[0].toInt())
        rightList.add(parts[1].toInt())
    }

    return Pair(leftList, rightList)
}
