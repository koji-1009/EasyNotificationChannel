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
import android.os.Build
import android.util.Log
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

object EasyNotificationChannel {

    fun init(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.i(TAG, "init EasyNotificationChannel")
            Completable
                    .create {
                        if (Util.checkUpdate(context)) {
                            Log.i(TAG, "app is upgraded")
                            Util.register(context)
                        }

                        it.onComplete()
                    }
                    .subscribeOn(Schedulers.newThread())
                    .subscribe({ Log.i(TAG, "finish") }, { Log.e(TAG, "incomplete", it) })
        }
    }

    private const val TAG = "EasyNotificationChannel"
}
