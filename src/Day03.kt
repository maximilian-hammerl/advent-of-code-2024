class Day03 : AdventOfCodeChallenge<Int, Int>(3) {
    override fun solveFirstTask(): Int {
        val regex = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()

        val input = readInput().readLines().joinToString("")

        val matches = regex.findAll(input)

        var result = 0

        for (match in matches) {
            val x = match.groupValues[1].toInt()
            val y = match.groupValues[2].toInt()

            result += (x * y)
        }

        return result
    }

    override fun solveSecondTask(): Int {
        val regex = """mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)""".toRegex()

        val input = readInput().readLines().joinToString("")
        var result = 0

        var doMultiply = true

        regex.findAll(input).forEach { match ->
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
