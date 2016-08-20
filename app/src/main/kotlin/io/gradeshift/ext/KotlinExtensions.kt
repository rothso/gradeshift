package io.gradeshift.ext

fun <A, B, R> Pair<A, B>.spread(f: (A, B) -> R) = f(first, second)