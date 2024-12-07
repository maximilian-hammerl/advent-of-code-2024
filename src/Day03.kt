import java.io.BufferedReader

class Day03 : AdventOfCodeChallenge<Int, Int>(3) {
    override fun solveFirstTask(input: BufferedReader): Int {
        val regex = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()

        var result = 0

        regex.findAll(input.readLines().joinToString("")).forEach { match ->
            val x = match.groupValues[1].toInt()
            val y = match.groupValues[2].toInt()

            result += (x * y)
        }

        return result
    }

    override fun solveSecondTask(input: BufferedReader): Int {
        val regex = """mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)""".toRegex()

        var result = 0

        var doMultiply = true

        regex.findAll(input.readLines().joinToString("")).forEach { match ->
            when (match.value) {
                "don't()" -> doMultiply = false
                "do()" -> doMultiply = true
                else -> {
                    if (doMultiply) {
                        val x = match.groupValues[1].toInt()
                        val y = match.groupValues[2].toInt()

                        result += (x * y)
                    }
                }
            }
        }

        return result
    }
}
