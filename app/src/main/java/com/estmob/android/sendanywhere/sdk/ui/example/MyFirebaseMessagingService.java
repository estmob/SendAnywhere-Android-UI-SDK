package com.estmob.android.sendanywhere.sdk.ui.example;

import com.estmob.sdk.transfer.SendAnywhere;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        SendAnywhere.getSettings(this).setDeviceToken(s);
    }
}
