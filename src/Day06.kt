class Day06 : AdventOfCodeChallenge<Int, Int>(6) {
    enum class Direction {
        NORTH, SOUTH, WEST, EAST
    }

    override fun solveFirstTask(): Int {
        val grid = mutableListOf<List<Boolean>>()

        var currentPosition: Pair<Int, Int>? = null
        var currentDirection: Direction? = null

        val uniquePositions = mutableSetOf<Pair<Int, Int>>()

        readInput().forEachLine { line ->
            val row = mutableListOf<Boolean>()

            line.withIndex().forEach { (index, char) ->
                row.add(char != '#')

                if (char != '#' && char != '.') {
                    currentPosition = Pair(index, grid.size)

                    currentDirection = when (char) {
                        '>' -> Direction.EAST
                        '<' -> Direction.WEST
                        '^' -> Direction.NORTH
                        'v' -> Direction.SOUTH
                        else -> throw Exception("Unknown character: $char")
                    }
                }
            }

            grid.add(row)
        }

        while (true) {
            val (nextPosition, nextDirection) = calculateNextPosition(currentPosition!!, currentDirection!!, grid)
                ?: break

            uniquePositions.add(nextPosition)

            currentPosition = nextPosition
            currentDirection = nextDirection
        }

        return uniquePositions.size
    }

    private fun calculateNextPosition(
        currentPosition: Pair<Int, Int>,
        currentDirection: Direction,
        grid: List<List<Boolean>>,
    ): Pair<Pair<Int, Int>, Direction>? {
        val nextPossiblePosition = when (currentDirection) {
            Direction.EAST -> Pair(currentPosition.first + 1, currentPosition.second)
            Direction.WEST -> Pair(currentPosition.first - 1, currentPosition.second)
            Direction.NORTH -> Pair(currentPosition.first, currentPosition.second - 1)
            Direction.SOUTH -> Pair(currentPosition.first, currentPosition.second + 1)
        }

        if (!isPositionInBound(nextPossiblePosition, grid)) {
            return null
        }

        if (grid[nextPossiblePosition.second][nextPossiblePosition.first]) {
            return Pair(nextPossiblePosition, currentDirection)
        }

        val newDirection = when (currentDirection) {
            Direction.EAST -> Direction.SOUTH
            Direction.WEST -> Direction.NORTH
            Direction.NORTH -> Direction.EAST
            Direction.SOUTH -> Direction.WEST
        }
        return calculateNextPosition(currentPosition, newDirection, grid)
    }

    private fun <X> isPositionInBound(currentPosition: Pair<Int, Int>, grid: List<List<X>>): Boolean {
        if (currentPosition.first < 0 || currentPosition.first >= grid[0].size) {
            return false
        }
        if (currentPosition.second < 0 || currentPosition.second >= grid.size) {
            return false
        }
        return true
    }

    private fun printGrid(currentPosition: Pair<Int, Int>, currentDirection: Direction, grid: List<List<Boolean>>) {
        for (y in grid.indices) {
            for (x in grid[y].indices) {
                if (x == currentPosition.first && y == currentPosition.second) {
                    print(
                        when (currentDirection) {
                            Direction.EAST -> '>'
                            Direction.WEST -> '<'
                            Direction.NORTH -> '^'
                            Direction.SOUTH -> 'v'
                        }
                    )
                } else {
                    print(if (grid[y][x]) '.' else '#')
                }
            }
            println()
        }
        println("-----")
    }

    override fun solveSecondTask(): Int {
        val grid = mutableListOf<MutableList<Boolean>>()

        var startingPosition: Pair<Int, Int>? = null
        var startingDirection: Direction? = null

        readInput().forEachLine { line ->
            val row = mutableListOf<Boolean>()

            line.withIndex().forEach { (index, char) ->
                row.add(char != '#')

                if (char != '#' && char != '.') {
                    startingPosition = Pair(index, grid.size)

                    startingDirection = when (char) {
                        '>' -> Direction.EAST
                        '<' -> Direction.WEST
                        '^' -> Direction.NORTH
                        'v' -> Direction.SOUTH
                        else -> throw Exception("Unknown character: $char")
                    }
                }
            }

            grid.add(row)
        }

        var newObstructions = 0

        for (y in grid.indices) {
            for (x in grid[y].indices) {
                if (x == startingPosition!!.first && y == startingPosition!!.second) {
                    continue
                }

                if (!grid[y][x]) {
                    continue
                }

                // Set obstruction
                grid[y][x] = false

                var currentPosition = Pair(startingPosition!!.first, startingPosition!!.second)
                var currentDirection = startingDirection!!

                var currentTry = 0

                while (currentTry < 1_000_000) {
                    val (nextPosition, nextDirection) = calculateNextPosition(
                        currentPosition,
                        currentDirection,
                        grid,
                    ) ?: break

                    currentPosition = nextPosition
                    currentDirection = nextDirection

                    currentTry++
                }

                if (currentTry == 1_000_000) {
                    newObstructions++
                }

                // Remove obstruction again
                grid[y][x] = true
            }
        }

        return newObstructions
    }
}
