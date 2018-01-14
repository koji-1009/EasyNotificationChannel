# EasyNotificationChannel

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