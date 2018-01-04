package com.app.dr1009.easynotificationchanneldemo

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.app.dr1009.easynotificationchannel.ChannelUtil
import com.app.dr1009.easynotificationchannel.EasyChannel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val easyChannel = EasyChannel("channel", R.string.name_1).apply {
            descriptionResId = R.string.description_1
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ChannelUtil.register(context = applicationContext, easyChannel = easyChannel)
        }
    }
}
