package com.github.akowal.aoc

import java.io.File

fun String.toIntArray(delimiter: Char = ',') = split(delimiter).map { it.toInt() }.toIntArray()

fun getFile(name: String) = Thread.currentThread().contextClassLoader.getResource(name)
    ?.let { File(it.toURI()) }
    ?: error("can't open file: $name")
