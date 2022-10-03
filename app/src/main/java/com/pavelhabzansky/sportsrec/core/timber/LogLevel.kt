package com.pavelhabzansky.sportsrec.core.timber

import android.util.Log

enum class LogLevel(val identifier: String) {
    DEBUG("[DEBUG]"),
    ERROR("[ERROR]"),
    INFO("[INFO]"),
    VERBOSE("[VERBOSE]"),
    WARN("[WARN]"),
}

fun mapLogLevel(priority: Int): LogLevel {
    return when (priority) {
        Log.DEBUG -> LogLevel.DEBUG
        Log.ERROR -> LogLevel.ERROR
        Log.INFO -> LogLevel.INFO
        Log.VERBOSE -> LogLevel.VERBOSE
        Log.WARN -> LogLevel.WARN
        else -> LogLevel.VERBOSE
    }
}