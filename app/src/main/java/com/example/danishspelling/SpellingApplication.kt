package com.example.danishspelling

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SpellingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Application-level initialization can go here
    }
}
