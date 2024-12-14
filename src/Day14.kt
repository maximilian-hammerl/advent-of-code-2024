import java.io.BufferedReader

class Day14 : AdventOfCodeChallenge<Int, Int>(14) {
    private var seconds = 100
    private var spaceWidth = 101
    private var spaceHeight = 103

    override fun solveFirstTask(input: BufferedReader): Int {
        val robots = mutableListOf<Robot>()

        input.forEachLine { line ->
            val split = line.split(' ')
            val position = split[0].substring(2, split[0].length).split(',')
            val velocity = split[1].substring(2, split[1].length).split(',')
            robots.add(
                Robot(
                    Pair(position[0].toInt(), position[1].toInt()), Pair(velocity[0].toInt(), velocity[1].toInt())
                )
            )
        }

        for (i in 0..<seconds) {
            for (robot in robots) {
                robot.move()
            }
        }

        val quadrants = mutableMapOf<Int, Int>()

        for (robot in robots) {
            val quadrant = robot.determineQuadrant()
            if (quadrant != null) {
                val existingCount = quadrants.getOrDefault(quadrant, 0)
                quadrants[quadrant] = existingCount + 1
            }
        }

        return quadrants.values.product()
    }

    override fun solveSecondTask(input: BufferedReader): Int {
        val robots = mutableListOf<Robot>()

        input.forEachLine { line ->
            val split = line.split(' ')
            val position = split[0].substring(2, split[0].length).split(',')
            val velocity = split[1].substring(2, split[1].length).split(',')
            robots.add(
                Robot(
                    Pair(position[0].toInt(), position[1].toInt()), Pair(velocity[0].toInt(), velocity[1].toInt())
                )
            )
        }

        var seconds = 0

        while (true) {
            if (arrangedAsChristmasTree(robots)) {
                break
            }

            for (robot in robots) {
                robot.move()
            }

            seconds++
        }

        return seconds
    }

    private fun arrangedAsChristmasTree(robots: List<Robot>): Boolean {
        for (x in 0..<spaceWidth) {
            var numberRobotsInARow = 0
            for (y in 0..<spaceHeight) {
                if (robots.find { robot -> robot.position.first == x && robot.position.second == y } != null) {
                    numberRobotsInARow++
                } else {
                    numberRobotsInARow = 0
                }
                if (numberRobotsInARow >= 10) {
                    return true
                }
            }
        }
        return false
    }

    private inner class Robot(
        var position: Pair<Int, Int>,
        private val velocity: Pair<Int, Int>,
    ) {

        fun move() {
            var x = position.first + velocity.first
            var y = position.second + velocity.second

            while (x < 0) {
                x += spaceWidth
            }

            while (x >= spaceWidth) {
                x -= spaceWidth
            }

            while (y < 0) {
                y += spaceHeight
            }

            while (y >= spaceHeight) {
                y -= spaceHeight
            }

            position = Pair(x, y)
        }

        fun determineQuadrant(): Int? {
            val xCenter = ((spaceWidth - 1) / 2)
            val yCenter = ((spaceHeight - 1) / 2)

            if (position.first < xCenter && position.second < yCenter) {
                return 1
            }

            if (position.first > xCenter && position.second < yCenter) {
                return 2
            }

            if (position.first < xCenter && position.second > yCenter) {
                return 3
            }

            if (position.first > xCenter && position.second > yCenter) {
                return 4
            }

            return null
        }
    }
}
