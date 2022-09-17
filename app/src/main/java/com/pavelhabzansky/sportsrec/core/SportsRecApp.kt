package com.pavelhabzansky.sportsrec.core

import android.app.Application
import com.google.firebase.FirebaseApp
import com.pavelhabzansky.sportsrec.core.timber.FileLogTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class SportsRecApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(FileLogTree(filesDir))

        FirebaseApp.initializeApp(this)
    }
}