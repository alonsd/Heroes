package com.heroes.core.extensions

fun sparseListOf(vararg ranges: IntRange): List<Int> = ranges.flatMap { it }