class Day02 : AdventOfCodeChallenge<Int, Int>(2) {
    override fun solveFirstTask(): Int {
        var numberSafeReports = 0

        readInput().forEachLine { line ->
            val levels = line.split("\\s+".toRegex()).map { it.toInt() }

            val isSafeReport = isSafeReport(levels)

            if (isSafeReport) {
                numberSafeReports++
            }
        }

        return numberSafeReports
    }

    override fun solveSecondTask(): Int {
        var numberSafeReports = 0

        readInput().forEachLine { line ->
            val levels = line.split("\\s+".toRegex()).map { it.toInt() }

            val isSafeReport = isSafeReport(levels) || levels.indices.any { index ->
                isSafeReport(
                    levels.toMutableList().apply { removeAt(index) }
                )
            }

            if (isSafeReport) {
                numberSafeReports++
            }
        }

        return numberSafeReports
    }

    private fun isSafeReport(levels: List<Int>): Boolean {
        val isIncreasing = levels[0] < levels[1]

        return levels.withIndex().all { (index, currentLevel) ->
            if (index == levels.size - 1) {
                return@all true
            }

            val nextLevel = levels[index + 1]

            if (isIncreasing) {
                (nextLevel > currentLevel && (nextLevel - currentLevel <= 3))
            } else {
                (nextLevel < currentLevel && (currentLevel - nextLevel <= 3))
            }
        }
    }
}
