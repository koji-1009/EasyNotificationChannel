package com.app.dr1009.easynotificationchanneldemo

import android.app.Application
import com.app.dr1009.easynotificationchannel.EasyNotificationChannel

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        EasyNotificationChannel.install(applicationContext)
    }
}