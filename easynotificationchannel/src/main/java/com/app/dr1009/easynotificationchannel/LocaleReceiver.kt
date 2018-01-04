package com.app.dr1009.easynotificationchannel

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class LocaleReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            when (intent?.action) {
                // Change title and description
                Intent.ACTION_LOCALE_CHANGED -> localeChange(context)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun localeChange(context: Context) {
        Log.i(TAG, "receive local change intent")
        Completable
                .create {
                    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    ChannelUtil.easyChannels(context).forEach { channel ->
                        Log.i(TAG, "update channel id = ${channel.channelId}")
                        manager.createNotificationChannel(channel.createNotificationChannel(context))
                    }

                    it.onComplete()
                }
                .subscribeOn(Schedulers.newThread())
                .subscribe {
                    Log.i(TAG, "finish locale change receiver")
                }
    }

    companion object {
        const private val TAG = "LocaleReceiver"
    }
}