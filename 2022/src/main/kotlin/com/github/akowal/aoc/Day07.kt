package com.github.akowal.aoc

class Day07 {
    private val root = loadFileTree()

    fun solvePart1(): Long {
        val dirs = findAll(root) { it.size <= 100000 }
        return dirs.sumOf { it.size }
    }

    fun solvePart2(): Long {
        val spaceToFree = 30000000 - (70000000 - root.size)
        val dirs = findAll(root) { it.size >= spaceToFree }
        return dirs.minOf { it.size }
    }

    private fun findAll(dir: Dir, predicate: (Dir) -> Boolean): List<Dir> {
        val result = mutableListOf<Dir>()

        fun traverse(d: Dir) {
            if (predicate(d)) {
                result += d
            }
            d.dirs.values.forEach { traverse(it) }
        }

        traverse(dir)
        return result
    }

    private fun loadFileTree(): Dir {
        val root = Dir("/")
        var pwd = root
        val input = inputScanner("day07")

        while (input.hasNextLine()) {
            val s = input.nextLine()
            when {
                s.startsWith("$ cd ") -> {
                    pwd = when (val dst = s.substringAfter("$ cd ")) {
                        ".." -> pwd.parent
                        "/" -> root
                        else -> pwd.getOrCreateDir(dst)
                    }
                }

                s == "$ ls" -> {
                    while (input.hasNextLine() && !input.hasNext("\\$")) {
                        val entry = input.nextLine()
                        if (!entry.startsWith("dir")) {
                            val name = entry.substringAfter(' ')
                            val size = entry.substringBefore(' ').toLong()
                            pwd.files += File(name, size)
                        }
                    }
                }
            }
        }

        return root
    }

    private data class File(
        val name: String,
        val size: Long,
    )

    private class Dir(
        val name: String,
    ) {
        lateinit var parent: Dir
        val dirs = mutableMapOf<String, Dir>()
        val files = mutableListOf<File>()
        val size: Long by lazy { files.sumOf { it.size } + dirs.values.sumOf { it.size } }

        fun getOrCreateDir(name: String): Dir {
            return dirs.computeIfAbsent(name) { Dir(name).also { it.parent = this } }
        }
    }
}

fun main() {
    val solution = Day07()
    println(solution.solvePart1())
    println(solution.solvePart2())
}
