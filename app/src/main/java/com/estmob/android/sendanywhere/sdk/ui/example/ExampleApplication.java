package com.estmob.android.sendanywhere.sdk.ui.example;

import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;

import com.estmob.sdk.transfer.SendAnywhere;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.File;
//import java.util.regex.Pattern;

/**
 * Created by francisco on 2016-12-14.
 */

public class ExampleApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        SendAnywhere.init(this, "YOUR_API_KEY");
        new SdkPreferences(this).load();

        final SendAnywhere.Settings settings = SendAnywhere.getSettings(this);
        settings.setDownloadDir(new File(Environment.getExternalStorageDirectory(), "SendAnywhere"));
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    settings.setDeviceToken(task.getResult().getToken());
                } else {
                    settings.setDeviceToken(null);
                }
            }
        });
//        final String FILE_PATTERN = "(.+(\\.(?i)(jpg|png|gif))$)";
//        settings.setFilePattern(Pattern.compile(FILE_PATTERN));
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SendAnywhere.shutdown();
    }
}
