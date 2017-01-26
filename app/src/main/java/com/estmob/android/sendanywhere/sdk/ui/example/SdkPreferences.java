package com.estmob.android.sendanywhere.sdk.ui.example;

import android.content.Context;
import android.content.SharedPreferences;

import com.estmob.sdk.transfer.SendAnywhere;

/**
 * Created by francisco on 2017-01-10.
 */

public class SdkPreferences {
    private static String DEFAULT_PROFILE_NAME = "Send Anywhere SDK";
    private SharedPreferences sharedPreferences;
    private SendAnywhere.Settings settings;

    public SdkPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("SendAnywhereSDK", Context.MODE_PRIVATE);
        settings = SendAnywhere.getSettings(context);
    }

    public void load() {
        settings.setProfileName(sharedPreferences.getString("profileName", DEFAULT_PROFILE_NAME));
        settings.setTheme(SendAnywhere.Theme.valueOf(sharedPreferences.getString("theme", settings.getTheme().name())));
        settings.setTrustedDevicesOption(SendAnywhere.TrustedDevicesOption.valueOf(sharedPreferences.getString("trustedDeviceOption", settings.getTrustedDevicesOption().name())));
        settings.setRecordTransferHistory(sharedPreferences.getBoolean("recordTransferHistory", settings.getRecordHistory()));
        settings.setDuplicateFileOption(SendAnywhere.DuplicateFileOption.valueOf(sharedPreferences.getString("duplicateFileOption", settings.getDuplicateFileOption().name())));
    }

    public void save() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("profileName", settings.getProfileName());
        editor.putString("theme", settings.getTheme().name());
        editor.putString("trustedDeviceOption", settings.getTrustedDevicesOption().name());
        editor.putBoolean("recordTransferHistory", settings.getRecordHistory());
        editor.putString("duplicateFileOption", settings.getDuplicateFileOption().name());
        editor.commit();
    }
}
