package com.estmob.android.sendanywhere.sdk.ui.example;

import android.app.Application;
import android.os.Environment;

import com.estmob.sdk.transfer.SendAnywhere;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.File;
import java.util.regex.Pattern;

/**
 * Created by francisco on 2016-12-14.
 */

public class ExampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SendAnywhere.init(this, "YOUR_API_KEY");
        new SdkPreferences(this).load();

        SendAnywhere.Settings settings = SendAnywhere.getSettings(this);
        settings.setDownloadDir(new File(Environment.getExternalStorageDirectory(), "SendAnywhere"));
        settings.setDeviceToken(FirebaseInstanceId.getInstance().getToken());
//        settings.setTransferTimeout(30000);

//        final String FILE_PATTERN = "(.+(\\.(?i)(jpg|png|gif))$)";
//        settings.setFilePattern(Pattern.compile(FILE_PATTERN));
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SendAnywhere.shutdown();
    }
}
