/*
 * Copyright (c) 2017 Koji Wakamiya.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.app.dr1009.easynotificationchannel

import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.support.annotation.RequiresApi
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.util.*

object ChannelUtil {

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun register(context: Context, id: String, nameResId: Int) {
        val easyChannel = EasyChannel(channelId = id, nameResId = nameResId)

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(easyChannel.createNotificationChannel(context))

        val prefs = getPreference(context)
        val channelIdSet = prefs.getStringSet(CHANNEL_ID_SET, emptySet()).plus(easyChannel.channelId)

        val json = getAdapter().toJson(easyChannel)
        getPreference(context).edit()
                .putString(easyChannel.channelId, json)
                .putStringSet(CHANNEL_ID_SET, channelIdSet)
                .apply()
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun register(context: Context, easyChannel: EasyChannel) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(easyChannel.createNotificationChannel(context))

        val prefs = getPreference(context)
        val channelIdSet = prefs.getStringSet(CHANNEL_ID_SET, emptySet()).plus(easyChannel.channelId)

        val json = getAdapter().toJson(easyChannel)
        getPreference(context).edit()
                .putString(easyChannel.channelId, json)
                .putStringSet(CHANNEL_ID_SET, channelIdSet)
                .apply()
    }

    /**
     * Return registered {@link EasyChannel} instance by channelId, if the chennelId is not registered return {@link null}
     */
    fun getChannel(context: Context, channelId: String): EasyChannel? {
        val prefs = getPreference(context)
        if (!prefs.getStringSet(CHANNEL_ID_SET, emptySet()).contains(channelId)) {
            return null
        }

        val channelJson = prefs.getString(channelId, "")

        return getAdapter().fromJson(channelJson)
    }

    internal fun easyChannels(context: Context): List<EasyChannel> {
        val prefs = getPreference(context)
        val channelIdSet = prefs.getStringSet(CHANNEL_ID_SET, Collections.emptySet())
        val channelList = listOf<EasyChannel>()

        val adapter = Moshi.Builder().build().adapter(EasyChannel::class.java)
        channelIdSet.forEach { id ->
            val json = prefs.getString(id.toString(), "")
            val easyChannel = adapter.fromJson(json)
            channelList.plus(easyChannel)
        }

        return channelList
    }

    private fun getPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

    private fun getAdapter(): JsonAdapter<EasyChannel> {
        return Moshi.Builder().build().adapter(EasyChannel::class.java)
    }

    const private val NAME = "com.app.dr1009.easynotificationchannel"
    const private val CHANNEL_ID_SET = "channel_id_set"
}

