import java.io.BufferedReader

val DIRECTIONS = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))

class Day10 : AdventOfCodeChallenge<Int, Int>(10) {
    override fun solveFirstTask(input: BufferedReader): Int {
        val grid = mutableListOf<List<Int>>()

        val startingPositions = mutableListOf<Pair<Int, Int>>()

        input.forEachLine { line ->
            val row = mutableListOf<Int>()

            line.forEach { char ->
                if (char == '.') {
                    row.add(-1)
                } else {
                    row.add(char.digitToInt())
                }

                if (char == '0') {
                    startingPositions.add(Pair(row.size - 1, grid.size))
                }
            }

            grid.add(row)
        }

        return startingPositions.sumOf { findTrails(grid, it) }
    }

    private fun findTrails(grid: List<List<Int>>, startingPosition: Pair<Int, Int>): Int {
        val queue = mutableListOf(startingPosition)
        val finalPositions = mutableSetOf<Pair<Int, Int>>()

        while (queue.isNotEmpty()) {
            val currentPosition = queue.removeFirst()

            for (direction in DIRECTIONS) {
                val nextPosition =
                    Pair(currentPosition.first + direction.first, currentPosition.second + direction.second)

                if (!isPositionInBound(nextPosition, grid)) {
                    continue
                }

                if (grid[nextPosition.second][nextPosition.first] != grid[currentPosition.second][currentPosition.first] + 1) {
                    continue
                }

                if (grid[nextPosition.second][nextPosition.first] == 9) {
                    finalPositions.add(nextPosition)
                } else {
                    queue.add(nextPosition)
                }
            }
        }

        return finalPositions.count()
    }

    private fun isPositionInBound(currentPosition: Pair<Int, Int>, grid: List<List<Int>>): Boolean {
        if (currentPosition.first < 0 || currentPosition.first >= grid[0].size) {
            return false
        }
        if (currentPosition.second < 0 || currentPosition.second >= grid.size) {
            return false
        }
        return true
    }

    override fun solveSecondTask(input: BufferedReader): Int {
        val grid = mutableListOf<List<Int>>()

        val startingPositions = mutableListOf<Pair<Int, Int>>()

        input.forEachLine { line ->
            val row = mutableListOf<Int>()

            line.forEach { char ->
                if (char == '.') {
                    row.add(-1)
                } else {
                    row.add(char.digitToInt())
                }

                if (char == '0') {
                    startingPositions.add(Pair(row.size - 1, grid.size))
                }
            }

            grid.add(row)
        }

        var result = 0

        for (startingPosition in startingPositions) {
            val paths = mutableListOf<List<Pair<Int, Int>>>()
            val path = mutableListOf(startingPosition)

            findDistinctTrails(startingPosition, path, paths, grid)
            result += paths.size
        }

        return result
    }

    private fun findDistinctTrails(
        currentPosition: Pair<Int, Int>,
        path: MutableList<Pair<Int, Int>>,
        paths: MutableList<List<Pair<Int, Int>>>,
        grid: List<List<Int>>,
    ) {
        if (grid[currentPosition.second][currentPosition.first] == 9) {
            paths.add(path)
            return
        }

        for (direction in DIRECTIONS) {
            val nextPosition =
                Pair(currentPosition.first + direction.first, currentPosition.second + direction.second)

            if (!isPositionInBound(nextPosition, grid)) {
                continue
            }

            if (grid[nextPosition.second][nextPosition.first] != grid[currentPosition.second][currentPosition.first] + 1) {
                continue
            }

            path.add(nextPosition)
            findDistinctTrails(nextPosition, path, paths, grid)
            path.removeAt(path.size - 1)
        }
    }
}
