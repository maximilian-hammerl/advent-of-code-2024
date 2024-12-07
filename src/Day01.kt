import java.io.BufferedReader
import kotlin.math.absoluteValue

class Day01 : AdventOfCodeChallenge<Int, Int>(1) {
    override fun solveFirstTask(input: BufferedReader): Int {
        val leftList = mutableListOf<Int>()
        val rightList = mutableListOf<Int>()

        input.forEachLine { line ->
            val parts = line.split("\\s+".toRegex())
            leftList.add(parts[0].toInt())
            rightList.add(parts[1].toInt())
        }

        leftList.sort()
        rightList.sort()

        val totalDistance =
            leftList.withIndex().sumOf { (index, leftNumber) -> (rightList[index] - leftNumber).absoluteValue }

        return totalDistance
    }

    override fun solveSecondTask(input: BufferedReader): Int {
        val leftList = mutableListOf<Int>()
        val rightList = mutableListOf<Int>()

        input.forEachLine { line ->
            val parts = line.split("\\s+".toRegex())
            leftList.add(parts[0].toInt())
            rightList.add(parts[1].toInt())
        }

        val totalSimilarity = leftList.sumOf { leftNumber -> leftNumber * rightList.count { it == leftNumber } }

        return totalSimilarity
    }
}
