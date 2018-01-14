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
import android.text.TextUtils
import android.util.Log

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
        val editor = prefs.edit()

        val registerGroupId = mutableSetOf<String>()
        context.assets.open(GROUP_DIR).use {
            var json = ""
            it.reader().forEachLine { line -> json += line }

            Adapter.groupList().fromJson(json)?.list?.forEach {
                manager.createNotificationChannelGroup(it.create(context))
                editor.putString(it.id, Adapter.group().toJson(it))

                registerGroupId.add(it.id)
            }
        }

        prefs.getStringSet(GROUP_ID_SET, emptySet())
                .filter { !registerGroupId.contains(it) }
                .forEach {
                    manager.deleteNotificationChannelGroup(it)
                    editor.remove(it)
                }
        editor.putStringSet(GROUP_ID_SET, registerGroupId)

        val registerChannelId = mutableSetOf<String>()
        context.assets.open(CHANNEL_DIR).use {
            var json = ""
            it.reader().forEachLine { line -> json += line }

            Adapter.channelList().fromJson(json)?.list?.forEach {
                manager.createNotificationChannel(it.create(context))
                editor.putString(it.id, Adapter.channel().toJson(it))

                registerChannelId.add(it.id)
            }
        }

        prefs.getStringSet(CHANNEL_ID_SET, emptySet())
                .filter { !registerChannelId.contains(it) }
                .forEach {
                    manager.deleteNotificationChannel(it)
                    editor.remove(it)
                }
        editor.putStringSet(CHANNEL_ID_SET, registerChannelId)

        // Set registered app version-code
        editor.putInt(APP_VER, BuildConfig.VERSION_CODE)

        editor.apply()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    internal fun updateLocal(context: Context) {
        val manager = context.getNotificationManager()
        val prefs = getPreference(context)

        prefs.getStringSet(GROUP_ID_SET, emptySet())
                .forEach {
                    Log.i(TAG, "update channel group id = $it")
                    val json = prefs.getString(it, null)
                    if (!TextUtils.isEmpty(json)) {
                        manager.createNotificationChannelGroup(Adapter.group().fromJson(json)?.create(context))
                    }
                }

        prefs.getStringSet(CHANNEL_ID_SET, emptySet())
                .forEach {
                    Log.i(TAG, "update channel id = $it")
                    val json = prefs.getString(it, null)
                    if (!TextUtils.isEmpty(json)) {
                        manager.createNotificationChannel(Adapter.channel().fromJson(json)?.create(context))
                    }
                }
    }

    private fun getPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

}