import java.io.BufferedReader

class Day13 : AdventOfCodeChallenge<Long, Long>(13) {
    override fun solveFirstTask(input: BufferedReader): Long {
        val aButtons = mutableListOf<Pair<Long, Long>>()
        val bButtons = mutableListOf<Pair<Long, Long>>()
        val prizes = mutableListOf<Pair<Long, Long>>()

        val aButtonRegex = Regex("""Button A: X\+(\d+), Y\+(\d+)""")
        val bButtonRegex = Regex("""Button B: X\+(\d+), Y\+(\d+)""")
        val prizeRegex = Regex("""Prize: X=(\d+), Y=(\d+)""")

        input.forEachLine { line ->
            if (line.isEmpty()) {
                return@forEachLine
            }

            if (line.startsWith("Button A")) {
                val matchResult = aButtonRegex.matchEntire(line) ?: throw Exception("Invalid button a $line")
                val (x, y) = matchResult.destructured
                aButtons.add(Pair(x.toLong(), y.toLong()))
            } else if (line.startsWith("Button B")) {
                val matchResult = bButtonRegex.matchEntire(line) ?: throw Exception("Invalid button b $line")
                val (x, y) = matchResult.destructured
                bButtons.add(Pair(x.toLong(), y.toLong()))
            } else if (line.startsWith("Prize")) {
                val matchResult = prizeRegex.matchEntire(line) ?: throw Exception("Invalid prize $line")
                val (x, y) = matchResult.destructured
                prizes.add(Pair(x.toLong(), y.toLong()))
            } else {
                throw Exception("Invalid line $line")
            }
        }

        var numberTokens: Long = 0

        for ((index, prize) in prizes.withIndex()) {
            var minTokenForPrice: Long? = null

            val aButton = aButtons[index]
            val bButton = bButtons[index]

            for (a in 0..<100) {
                for (b in 0..<100) {
                    if (aButton.first * a + bButton.first * b == prize.first && aButton.second * a + bButton.second * b == prize.second) {
                        val tokens: Long = a * 3L + b

                        if (minTokenForPrice == null || tokens < minTokenForPrice) {
                            minTokenForPrice = tokens
                        }
                    }
                }
            }

            if (minTokenForPrice != null) {
                numberTokens += minTokenForPrice
            }
        }

        return numberTokens
    }

    override fun solveSecondTask(input: BufferedReader): Long {
        val aButtons = mutableListOf<Pair<Long, Long>>()
        val bButtons = mutableListOf<Pair<Long, Long>>()
        val prizes = mutableListOf<Pair<Long, Long>>()

        val aButtonRegex = Regex("""Button A: X\+(\d+), Y\+(\d+)""")
        val bButtonRegex = Regex("""Button B: X\+(\d+), Y\+(\d+)""")
        val prizeRegex = Regex("""Prize: X=(\d+), Y=(\d+)""")

        input.forEachLine { line ->
            if (line.isEmpty()) {
                return@forEachLine
            }

            if (line.startsWith("Button A")) {
                val matchResult = aButtonRegex.matchEntire(line) ?: throw Exception("Invalid button a $line")
                val (x, y) = matchResult.destructured
                aButtons.add(Pair(x.toLong(), y.toLong()))
            } else if (line.startsWith("Button B")) {
                val matchResult = bButtonRegex.matchEntire(line) ?: throw Exception("Invalid button b $line")
                val (x, y) = matchResult.destructured
                bButtons.add(Pair(x.toLong(), y.toLong()))
            } else if (line.startsWith("Prize")) {
                val matchResult = prizeRegex.matchEntire(line) ?: throw Exception("Invalid prize $line")
                val (x, y) = matchResult.destructured
                prizes.add(Pair(x.toLong() + 10000000000000L, y.toLong() + 10000000000000L))
            } else {
                throw Exception("Invalid line $line")
            }
        }

        var numberTokens: Long = 0

        for ((index, prize) in prizes.withIndex()) {
            val aButton = aButtons[index]
            val bButton = bButtons[index]

            val aToB = aButton.first * bButton.second - aButton.second * bButton.first
            val prizeToB = prize.first * bButton.second - prize.second * bButton.first
            val aToPrize = aButton.first * prize.second - aButton.second * prize.first

            if (prizeToB % aToB == 0L && aToPrize % aToB == 0L) {
                val minTokenForPrice = (prizeToB / aToB) * 3 + (aToPrize / aToB)
                numberTokens += minTokenForPrice
            }
        }

        return numberTokens
    }
}
