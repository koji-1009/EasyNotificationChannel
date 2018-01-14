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

    /**
     * Handle {@link Intent#ACTION_LOCALE_CHANGED} intent.
     * Re-create group's and channel's by current local resource.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun localeChange(context: Context) {
        Log.i(TAG, "receive local change intent")
        Completable
                .create {
                    Util.updateLocal(context)
                    it.onComplete()
                }
                .subscribeOn(Schedulers.newThread())
                .subscribe({ Log.i(TAG, "finish") }, { Log.e(TAG, "incomplete", it) })
    }

    companion object {
        const private val TAG = "LocaleReceiver"
    }
}