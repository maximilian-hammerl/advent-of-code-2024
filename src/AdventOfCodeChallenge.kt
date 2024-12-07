import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import kotlin.time.measureTimedValue

abstract class AdventOfCodeChallenge<FirstTaskResult, SecondTaskResult>(
    private val day: Int,
    private val separateInputForEachTask: Boolean = false,
) {

    protected abstract fun solveFirstTask(input: BufferedReader): FirstTaskResult

    protected abstract fun solveSecondTask(input: BufferedReader): SecondTaskResult

    private fun validateFirstTask(
        expectedFirstTaskResult: FirstTaskResult,
    ) {
        val input = if (separateInputForEachTask) {
            readInput(1)
        } else {
            readInput()
        }

        val (actualFirstTaskResult, timeTaken) = measureTimedValue {
            solveFirstTask(input)
        }

        println("Day $day, task 1: $timeTaken")

        check(actualFirstTaskResult != null && expectedFirstTaskResult != null && actualFirstTaskResult == expectedFirstTaskResult) { "First task: Actual $actualFirstTaskResult not equal to expected $expectedFirstTaskResult" }
    }

    private fun validateSecondTask(expectedSecondTaskResult: SecondTaskResult) {
        val input = if (separateInputForEachTask) {
            readInput(2)
        } else {
            readInput()
        }

        val (actualSecondTaskResult, timeTaken) = measureTimedValue {
            solveSecondTask(input)
        }

        println("Day $day, task 2: $timeTaken")

        check(actualSecondTaskResult != null && expectedSecondTaskResult != null && actualSecondTaskResult == expectedSecondTaskResult) { "Second task: Actual $actualSecondTaskResult not equal to expected $expectedSecondTaskResult" }
    }

    fun validate(
        expectedFirstTaskResult: FirstTaskResult,
        expectedSecondTaskResult: SecondTaskResult,
    ) {
        validateFirstTask(expectedFirstTaskResult)
        validateSecondTask(expectedSecondTaskResult)
    }

    private fun readInput(task: Int? = null): BufferedReader {
        val formattedDay = String.format("%02d", day)
        var pathname = "day-$formattedDay"

        if (task != null) {
            val formattedTask = String.format("%02d", task)
            pathname += "-task-$formattedTask"
        }

        val inputStream: InputStream = File("$pathname.txt").inputStream()

        return inputStream.bufferedReader()
    }
}
