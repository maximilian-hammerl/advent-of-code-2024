import java.io.BufferedReader

class Day05 : AdventOfCodeChallenge<Int, Int>(5) {
    override fun solveFirstTask(input: BufferedReader): Int {
        val rulesByPageNumbers = mutableMapOf<Int, MutableList<Int>>()

        var result = 0

        var isCollectingRules = true

        input.forEachLine { line ->
            if (line == "") {
                isCollectingRules = false
            } else if (isCollectingRules) {
                val parts = line.split('|')
                val from = parts[0].toInt()
                val to = parts[1].toInt()

                rulesByPageNumbers.getOrPut(from) { mutableListOf() }.add(to)
            } else {
                val pages = line.split(',').map { it.toInt() }

                if (isInRightOrder(pages, rulesByPageNumbers)) {
                    result += pages[pages.size / 2]
                }
            }
        }

        return result
    }

    override fun solveSecondTask(input: BufferedReader): Int {
        val rulesByPageNumbers = mutableMapOf<Int, MutableList<Int>>()

        var result = 0

        var isCollectingRules = true

        input.forEachLine { line ->
            if (line == "") {
                isCollectingRules = false
            } else if (isCollectingRules) {
                val parts = line.split('|')
                val from = parts[0].toInt()
                val to = parts[1].toInt()

                rulesByPageNumbers.getOrPut(from) { mutableListOf() }.add(to)
            } else {
                val pages = line.split(',').map { it.toInt() }.toMutableList()

                var hasRequiredReordering = false

                while (true) {
                    if (isInRightOrder(pages, rulesByPageNumbers)) {
                        break
                    }

                    for (i in pages.indices) {
                        if (i == 0) {
                            continue
                        }

                        val page = pages[i]
                        val rules = rulesByPageNumbers[page] ?: continue

                        var hasReordered = false

                        for (j in pages.indices) {
                            if (j >= i) {
                                break
                            }
                            val previousPage = pages[j]

                            if (rules.contains(previousPage)) {
                                pages.add(i + 1, previousPage)
                                pages.removeAt(j)
                                hasReordered = true
                                break
                            }
                        }

                        if (hasReordered) {
                            hasRequiredReordering = true
                            break
                        }
                    }
                }

                if (hasRequiredReordering) {
                    result += pages[pages.size / 2]
                }
            }
        }

        return result
    }

    private fun isInRightOrder(pages: List<Int>, rulesByPageNumbers: Map<Int, List<Int>>): Boolean {
        return pages.withIndex().all { (index, page) ->
            if (index == 0) {
                return@all true
            }

            val rules = rulesByPageNumbers[page] ?: return@all true

            var isInRightPosition = true
            for ((previousIndex, previousPage) in pages.withIndex()) {
                if (previousIndex >= index) {
                    break
                }
                if (rules.contains(previousPage)) {
                    isInRightPosition = false
                    break
                }
            }

            isInRightPosition
        }
    }
}
