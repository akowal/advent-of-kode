package com.github.akowal.aoc

class Day05 {

    fun solvePart1(): String {
        val (stacks, moves) = loadData()
        moves.forEach { m ->
            val from = stacks[m.from]
            val to = stacks[m.to]
            repeat(m.qty) {
                to.add(from.removeLast())
            }
        }

        return stacks.map { it.last() }.joinToString(separator = "")
    }

    fun solvePart2(): String {
        val (stacks, moves) = loadData()
        moves.forEach { m ->
            val from = stacks[m.from]
            val to = stacks[m.to]
            val buf = mutableListOf<Char>()
            repeat(m.qty) {
                buf += from.removeLast()
            }
            buf.reversed().forEach {
                to.add(it)
            }
        }

        return stacks.map { it.last() }.joinToString(separator = "")
    }

    private fun loadData(): Data {
        val stackStr = mutableListOf<String>()
        val moveStr = mutableListOf<String>()

        inputFile("day05").readLines().let { lines ->
            stackStr += lines.takeWhile { it.isNotEmpty() }.toList()
            stackStr.removeLast()
            moveStr += lines.drop(stackStr.size + 2)
        }

        val numOfStacks = stackStr.last().split(' ').size
        val stacks = Array(numOfStacks) { ArrayDeque<Char>() }
        repeat(numOfStacks) { i ->
            val x = i * 4 + 1
            var y = stackStr.lastIndex
            while (y >= 0 && x < stackStr[y].length && stackStr[y][x] != ' ') {
                stacks[i].add(stackStr[y][x])
                y--
            }
        }

        val moves = moveStr.map {
            val arr = it.split(' ')
            Move(
                qty = arr[1].toInt(),
                from = arr[3].toInt() - 1,
                to = arr[5].toInt() - 1,
            )
        }

        return Data(stacks, moves)
    }

    private data class Data(
        val stacks: Array<ArrayDeque<Char>>,
        val moves: List<Move>,
    )

    private data class Move(
        val qty: Int,
        val from: Int,
        val to: Int,
    )
}

fun main() {
    val solution = Day05()
    println(solution.solvePart1())
    println(solution.solvePart2())
}
