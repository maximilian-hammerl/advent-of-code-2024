import java.io.BufferedReader

class Day08 : AdventOfCodeChallenge<Int, Int>(8) {
    override fun solveFirstTask(input: BufferedReader): Int {
        val antennasByFrequencies = mutableMapOf<Char, MutableSet<Pair<Int, Int>>>()

        var numberColumns = 0

        var currentRow = 0
        input.forEachLine { line ->

            line.withIndex().forEach { (index, char) ->
                if (char != '.') {
                    antennasByFrequencies.getOrPut(char) { mutableSetOf() }.add(Pair(currentRow, index))
                }
            }

            numberColumns = line.length
            currentRow++
        }
        val numberRows = currentRow

        var numberAntinodes = 0

        for (row in 0..<numberRows) {
            for (column in 0..<numberColumns) {
                for (antennas in antennasByFrequencies.values) {
                    if (antennas.size <= 1) {
                        continue
                    }

                    if (findAntinodeForFirstTask(Pair(row, column), antennas)) {
                        numberAntinodes++
                        break
                    }
                }
            }
        }

        return numberAntinodes
    }

    override fun solveSecondTask(input: BufferedReader): Int {
        val antennasByFrequencies = mutableMapOf<Char, MutableSet<Pair<Int, Int>>>()

        var numberColumns = 0

        var currentRow = 0
        input.forEachLine { line ->

            line.withIndex().forEach { (index, char) ->
                if (char != '.') {
                    antennasByFrequencies.getOrPut(char) { mutableSetOf() }.add(Pair(currentRow, index))
                }
            }

            numberColumns = line.length
            currentRow++
        }
        val numberRows = currentRow

        var numberAntinodes = 0

        for (row in 0..<numberRows) {
            for (column in 0..<numberColumns) {
                for (antennas in antennasByFrequencies.values) {
                    if (antennas.size <= 1) {
                        continue
                    }

                    if (findAntinodeForSecondTask(Pair(row, column), antennas)) {
                        numberAntinodes++
                        break
                    }
                }
            }
        }

        return numberAntinodes
    }

    private fun findAntinodeForFirstTask(position: Pair<Int, Int>, antennas: Set<Pair<Int, Int>>): Boolean {
        for (first in antennas) {
            for (second in antennas) {
                if (first == second) {
                    continue
                }

                if (position.first - first.first == first.first - second.first && position.second - first.second == first.second - second.second) {
                    return true
                }

                if (position.first - second.first == second.first - first.first && position.second - second.second == second.second - first.second) {
                    return true
                }
            }
        }

        return false
    }

    private fun findAntinodeForSecondTask(position: Pair<Int, Int>, antennas: Set<Pair<Int, Int>>): Boolean {
        for (first in antennas) {
            if (first == position) {
                return true
            }

            for (second in antennas) {
                if (second == position) {
                    return true
                }

                if (first == second) {
                    continue
                }

                val xDistance = (first.first - second.first).toDouble()
                val yDistance = (first.second - second.second).toDouble()

                if ((first.first - position.first) / xDistance == (first.second - position.second) / yDistance) {
                    return true
                }

                if ((position.first - first.first) / xDistance == (position.second - first.second) / yDistance) {
                    return true
                }
            }
        }

        return false
    }
}