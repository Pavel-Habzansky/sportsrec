package com.pavelhabzansky.sportsrec.core.timber

import android.util.Log
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import timber.log.Timber
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

class FileLogTree(
    private val root: File
) : Timber.Tree() {

    private val logTimestampFormat = SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss.SSS",
        Locale.getDefault()
    )

    private val filename = "log.txt"

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val logFile = generateFile() ?: run {
            Log.w("FileLogTree", "Couldn't create log file")
            return
        }

        val logLevel = mapLogLevel(priority)
        val timestamp = logTimestampFormat.format(Date())
        val writer = FileWriter(logFile, true)
        writer.appendLine("$timestamp ${logLevel.identifier} $message ${t ?: ""}")
        writer.flush()
        writer.close()

        if (t != null) {
            Firebase.crashlytics.recordException(t)
            Firebase.crashlytics.sendUnsentReports()
        }
    }

    private fun generateFile(): File? {
        var logFile: File? = null

        var dirExists = true
        if (!root.exists()) {
            dirExists = root.mkdirs()
        }

        if (dirExists) {
            logFile = File(root, filename)
            if (!logFile.exists()) {
                logFile.createNewFile()
            }
        }

        return logFile
    }
}