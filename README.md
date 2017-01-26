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
compile ('com.estmob.android:sendanywhere-transfer:7.1.25@aar') {
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

### API key error: `ERROR_WRONG_API_KEY`
You must call `SendAnywhere.init(context, "YOUR_API_KEY")` proceeding any transfer operations, e.g. `onCreate` of `Activity`. It is declared as `static`, so you just have to call it once.

If this problem persists, please contact us to re-issue your api-key.

### Conflict with `google-play-services`
Send Anywhere SDK uses `play-services-analytics:10.0.1` internally.
If this conflicts with your `play-services` dependency, please exclude `play-services` module used in our SDK:
```gradle
compile ('com.estmob.android:sendanywhere-transfer:x.x.x@aar') {
    exclude module: "play-services-analytics"
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
key         | The key to download.                |

### public static void showActivity(Context context)
Show the send and receive progress and the result of the transfer operation.
Parameters |                                      |
-----------| -------------------------------------|
context    | The current context.                 |

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
Parameters |                                        |
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
This class is used to set and get the options of the SDK.
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

        File getDownloadDir();
        String getProfileName();
        String getDeviceToken();
        Theme getTheme();
        TrustedDevicesOption getTrustedDevicesOption();
        boolean getRecordHistory();
        DuplicateFileOption getDuplicateFileOption();
    }
...
}
```

### void setDownloadDir(File dir)
Set the directory where the files is downloaded. The default is '/ SendAnywhere'.
Parameters |                                                        |
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
Parameters |                       |
---------- | ----------------------|
token      | GCM/FCM token         |

### void setTheme(Theme theme)
Set the theme that will be used for the Activity starting from the SDK. The default value is 'DEFAULT'.
SendAnywhere.Theme   |                       |
-------------------- | ----------------------|
DEFAULT              | Default Theme         |
DARK                  | Dark Theme            |

### void setTrustedDevicesOption(TrustedDevicesOption option)
Set the option for trusted devices. Files sent from trusted devices are automatically downloaded. The default value is 'ASK'.
SendAnywhere.TrustedDevicesOption   |                       |
----------------------------------- | ----------------------|
ON                                | Trust all devices         |
OFF                                | Do not trust all devices  |
ASK                                | Ask user to trust        |

### void setRecordTransferHistory(boolean record)
Set whether to record the send and receive history. The default value is false.
value of record   |                       |
----------------- | ----------------------|
true             | Record history.          |
false            | Do not record history.  |

### void setDuplicateFileOption(DuplicateFileOption option)
Set up the processing method when duplicate files are downloaded. The default value is 'RENAME'.
SendAnywhere.DuplicateFileOption    |                       |
----------------------------------- | ----------------------|
RENAME                           | Rename the file.         |
OVERWRITE                        | Overwrite the file.      |