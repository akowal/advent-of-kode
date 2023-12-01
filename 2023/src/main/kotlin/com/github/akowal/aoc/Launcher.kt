package com.github.akowal.aoc

import java.io.File
import kotlin.time.measureTimedValue

class Launcher(
    private val name: String,
    private val ctor: (File) -> Problem<*>,
) {
    private val input = getFile("$name.txt")

    fun run() {
        println("Testing part 1...")
        test(1, Problem<*>::solve1)

        print("Solving part 1...")
        val problem = ctor(input)
        measureTimedValue { problem.solve1() }.let { (output, t) ->
            println("completed in $t")
            println("Result: $output")
        }

        println("Testing part 2...")
        test(2, Problem<*>::solve2)

        println("Solving part 2...")
        measureTimedValue { problem.solve2() }.let { (output, t) ->
            println("completed in $t")
            println("Result: $output")
        }
    }

    private fun test(part: Int, fn: (Problem<*>) -> Any?) {
        val test = ctor(getFile("$name-test-$part.txt"))
        val expectedOutput = getFile("$name-test-$part-out.txt").readLines().single()
        val actualOutput = fn(test).toString()
        require(expectedOutput == actualOutput) {
            "Test failed: expected=${expectedOutput}, actual=$actualOutput"
        }
    }
}
