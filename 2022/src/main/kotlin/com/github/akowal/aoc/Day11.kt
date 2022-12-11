package com.github.akowal.aoc

import com.github.akowal.aoc.Day11.Op

class Day11 {
    fun solvePart1(): Long {
        return monkeyBusiness(20, 3)
    }

    fun solvePart2(): Long {
        return monkeyBusiness(10000, 1)
    }

    private fun monkeyBusiness(rounds: Int, relief: Long): Long {
        val monkeys = load()
        val divCommonMul = monkeys.fold(1L) { c, m -> c * m.div }
        repeat(rounds) {
            monkeys.forEach { m ->
                while (m.items.isNotEmpty()) {
                    m.inspected++
                    val item = m.items.removeFirst()
                    val worryLevel = (m.op.calc(item) / relief) % divCommonMul
                    if (worryLevel % m.div == 0L) {
                        monkeys[m.throwIfTrue].items.add(worryLevel)
                    } else {
                        monkeys[m.throwIfFalse].items.add(worryLevel)
                    }
                }
            }
        }
        return monkeys.map { it.inspected }.sortedDescending().take(2).reduce(Long::times)
    }


    private fun load() = sequence {
        inputScanner("day11").let { input ->
            while (input.hasNextLine()) {
                if (input.nextLine().isEmpty()) {
                    continue
                }
                val startingItems = input.nextLine().removePrefix("  Starting items: ").split(", ").map { it.toLong() }
                val op = input.nextLine().removePrefix("  Operation: new = ").let {
                    when {
                        it == "old * old" -> Op { old -> old * old }
                        it.startsWith("old + ") -> {
                            val x = it.substringAfterLast(' ').toLong()
                            Op { old -> Math.addExact(old, x) }
                        }

                        it.startsWith("old * ") -> {
                            val x = it.substringAfterLast(' ').toLong()
                            Op { old -> Math.multiplyExact(old, x) }
                        }

                        else -> error("xoxoxo")
                    }
                }
                val div = input.nextLine().removePrefix("  Test: divisible by ").toLong()
                val throwIfTrue = input.nextLine().removePrefix("    If true: throw to monkey ").toInt()
                val throwIfFalse = input.nextLine().removePrefix("    If false: throw to monkey ").toInt()
                yield(Monkey(div, op, throwIfTrue, throwIfFalse, startingItems))
            }
        }
    }.toList()

    private fun interface Op {
        fun calc(old: Long): Long
    }

    private class Monkey(
        val div: Long,
        val op: Op,
        val throwIfTrue: Int,
        val throwIfFalse: Int,
        items: List<Long>,
    ) {
        var inspected: Long = 0
        val items: MutableList<Long> = items.toMutableList()
    }
}

fun main() {
    val solution = Day11()
    println(solution.solvePart1())
    println(solution.solvePart2())
}
