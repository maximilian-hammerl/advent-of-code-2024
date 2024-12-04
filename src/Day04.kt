class Day04 : AdventOfCodeChallenge<Int, Int>(4) {
    override fun solveFirstTask(): Int {
        val grid = mutableListOf<CharArray>()

        readInput().forEachLine { line ->
            val row = line.toCharArray()
            grid.add(row)
        }

        var occurrences = 0

        for (x in grid.indices) {
            val row = grid[x]
            for (y in row.indices) {
                val char = row[y]

                if (char != 'X') {
                    continue
                }

                // Horizontal, forwards
                if (y + 3 < row.size && row[y + 1] == 'M' && row[y + 2] == 'A' && row[y + 3] == 'S') {
                    occurrences++
                }
                // Horizontal, backwards
                if (y - 3 >= 0 && row[y - 1] == 'M' && row[y - 2] == 'A' && row[y - 3] == 'S') {
                    occurrences++
                }
                // Vertical, downwards
                if (x + 3 < grid.size && grid[x + 1][y] == 'M' && grid[x + 2][y] == 'A' && grid[x + 3][y] == 'S') {
                    occurrences++
                }
                // Vertical, upwards
                if (x - 3 >= 0 && grid[x - 1][y] == 'M' && grid[x - 2][y] == 'A' && grid[x - 3][y] == 'S') {
                    occurrences++
                }

                // Diagonal
                if (x + 3 < grid.size && y + 3 < row.size && grid[x + 1][y + 1] == 'M' && grid[x + 2][y + 2] == 'A' && grid[x + 3][y + 3] == 'S') {
                    occurrences++
                }
                if (x - 3 >= 0 && y - 3 >= 0 && grid[x - 1][y - 1] == 'M' && grid[x - 2][y - 2] == 'A' && grid[x - 3][y - 3] == 'S') {
                    occurrences++
                }
                if (x + 3 < grid.size && y - 3 >= 0 && grid[x + 1][y - 1] == 'M' && grid[x + 2][y - 2] == 'A' && grid[x + 3][y - 3] == 'S') {
                    occurrences++
                }
                if (x - 3 >= 0 && y + 3 < row.size && grid[x - 1][y + 1] == 'M' && grid[x - 2][y + 2] == 'A' && grid[x - 3][y + 3] == 'S') {
                    occurrences++
                }
            }
        }

        return occurrences
    }

    override fun solveSecondTask(): Int {
        val grid = mutableListOf<CharArray>()

        readInput().forEachLine { line ->
            val row = line.toCharArray()
            grid.add(row)
        }

        var occurrences = 0

        for (x in grid.indices) {
            val row = grid[x]
            for (y in row.indices) {
                val char = row[y]

                if (char != 'A') {
                    continue
                }

                if (x - 1 < 0 || x + 1 >= grid.size || y - 1 < 0 || y + 1 >= row.size) {
                    continue
                }

                val letters = listOf(grid[x + 1][y + 1], grid[x + 1][y - 1], grid[x - 1][y + 1], grid[x - 1][y - 1])

                if (letters.count { it == 'M' } == 2 && letters.count { it == 'S' } == 2 && grid[x + 1][y + 1] != grid[x - 1][y - 1]) {
                    occurrences++
                }
            }
        }

        return occurrences
    }
}
