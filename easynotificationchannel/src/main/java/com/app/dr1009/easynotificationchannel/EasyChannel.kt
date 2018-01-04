package com.app.dr1009.easynotificationchannel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi

class EasyChannel(val channelId: String, private val nameResId: Int, private val importance: Int = EasyChannel.IMPORTANCE_DEFAULT) {

    var descriptionResId: Int = INIT_DESCRIPTION
    var channelGroupId: String? = null
    var isLights: Boolean = false
    var lightsColor: Int = INIT_LIGHTS_COLOR
    var isVibration: Boolean = false
    var isShowBadge: Boolean = false
    var isBypassDnd: Boolean = false
    var notificationVisibility: Int = INIT_LOCKSCREEN_VISIBLITY

    @RequiresApi(Build.VERSION_CODES.O)
    internal fun createNotificationChannel(context: Context): NotificationChannel {
        val channel = NotificationChannel(channelId, context.resources.getString(nameResId), importance)
        channel.apply {
            group = channelGroupId

            if (descriptionResId != INIT_DESCRIPTION) {
                description = context.resources.getString(descriptionResId)
            }

            // lights
            enableLights(isLights)
            lightColor = lightsColor

            // vibration
            enableVibration(isVibration)

            // badge
            setShowBadge(isShowBadge)

            // DND
            setBypassDnd(isBypassDnd)

            // lock screen visibility
            if (notificationVisibility != INIT_LOCKSCREEN_VISIBLITY) {
                lockscreenVisibility = notificationVisibility
            }
        }

        return channel
    }

    companion object {
        /**
         * Value signifying that the user has not expressed an importance.
         *
         * This value is for persisting preferences, and should never be associated with
         * an actual notification.
         */
        val IMPORTANCE_UNSPECIFIED = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationManager.IMPORTANCE_UNSPECIFIED
        } else {
            -1000
        }

        /**
         * A notification with no importance: does not show in the shade.
         */
        val IMPORTANCE_NONE = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationManager.IMPORTANCE_NONE
        } else {
            0
        }

        /**
         * Min notification importance: only shows in the shade, below the fold.  This should
         * not be used with {@link Service#startForeground(int, Notification) Service.startForeground}
         * since a foreground service is supposed to be something the user cares about so it does
         * not make semantic sense to mark its notification as minimum importance.  If you do this
         * as of Android version {@link android.os.Build.VERSION_CODES#O}, the system will show
         * a higher-priority notification about your app running in the background.
         */
        val IMPORTANCE_MIN = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationManager.IMPORTANCE_MIN
        } else {
            1
        }

        /**
         * Low notification importance: shows everywhere, but is not intrusive.
         */
        val IMPORTANCE_LOW = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationManager.IMPORTANCE_LOW
        } else {
            2
        }

        /**
         * Default notification importance: shows everywhere, makes noise, but does not visually
         * intrude.
         */
        val IMPORTANCE_DEFAULT = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationManager.IMPORTANCE_DEFAULT
        } else {
            3
        }

        /**
         * Higher notification importance: shows everywhere, makes noise and peeks. May use full screen
         * intents.
         */
        val IMPORTANCE_HIGH = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationManager.IMPORTANCE_HIGH
        } else {
            4
        }

        /**
         * Unused.
         */
        val IMPORTANCE_MAX = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationManager.IMPORTANCE_MAX
        } else {
            5
        }

        const private val INIT_DESCRIPTION = -1
        const private val INIT_LIGHTS_COLOR = 0
        const private val INIT_LOCKSCREEN_VISIBLITY = -1000
    }
}