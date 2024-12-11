import java.io.BufferedReader

class Day11 : AdventOfCodeChallenge<Int, Long>(11) {
    override fun solveFirstTask(input: BufferedReader): Int {
        val stones = input.readLine().split(' ').map { it.toLong() }.toMutableList()

        for (i in 0..<25) {
            var j = 0
            var numberStones = stones.size

            while (j < numberStones) {
                val stone = stones[j]

                if (stone == 0L) {
                    stones[j] = 1L
                    j++
                    continue
                }

                val stoneToString = stone.toString()
                if (stoneToString.length % 2 == 0) {
                    val mid = stoneToString.length / 2
                    stones[j] = stoneToString.substring(0, mid).toLong()
                    stones.add(j + 1, stoneToString.substring(mid).toLong())

                    j += 2
                    numberStones++
                    continue
                }

                stones[j] = stone * 2024
                j++
            }
        }

        return stones.size
    }

    override fun solveSecondTask(input: BufferedReader): Long {
        val stones = input.readLine().split(' ').map { it.toLong() }

        var result: Long = 0

        val cache = mutableMapOf<Pair<Long, Int>, Long>()

        for (stone in stones) {
            result += solveStone(stone, 75, cache)
        }

        return result
    }

    private fun solveStone(stone: Long, times: Int, cache: MutableMap<Pair<Long, Int>, Long>): Long {
        val cachedValue = cache[Pair(stone, times)]
        if (cachedValue != null) {
            return cachedValue
        }

        if (times == 1) {
            return blink(stone).size.toLong()
        }

        val stones = blink(stone)

        val res = stones.map { s -> solveStone(s, times - 1, cache) }
        val sum = res.sum()

        cache[Pair(stone, times)] = sum

        return sum;
    }

    private fun blink(stone: Long): List<Long> {
        if (stone == 0L) {
            return listOf(1L)
        }

        val stoneToString = stone.toString()
        if (stoneToString.length % 2 == 0) {
            val mid = stoneToString.length / 2
            return listOf(
                stoneToString.substring(0, mid).toLong(), stoneToString.substring(mid).toLong()
            )
        }

        return listOf(stone * 2024)
    }
}