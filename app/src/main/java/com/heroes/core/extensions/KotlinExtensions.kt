package com.heroes.core.extensions

fun rangeToIntList(vararg ranges: IntRange): List<Int> = ranges.flatMap { it }