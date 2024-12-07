class Day07 : AdventOfCodeChallenge<Long, Long>(7) {
    override fun solveFirstTask(): Long {
        var result: Long = 0

        readInput().forEachLine { line ->
            val parts = line.split(": ")
            val expectedTestValue = parts[0].toLong()
            val numbers = parts[1].split(' ').map { it.toLong() }

            for (combinations in generateCombinations(listOf('+', '*'), numbers.size - 1)) {
                var actualTestValue = numbers[0]

                for (i in 1..<numbers.size) {
                    when (val operation = combinations[i - 1]) {
                        '*' -> {
                            actualTestValue *= numbers[i]
                        }

                        '+' -> {
                            actualTestValue += numbers[i]
                        }

                        else -> throw Exception("Unknown operation: $operation")
                    }
                }

                if (expectedTestValue == actualTestValue) {
                    result += expectedTestValue
                    break
                }
            }
        }

        return result
    }

    override fun solveSecondTask(): Long {
        var result: Long = 0

        readInput().forEachLine { line ->
            val parts = line.split(": ")
            val expectedTestValue = parts[0].toLong()
            val numbers = parts[1].split(' ').map { it.toLong() }

            for (combinations in generateCombinations(listOf("+", "*", "||"), numbers.size - 1)) {
                var actualTestValue = numbers[0]

                for (i in 1..<numbers.size) {
                    when (val operation = combinations[i - 1]) {
                        "*" -> {
                            actualTestValue *= numbers[i]
                        }

                        "+" -> {
                            actualTestValue += numbers[i]
                        }

                        "||" -> {
                            actualTestValue = "${actualTestValue}${numbers[i]}".toLong()
                        }

                        else -> throw Exception("Unknown operation: $operation")
                    }

                    if (actualTestValue > expectedTestValue) {
                        break
                    }
                }

                if (expectedTestValue == actualTestValue) {
                    result += expectedTestValue
                    break
                }
            }
        }

        return result
    }

    private fun <T> generateCombinations(elements: List<T>, length: Int): List<List<T>> {
        val combinations = mutableListOf<List<T>>()

        fun buildCombination(current: List<T>) {
            if (current.size == length) {
                combinations.add(current)
                return
            }
            for (element in elements) {
                buildCombination(current + element)
            }
        }

        buildCombination(emptyList())
        return combinations
    }
}
