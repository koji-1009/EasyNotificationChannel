package com.app.dr1009.easynotificationchanneldemo

import android.app.Application
import android.util.Log

class App : Application() {
    private val TAG = "App"

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, ": ")
    }
}