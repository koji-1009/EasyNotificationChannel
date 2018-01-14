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

import com.app.dr1009.easynotificationchannel.param.ChannelList
import com.app.dr1009.easynotificationchannel.param.GroupList
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi

/**
 * Internal JSON Adapter
 */
internal object Adapter {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    internal fun channelList() = moshi.adapter<ChannelList>(ChannelList::class.java)
    internal fun groupList() = moshi.adapter<GroupList>(GroupList::class.java)
}
