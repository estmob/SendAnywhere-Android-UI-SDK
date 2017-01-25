package com.estmob.android.sendanywhere.sdk.ui.example.sample;

import com.estmob.sdk.transfer.SendAnywhere;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by francisco on 2016-12-15.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        SendAnywhere.getSettings(this).setDeviceToken(FirebaseInstanceId.getInstance().getToken());
    }
}
