import java.io.BufferedReader

class Day09 : AdventOfCodeChallenge<Long, Long>(9) {
    override fun solveFirstTask(input: BufferedReader): Long {
        val hardDrive = input.readLines().joinToString("")

        var currentIdNumber = 0
        var isFile = true

        val blocks = mutableListOf<String>()

        for (char in hardDrive) {
            if (isFile) {

                addMultipleTimes(blocks, currentIdNumber, char.digitToInt())
                currentIdNumber++
            } else {
                addMultipleTimes(blocks, '.', char.digitToInt())
            }
            isFile = !isFile
        }

        for (i in 0..<blocks.size - 1) {
            val block = blocks[i]
            if (block == ".") {
                val j = blocks.indexOfLast { it != "." }
                if (j < i) {
                    break
                }
                blocks[i] = blocks[j]
                blocks[j] = "."
            }
        }

        return blocks.withIndex().sumOf { (index, block) ->
            if (block == ".") {
                return@sumOf 0L
            }
            index * block.toLong()
        }
    }

    override fun solveSecondTask(input: BufferedReader): Long {
        val hardDrive = input.readLines().joinToString("")

        var currentIdNumber = 0
        var isFile = true

        val blocks = mutableListOf<Pair<Int?, Int>>()

        for (char in hardDrive) {
            val digit = char.digitToInt()
            if (isFile) {
                if (digit > 0) {
                    blocks.add(Pair(currentIdNumber, digit))
                }
                currentIdNumber++
            } else if (digit > 0) {
                blocks.add(Pair(null, digit))
            }
            isFile = !isFile
        }

        currentIdNumber--

        while (currentIdNumber >= 0) {
            val i = blocks.indexOfLast { it.first == currentIdNumber }

            currentIdNumber--

            val block = blocks[i]

            val j = blocks.indexOfFirst { it.first == null && it.second >= block.second }

            if (j == -1 || j > i) {
                continue
            }

            val emptyBlockToSwap = blocks[j]

            if (block.second == emptyBlockToSwap.second) {
                addNullBlocks(blocks, i, block.second)
                blocks[j] = Pair(block.first, block.second)

                continue
            }


            addNullBlocks(blocks, i, block.second)

            blocks.add(j, Pair(block.first, block.second))
            blocks[j + 1] = Pair(null, emptyBlockToSwap.second - block.second)
        }

        var index = 0
        return blocks.sumOf { block ->
            val fileIdNumber = block.first
            if (fileIdNumber == null) {
                index += block.second
                return@sumOf 0L
            }

            var result: Long = 0
            for (x in 0..<block.second) {
                result += (fileIdNumber * index)
                index++
            }

            result
        }
    }

    private fun addNullBlocks(
        blocks: MutableList<Pair<Int?, Int>>,
        j: Int,
        length: Int,
    ) {
        val previousBlock = blocks[j - 1]
        val nextBlock = blocks.getOrNull(j + 1)

        if (nextBlock != null && nextBlock.first == null) {
            if (previousBlock.first == null) {
                blocks[j - 1] = Pair(null, previousBlock.second + length + nextBlock.second)

                blocks.removeAt(j + 1)
                blocks.removeAt(j)
                return
            }

            blocks[j + 1] = Pair(null, nextBlock.second + length)
            blocks.removeAt(j)
            return
        }

        if (previousBlock.first == null) {
            blocks[j - 1] = Pair(null, previousBlock.second + length)
            blocks.removeAt(j)
            return
        }

        blocks[j] = Pair(null, length)
    }

    private fun addMultipleTimes(blocks: MutableList<String>, char: Char, times: Int): MutableList<String> {
        repeat(times) {
            blocks.add(char.toString())
        }
        return blocks
    }

    private fun addMultipleTimes(blocks: MutableList<String>, int: Int, times: Int): MutableList<String> {
        repeat(times) {
            blocks.add(int.toString())
        }
        return blocks
    }

    private fun addMultipleTimes(blocks: MutableList<String>, long: Long, times: Int): MutableList<String> {
        repeat(times) {
            blocks.add(long.toString())
        }
        return blocks
    }
}