/*
 * Copyright (c) 2018 Koji Wakamiya.
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

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import com.app.dr1009.easynotificationchannel.param.Channel
import com.app.dr1009.easynotificationchannel.param.Group

internal object Util {

    const private val TAG = "Util"
    // JSON
    const private val CHANNEL_DIR = "easynotification/channel.json"
    const private val GROUP_DIR = "easynotification/group.json"

    // Shared Preference
    const private val NAME = "com.app.dr1009.easynotificationchannel"
    const private val CHANNEL_ID_SET = "channel_id_set"
    const private val GROUP_ID_SET = "group_id_set"
    const private val APP_VER = "app_ver"

    internal fun checkUpdate(context: Context) = getPreference(context).getInt(APP_VER, 0) < BuildConfig.VERSION_CODE

    @RequiresApi(Build.VERSION_CODES.O)
    internal fun register(context: Context) {
        val manager = context.getNotificationManager()
        val prefs = getPreference(context)

        val registerGroupId = mutableSetOf<String>()
        getGroupList(context).forEach {
            manager.createNotificationChannelGroup(it.create(context))
            registerGroupId.add(it.id)
        }

        prefs.getStringSet(GROUP_ID_SET, emptySet())
                .filter { !registerGroupId.contains(it) }
                .forEach { manager.deleteNotificationChannelGroup(it) }

        val registerChannelId = mutableSetOf<String>()
        getChannelList(context).forEach {
            manager.createNotificationChannel(it.create(context))
            registerChannelId.add(it.id)
        }

        prefs.getStringSet(CHANNEL_ID_SET, emptySet())
                .filter { !registerChannelId.contains(it) }
                .forEach { manager.deleteNotificationChannel(it) }

        prefs.edit()
                .apply {
                    // Set registered group and channel ids
                    putStringSet(GROUP_ID_SET, registerGroupId)
                    putStringSet(CHANNEL_ID_SET, registerChannelId)

                    // Set registered app version-code
                    putInt(APP_VER, BuildConfig.VERSION_CODE)
                }
                .apply()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    internal fun updateLocal(context: Context) {
        val manager = context.getNotificationManager()

        getGroupList(context).forEach {
            Log.i(TAG, "update channel group id = ${it.id}")
            manager.createNotificationChannelGroup(it.create(context))
        }

        getChannelList(context).forEach {
            Log.i(TAG, "update channel id = ${it.id}")
            manager.createNotificationChannel(it.create(context))
        }
    }

    private fun getGroupList(context: Context): List<Group> {
        context.assets.open(GROUP_DIR).use {
            val json = StringBuilder()
            it.reader().forEachLine { line -> json.append(line) }

            return Adapter.groupList().fromJson(json.toString())?.list ?: emptyList()
        }
    }

    private fun getChannelList(context: Context): List<Channel> {
        context.assets.open(CHANNEL_DIR).use {
            val json = StringBuilder()
            it.reader().forEachLine { line -> json.append(line) }

            return Adapter.channelList().fromJson(json.toString())?.list ?: emptyList()
        }
    }

    private fun getPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

}