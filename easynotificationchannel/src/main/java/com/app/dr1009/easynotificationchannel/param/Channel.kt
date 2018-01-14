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

package com.app.dr1009.easynotificationchannel.param

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import com.app.dr1009.easynotificationchannel.getResString

@RequiresApi(api = Build.VERSION_CODES.O)
internal class Channel(val id: String,
                       private val nameRes: String,
                       private val descriptionRes: String? = null,
                       private val importance: Int = 3,
                       private val groupId: String? = null) {

    fun create(context: Context): NotificationChannel = NotificationChannel(id, context.getResString(nameRes), notificationImportance())
            .apply {
                if (descriptionRes != null) {
                    description = context.getResString(descriptionRes)
                }

                group = groupId
            }

    private fun notificationImportance() = when (importance) {
        1 -> NotificationManager.IMPORTANCE_MIN
        2 -> NotificationManager.IMPORTANCE_LOW
        3 -> NotificationManager.IMPORTANCE_DEFAULT
        4 -> NotificationManager.IMPORTANCE_HIGH
        5 -> NotificationManager.IMPORTANCE_MAX
        else -> NotificationManager.IMPORTANCE_DEFAULT
    }
}

