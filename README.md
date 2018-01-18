# EasyNotificationChannel

[![](https://jitpack.io/v/koji-1009/EasyNotificationChannel.svg)](https://jitpack.io/#koji-1009/EasyNotificationChannel)

Register Notification Channel and Notification Channel Group, and change name when user change the MUT's local.

- Create Channel and Group by JSON
- Change Channel' name and description and Group's name when change MUT's local

## How to use

### Group

- id
  - group's id
- nameRes
  - resource name of group's name
  - if you set "@string/group_name_1" at your app, please set "group_name_1"
  
example

```json:Group
{
  "list": [
    {
      "id": "group 1",
      "nameRes": "group_name_1"
    },
    {
      "id": "group 2",
      "nameRes": "group_name_2"
    }
  ]
}
```

### Channel

- id
  - channel's id
- nameRes
  - resource name of channel's name
  - if you set "@string/channel_name_1" at your app, please set "channel_name_1"
- descriptionRes
  - resource name of channel's description, if no description text, please ignore this param
  - if you set "@string/channel_description_1" at your app, please set "channel_description_1"
- importance
  - Notification importance
  - default value is 3
  - if you ignore this param, EasyNotificationChannel set default value
- groupId
  - channel's group id

```json:Channel
{
  "list": [
    {
      "id": "channel 1",
      "nameRes": "name_1",
      "descriptionRes": "description_1",
      "importance": 3,
      "groupId": "group 1"
    },
    {
      "id": "channel 2",
      "nameRes": "name_2",
      "descriptionRes": "description_2",
      "groupId": "group 2"
    }
  ]
}
```

### Change local

Add following snippet to your AndroidManifest.

```xml:AndroidManifest.xml
<receiver android:name="com.app.dr1009.easynotificationchannel.LocaleReceiver">
    <intent-filter>
        <action android:name="android.intent.action.LOCALE_CHANGED" />
    </intent-filter>
</receiver>
```

## Download

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

```groovy:root
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency
```groovy:app
dependencies {
    compile 'com.github.koji-1009:EasyNotificationChannel:x.y.z'
}
```

## License

    Copyright 2018 Koji Wakamiya

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


