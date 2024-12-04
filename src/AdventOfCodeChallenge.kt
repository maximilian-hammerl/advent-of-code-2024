import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import kotlin.time.measureTimedValue

abstract class AdventOfCodeChallenge<FirstTaskResult, SecondTaskResult>(
    private val day: Int,
) {

    protected abstract fun solveFirstTask(): FirstTaskResult

    protected abstract fun solveSecondTask(): SecondTaskResult

    private fun validateFirstTask(
        expectedFirstTaskResult: FirstTaskResult,
    ) {
        val (actualFirstTaskResult, timeTaken) = measureTimedValue {
            solveFirstTask()
        }

        println("Day $day, task 1: $timeTaken")

        check(actualFirstTaskResult != null && expectedFirstTaskResult != null && actualFirstTaskResult == expectedFirstTaskResult) { "First task: Actual $actualFirstTaskResult not equal to expected $expectedFirstTaskResult" }
    }

    private fun validateSecondTask(expectedSecondTaskResult: SecondTaskResult) {
        val (actualSecondTaskResult, timeTaken) = measureTimedValue {
            solveSecondTask()
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

    protected fun readInput(task: Int? = null): BufferedReader {
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
