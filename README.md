Send Anywhere Android SDK with UI [ ![Download](https://api.bintray.com/packages/estmob/maven/sendanywhere-transfer/images/download.svg) ](https://bintray.com/estmob/maven/sendanywhere-transfer/_latestVersion)
===
The simplest way to Send files Anywhere

# Prequisites
Please issue your API key from following link first:
https://send-anywhere.com/web/page/api

# Setup
Send Anywhere Android SDK is available via both `jcenter()` and `mavenCentral()`.
Just add the following line to your gradle dependency:
```gradle
compile ('com.estmob.android:sendanywhere-transfer:8.7.23@aar') {
	transitive = true
}
```

# Troubleshooting
If you have any problem or questions with Send Anywhere Android SDK, please create new issue(https://github.com/estmob/SendAnywhere-Android-UI-SDK/issues) or contact to our customer center(https://send-anywhere.zendesk.com).

### Proguard
If your are using Proguard and it complains during complie, refer [Proguard rules in sample app](https://github.com/estmob/SendAnywhere-Android-UI-SDK/blob/master/app/proguard-rules.pro).

### Runtime permission error: `java.io.IOException: open failed: EACCES (Permission denied)`
From Android Marshmallow(API 23), Android introduced new way to handle application permissions, called "Runtime Permissions". This requires developers to request sensitive permissions to users explicitly in application runtime. Send Anywhere SDK requires external storage permissions(`android.permission.WRITE_EXTERNAL_STORAGE`,`
android.permission.READ_EXTERNAL_STORAGE`) to work properly, but **does NOT** handle these permissions automatically. Please refer these articles to see more about Runtime Permission Model:
* Requesting Permissions at Run Time (Android Developers) -  https://developer.android.com/training/permissions/requesting.html
* Exploring the new Android Permissions Model (Ribot labs) - https://labs.ribot.co.uk/exploring-the-new-android-permissions-model-ba1d5d6c0610#.95cppknud

### API key error: `Wrong API key`
You must call `SendAnywhere.init(context, "YOUR_API_KEY")` proceeding any transfer operations, e.g. `onCreate` of `Activity`. It is declared as `static`, so you just have to call it once.

If this problem persists, please contact us to re-issue your api-key.

### Conflict with `google-play-services`
Send Anywhere SDK uses `com.google.android.gms:play-services-analytics` internally.
If this conflicts with your `play-services` dependency, please exclude `play-services` module used in our SDK:
```gradle
compile ('com.estmob.android:sendanywhere-transfer:x.x.x@aar') {
    exclude module: "play-services-analytics"
    transitive = true
}
```

### Conflict with `httpcore`
Send Anywhere SDK uses `org.apache.httpcomponents:httpcore` internally.
If this conflicts with your `httpcore` dependency, please exclude `httpcore` module used in our SDK:
```gradle
compile ('com.estmob.android:sendanywhere-transfer:x.x.x@aar') {
    exclude module: "httpcore"
    transitive = true
}
```


# Usage
First look at the source code of [the provided demo](https://github.com/estmob/SendAnywhere-Android-UI-SDK/blob/master/app/src/main/java/com/estmob/android/sendanywhere/sdk/ui/example/MainActivity.java).

## Class SendAnywhere
---

```java
package com.estmob.sdk.transfer;
...
public class SendAnywhere {
...
    public static void init(Context applicationContext, String key);
    public static void shutdown();
    public static Settings getSettings(Context context);
    public static void startSendActivity(Context context, Uri[] uris);
    public static void startSendActivity(Context context, File[] files);
    public static void startReceiveActivity(Context context);
    public static void startReceiveActivity(Context context, String key);
    public static void showActivity(Context context);
    public static DialogInterface showSendDialog(Context context, Uri[] uris, DialogInterface.OnDismissListener onDismissListener);
    public static DialogInterface showSendDialog(Context context, Uri[] uris, DialogInterface.OnDismissListener onDismissListener, ResultCallback resultCallback);
    public static DialogInterface showReceiveDialog(Context context, DialogInterface.OnDismissListener onDismissListener);
    public static DialogInterface showReceiveDialog(Context context, DialogInterface.OnDismissListener onDismissListener, ResultCallback resultCallback);
    public static void publishNearBy();
    public static void closeNearBy();
    public static void getHistory(HistoryListener listener);
    public static void deleteHistory(String id);
    public static void deleteAllHistory();
    public static void deleteKey(String key);
    public static void getReceivedNotifications(ReceivedNotificationListener listener);
    public static void deleteAllReceivedNotifications();
    public static void deleteReceivedNotification(long id);
    public static void getDeviceList(DeviceListListener listener);
    public static void getDevice(String id, DeviceListener listener);
    public static void deleteDevice(String id);
    public static void deleteAllDevices();
    public static void resetAllTrustedDevices();
    ...
}
```

### public static void init(Context context, String key)
Initialize the SDK. It must be called before using the SDK.

Parameters |                                      |
-----------| -------------------------------------|
context    | The application context.             |
key        | Your API key.                        |

### public static void shutdown()
It should be called when you are finished using the SDK. When this method is called, the resources allocated by the SDK are released.

### public static Settings getSettings(Context context)
Returns an object of the class that sets the SDK options or gets the current options.

Parameters |                                      |
-----------| -------------------------------------|
context    | The current context.                 |

### public static void startSendActivity(Context context, Uri[] uris)
Start the Send Activity.

Parameters |                                      |
-----------| -------------------------------------|
context    | The current context.                 |
uris       | The array of URIs of the files to transfer.   |

### public static void startReceiveActivity(Context context)
Start the Receive Activity.

Parameters |                                      |
-----------| -------------------------------------|
context    | The current context.                 |

### public static void startReceiveActivity(Context context, String key)
Start the Receive Activity and start downloading immediately with the given key.

Parameters |                                      |
-----------| -------------------------------------|
context    | The current context.                 |
key        | The key to download.                 |

### public static void showActivity(Context context)
Show the send and receive progress and the result of the transfer operation.

Parameters |                                      |
-----------| -------------------------------------|
context    | The current context.                 |

### public static DialogInterface showSendDialog(Context context, Uri[] uris, DialogInterface.OnDismissListener onDismissListener, ResultCallback resultCallback)
Show dialog for sending files. Returns the object of android.content.DialogInterface for the dialog shown.

Parameters        |                                                     |
------------------| ----------------------------------------------------|
context           | The current context.                                |
uris              | The array of URIs of the files to transfer.         |
onDismissListener | The listener for receiving dialog dismiss event.    |
resultCallback    | The callback that receives the transmission result. |

onDismissListener and resultCallback can be set to null if not used.

### public static DialogInterface showReceiveDialog(Context context, DialogInterface.OnDismissListener onDismissListener, ResultCallback resultCallback)
Show dialog for receiving files. Returns the object of android.content.DialogInterface for the dialog shown.

Parameters        |                                                     |
------------------| ----------------------------------------------------|
context           | The current context.                                |
onDismissListener | The listener for receiving dialog dismiss event.    |
resultCallback    | The callback that receives the transmission result. |

onDismissListener and resultCallback can be set to null if not used.

### public static void publishNearBy()
Make sure the app currently appears in the apps that use this SDK and the Send Anywhere application on nearby devices. Users can more easily transfer files between their devices.

### public static void closeNearBy()
Prevent the current app from appearing on other devices. You should call this method when the app is not visible on the screen or when it is no longer in use.

### public static void getHistory(HistoryListener listener)
Get the transfer history. You can get history via the listener passed as a parameter.

### public static void deleteHistory(String id)
Delete the history.

Parameters |                           |
---------- | --------------------------|
id         | ID of history to delete   |

### public static void deleteAllHistory()
Delete all file transfer history.

### public static void deleteKey(String key)
Delete the link used for file sharing. Unable to download file with deleted link.

Parameters |                           |
---------- | --------------------------|
key         | The key used for link    |

### public static void getReceivedNotifications(ReceivedNotificationListener listener)
Get notifications of file transfers delivered directly to the current device. You can get the objects of the notifications via the listener passed in as a parameter.

### public static void deleteAllReceivedNotifications()
Delete all file transfer notifications delivered to the current device.

### public static void deleteReceivedNotification(long id)
Delete the specified file transfer notification.

Parameters |                                       |
---------- | --------------------------------------|
id         | The ID of the notification to delete. |

### public static void getDeviceList(DeviceListListener listener)
Get information about devices that recently exchanged files. Device information can be obtained through a listener passed as a parameter.

### public static void getDevice(String id, DeviceListener listener)
Get information about the specified device.

Parameters |                            |
---------- | ---------------------------|
id         | The ID of the device.      |

### public static void deleteDevice(String id)
Delete information about the specified device.

Parameters |                            |
---------- | ---------------------------|
id         | The ID of the device.      |

### public static void deleteAllDevices()
Delete all the devices that have recently exchanged files. After this method is called, the list of recent devices in the Send activity is cleared.

### public static void resetAllTrustedDevices()
Delete all trusted devices. Trusted devices can be specified by the user and will automatically start downloading when transferring files between devices.

## Interface SendAnywhere.Settings
---
This interface is used to set and get the options of the SDK.
First look at the source code of [the provided demo](https://github.com/estmob/SendAnywhere-Android-UI-SDK/blob/master/app/src/main/java/com/estmob/android/sendanywhere/sdk/ui/example/SettingsActivity.java).
```java
package com.estmob.sdk.transfer;
...
public class SendAnywhere {
...
    public enum Theme {
        DEFAULT,
        DARK
    }

    public enum TrustedDevicesOption {
        ON,
        OFF,
        ASK
    }

    public enum DuplicateFileOption {
        RENAME,
        OVERWRITE
    }

    public interface Settings {

        void setDownloadDir(File dir);
        void setProfileName(String name);
        void setDeviceToken(String token);
        void setTheme(Theme theme);
        void setTrustedDevicesOption(TrustedDevicesOption option);
        void setRecordTransferHistory(boolean record);
        void setDuplicateFileOption(DuplicateFileOption option);
        void setNotificationSmallIcon(Integer iconRes);
        void setNotificationLargeIcon(Bitmap bitmap);
        void setFilePattern(Pattern pattern);
        void setTransferTimeout(long timeout);

        File getDownloadDir();
        String getProfileName();
        String getDeviceToken();
        Theme getTheme();
        TrustedDevicesOption getTrustedDevicesOption();
        boolean getRecordHistory();
        DuplicateFileOption getDuplicateFileOption();
        Integer getNotificationSmallIcon();
        Bitmap getNotificationLargeIcon();
        Pattern getFilePattern();
        long getTransferTimeout();
    }
...
}
```

### void setDownloadDir(File dir)
Set the directory where the files is downloaded. The default is '/ SendAnywhere'.

Parameters |                                                       |
---------- | ------------------------------------------------------|
dir        | The directory where the files will be downloaded      |

### void setProfileName(String name)
Set the profile name to be displayed on other devices.

Parameters |                       |
---------- | ----------------------|
dir        | The profile name      |

### void setDeviceToken(String token)
Set the GCM / FCM token to receive file transfer notifications delivered directly to the device. If set to null, no notifications will be dispatched. The default value is null.
See [example code](https://github.com/estmob/SendAnywhere-Android-UI-SDK/blob/master/app/src/main/java/com/estmob/android/sendanywhere/sdk/ui/example/MyFirebaseInstanceIDService.java).
**In order for your app to receive notifications, you need to register your GCM / FCM server key. Request registration of your server key by email(api@estmob.com).**
You should also add the following to AndroidManifest.xml:
```xml
<manifest ....>
    <permission
            android:name="${applicationId}.permission.C2D_MESSAGE"
            android:protectionLevel="signature"/>
    <uses-permission android:name="${applicationId}.permission.C2D_MESSAGE"/>
</manifest>
```

Parameters |                       |
---------- | ----------------------|
token      | GCM/FCM token         |

### void setTheme(Theme theme)
Set the theme that will be used for the Activity starting from the SDK. The default value is 'DEFAULT'.

SendAnywhere.Theme   |                       |
-------------------- | ----------------------|
DEFAULT              | Default Theme         |
DARK                 | Dark Theme            |

### void setTrustedDevicesOption(TrustedDevicesOption option)
Set the option for trusted devices. Files sent from trusted devices are automatically downloaded. The default value is 'ASK'.

SendAnywhere.TrustedDevicesOption   |                          |
----------------------------------- | -------------------------|
ON                                  | Trust all devices        |
OFF                                 | Do not trust all devices |
ASK                                 | Ask user to trust        |

### void setRecordTransferHistory(boolean record)
Set whether to record the send and receive history. The default value is false.

value of record   |                          |
----------------- | -------------------------|
true              | Record history.          |
false             | Do not record history.   |

### void setDuplicateFileOption(DuplicateFileOption option)
Set up the processing method when duplicate files are downloaded. The default value is 'RENAME'.

SendAnywhere.DuplicateFileOption    |                       |
----------------------------------- | ----------------------|
RENAME                              | Rename the file.      |
OVERWRITE                           | Overwrite the file.   |

### void setNotificationSmallIcon(Integer iconRes)
Set the small icon resource, which will be used to represent the notification in the status bar. If set to null, the default icon is used.

Parameters |                                                                    |
---------- | -------------------------------------------------------------------|
iconRes    | A resource ID in the application's package of the drawable to use. |

### void setNotificationLargeIcon(Bitmap bitmap)
Set the large icon in the notification. If set to null, the default icon is used.

Parameters |                                                    |
---------- | ---------------------------------------------------|
bitmap     | A bitmap to be used as large icon in notification. |

### void setFilePattern(Pattern pattern)
Set a pattern to filter downloaded files.

Parameters |                                                               |
---------- | --------------------------------------------------------------|
pattern    | The java.util.regex.Pattern object used for file filtering.   |

```java
// ex) Set to download only jpg, png files.
    void setFilePattern() {
        SendAnywhere.Settings settings = SendAnywhere.getSettings(context);
        final String FILE_PATTERN = "(.+(\\.(?i)(jpg|png))$)";
        settings.setFilePattern(Pattern.compile(FILE_PATTERN));
    }

```

### void setTransferTimeout(long timeout)
Set the timeout to wait when there is no response from the other party during transmission. (Milliseconds)

## Interface SendAnywhere.HistoryListener
Used as a parameter of SendAnywhere.getHistory().
```java
public class SendAnywhere {
...
    public interface HistoryListener {
        void onGetHistory(List<SendAnywhere.TransferHistory> historyList);
    }
...
}
```
### void onGetHistory(List<SendAnywhere.TransferHistory> historyList)
Called when a transfer history list is obtained. See [example code](https://github.com/estmob/SendAnywhere-Android-UI-SDK/blob/master/app/src/main/java/com/estmob/android/sendanywhere/sdk/ui/example/HistoryActivity.java).

## Interface SendAnywhere.TransferHistory
This interface provides data related to each transfer history.
You need to call 'Settings.setRecordTransferHistory (true);' to get the full history. The history of items shared with 'Share link' can be obtained regardless of the Settings.setRecordTransferHistory () call.
See [example code](https://github.com/estmob/SendAnywhere-Android-UI-SDK/blob/master/app/src/main/java/com/estmob/android/sendanywhere/sdk/ui/example/HistoryActivity.java).
```java
public class SendAnywhere {
...
    public interface TransferHistory {
        String getId();
        String getKey();
        String getLink();
        String getPeerDeviceId();
        SendAnywhere.TransferType getType();
        long getExpireAt();
        SendAnywhere.TransferState getState();
        long getSize();
        int getFileCount();
        long getStartTime();
        long getFinishedTime();
    }
...
}
```

### String getId()
Get the history ID. This ID is used as a parameter of SendAnywhere.deleteHistory().

### String getKey()
Get the transfer key. This key is used as a parameter of SendAnywhere.deleteKey().

### String getLink()
Get the URL of the item shared with 'Share link'.

### String getPeerDeviceId()
Get the device ID of the other party. This ID is used as a parameter of SendAnywhere.getDevice().

### SendAnywhere.TransferType getType()
Get the transfer type.

SendAnywhere.TransferType   |                       |
--------------------------- | ----------------------|
SEND                        | Sent                  |
RECEIVE                     | Received              |
SHARE                       | Shared via URL link   |

### long getExpireAt()
Get the time the URL link expires. (millisecond)

### SendAnywhere.TransferState getState()
Get the result of the transmission.

SendAnywhere.TransferState  |                       |
--------------------------- | ----------------------|
SUCCEEDED                   | Succeeded             |
CANCELLED                   | Cancelled by user     |
FAILED                      | Failed                |
SKIPPED                     | No file transferred.  |

### long getSize()
Get all transferred file sizes.

### int getFileCount()
Get the number of all transferred files.

### long getStartTime()
Get the time when the transmission started. (milliseconds)

### long getFinishedTime()
Get the time when the transmission finished. (milliseconds)

## Interface SendAnywhere.DeviceListListener
Used as a parameter of SendAnywhere.getDeviceList().
See [example code](https://github.com/estmob/SendAnywhere-Android-UI-SDK/blob/master/app/src/main/java/com/estmob/android/sendanywhere/sdk/ui/example/DeviceListActivity.java).
```java
public class SendAnywhere {
...
    public interface DeviceListListener {
        void onGetDeviceList(List<SendAnywhere.DeviceInfo> deviceList);
    }
...
}
```
### void onGetDeviceList(List<SendAnywhere.DeviceInfo> deviceList)
Called when getting a list of devices that have recently sent or received files.

## Interface SendAnywhere.DeviceListListener
Used as a parameter of SendAnywhere.getDevice().
See [example code](https://github.com/estmob/SendAnywhere-Android-UI-SDK/blob/master/app/src/main/java/com/estmob/android/sendanywhere/sdk/ui/example/HistoryActivity.java).
```java
public class SendAnywhere {
...
    public interface DeviceListener {
        void onGetDevice(SendAnywhere.DeviceInfo deviceInfo);
    }
...
}
```
### void onGetDevice(SendAnywhere.DeviceInfo deviceInfo)
Called when the specified device information is obtained.

## Interface SendAnywhere.DeviceInfo
This interface is provides information about the device.
See example code([DeviceListActivity.java](https://github.com/estmob/SendAnywhere-Android-UI-SDK/blob/master/app/src/main/java/com/estmob/android/sendanywhere/sdk/ui/example/DeviceListActivity.java), [HistoryActivity.java](https://github.com/estmob/SendAnywhere-Android-UI-SDK/blob/master/app/src/main/java/com/estmob/android/sendanywhere/sdk/ui/example/HistoryActivity.java)).
```java
public class SendAnywhere {
...
    public interface DeviceInfo {
        String getId();
        String getDeviceName();
        String getProfileName();
        Boolean isTrusted();
    }
...
}
```

### String getId()
Returns the ID of the device. This ID is used as a parameter of SendAnywhere.deleteDevice().

### String getDeviceName()
Returns the ID of the device.

### String getProfileName()
Returns the profile name specified by the user.

### Boolean isTrusted()
Returns true if this device is trusted. Files sent from trusted devices are automatically downloaded.

## Interface SendAnywhere.ReceivedNotificationListener
Used as a parameter of SendAnywhere.getReceivedNotifications().
See [example code](https://github.com/estmob/SendAnywhere-Android-UI-SDK/blob/master/app/src/main/java/com/estmob/android/sendanywhere/sdk/ui/example/ReceivedNotificationsActivity.java).
```java
public class SendAnywhere {
...
    public interface ReceivedNotificationListener {
        void onGetReceivedNotifications(List<SendAnywhere.ReceivedNotification> notifications);
    }
...
}
```

### void onGetReceivedNotifications(List<SendAnywhere.ReceivedNotification> notifications)
Called when getting the list of file transfer notifications delivered to the current device.

## Interface SendAnywhere.ReceivedNotification
This interface provides data related to notifications delivered to the device.
See [example code](https://github.com/estmob/SendAnywhere-Android-UI-SDK/blob/master/app/src/main/java/com/estmob/android/sendanywhere/sdk/ui/example/ReceivedNotificationsActivity.java).
```java
public class SendAnywhere {
...
    public interface ReceivedNotification {
        long getId();
        int getFileCount();
        long getSize();
        long getSendAt();
        long getExireAt();
        String getDeviceId();
        String getDeviceName();
        String getProfileName();
    }
...
}
```

### long getId()
Returns the ID of the notification. This ID is used as a parameter of SendAnywhere.deleteReceivedNotification().

### int getFileCount()
Returns the number of files to transfer.

### long getSize()
Returns the size of the files to be transferred.

### long getSendAt()
Returns the time at which the notification was delivered. (Milliseconds)

### long getExireAt()
Returns the time at which the transmission expires.
Transmission is not possible after this time. (Milliseconds)

### String getDeviceId()
Returns the ID of the device that transferred the file. This ID is used as a parameter of SendAnywhere.getDevice() or SendAnywhere.deleteDevice().

### String getDeviceName()
Returns the name of the device that sent the files.

### String getProfileName()
Returns the user-specified profile name on the device that sent the files.

## Interface SendAnywhere.ResultCallback
This interface is used to receive the transmission results.
```java
public class SendAnywhere {
...
    public interface ResultCallback {
        void onResult(SendAnywhere.TransferResult result);
    }
...
}
```

### void onResult(SendAnywhere.TransferResult result)
Called when the transfer is finished.

Parameters |                                                    |
---------- | ---------------------------------------------------|
result     | Information about the result of the transmission.  |

## Interface SendAnywhere.TransferResult
This interface provides information about the transmission result.
```java
public class SendAnywhere {
...
    public interface TransferResult {
        SendAnywhere.TransferType getType();
        SendAnywhere.TransferState getState();
        int getTotalFileCount();
        int getFileCount();
        int getTransferredFileCount();
    }
...
}
```

### SendAnywhere.TransferType getType()
Get the transfer type.

SendAnywhere.TransferType   |                       |
--------------------------- | ----------------------|
SEND                        | Sent                  |
RECEIVE                     | Received              |
SHARE                       | Shared via URL link   |

### SendAnywhere.TransferState getState()
Get the result of the transmission.

SendAnywhere.TransferState  |                       |
--------------------------- | ----------------------|
SUCCEEDED                   | Succeeded             |
CANCELLED                   | Cancelled by user     |
FAILED                      | Failed                |
SKIPPED                     | No file transferred.  |

### int getTotalFileCount()
Returns the total number of files sent by the sender.

### int getFileCount()
Returns the number of files the receiver attempted to download. It is determined by the file pattern set by the receiver.

### int getTransferredFileCount()
Returns the number of files that have been transferred.