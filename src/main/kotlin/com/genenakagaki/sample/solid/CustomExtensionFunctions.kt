package com.genenakagaki.sample.solid

fun Any?.int(): Int {
    return this as Int
}

fun Any?.str(): String {
    return this as String
}