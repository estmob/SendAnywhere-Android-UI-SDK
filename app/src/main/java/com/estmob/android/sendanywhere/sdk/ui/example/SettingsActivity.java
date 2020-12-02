package com.estmob.android.sendanywhere.sdk.ui.example;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.estmob.sdk.transfer.SendAnywhere;

/**
 * Created by francisco on 2017-01-06.
 */

public class SettingsActivity extends AppCompatActivity {

    private EditText editProfileName;
    private SwitchCompat switchhistory;

    View.OnClickListener onButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.buttonDefault) {
                SendAnywhere.getSettings(SettingsActivity.this).setTheme(SendAnywhere.Theme.DEFAULT);
            } else if (v.getId() == R.id.buttonDark) {
                SendAnywhere.getSettings(SettingsActivity.this).setTheme(SendAnywhere.Theme.DARK);
            } else if (v.getId() == R.id.buttonResetAllTrustedDevices) {
                SendAnywhere.resetAllTrustedDevices();
            } else if (v.getId() == R.id.buttonDeleteAllDevices) {
                SendAnywhere.deleteAllDevices();
            } else if (v.getId() == R.id.buttonDeleteAllHistory) {
                SendAnywhere.deleteAllHistory();
            } else if (v.getId() == R.id.buttonDeleteAllReceivedNotifications) {
                SendAnywhere.deleteAllReceivedNotifications();
            } else if (v.getId() == R.id.buttonOn) {
                SendAnywhere.getSettings(SettingsActivity.this).setTrustedDevicesOption(SendAnywhere.TrustedDevicesOption.ON);
            } else if (v.getId() == R.id.buttonOff) {
                SendAnywhere.getSettings(SettingsActivity.this).setTrustedDevicesOption(SendAnywhere.TrustedDevicesOption.OFF);
            } else if (v.getId() == R.id.buttonAsk) {
                SendAnywhere.getSettings(SettingsActivity.this).setTrustedDevicesOption(SendAnywhere.TrustedDevicesOption.ASK);
            } else if (v.getId() == R.id.switchHistory) {
                SendAnywhere.getSettings(SettingsActivity.this).setRecordTransferHistory(switchhistory.isChecked());
            } else if (v.getId() == R.id.buttonRename) {
                SendAnywhere.getSettings(SettingsActivity.this).setDuplicateFileOption(SendAnywhere.DuplicateFileOption.RENAME);
            } else if (v.getId() == R.id.buttonOverwrite) {
                SendAnywhere.getSettings(SettingsActivity.this).setDuplicateFileOption(SendAnywhere.DuplicateFileOption.OVERWRITE);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        findViewById(R.id.buttonResetAllTrustedDevices).setOnClickListener(onButtonClickListener);
        findViewById(R.id.buttonDeleteAllDevices).setOnClickListener(onButtonClickListener);
        findViewById(R.id.buttonDeleteAllHistory).setOnClickListener(onButtonClickListener);
        findViewById(R.id.buttonDeleteAllReceivedNotifications).setOnClickListener(onButtonClickListener);

        SendAnywhere.Settings settings = SendAnywhere.getSettings(this);

        switchhistory = (SwitchCompat) findViewById(R.id.switchHistory);
        switchhistory.setOnClickListener(onButtonClickListener);
        switchhistory.setChecked(settings.getRecordHistory());

        RadioButton radioButton = (RadioButton) findViewById(R.id.buttonDefault);
        radioButton.setOnClickListener(onButtonClickListener);
        radioButton.setChecked(settings.getTheme() == SendAnywhere.Theme.DEFAULT);
        radioButton = (RadioButton) findViewById(R.id.buttonDark);
        radioButton.setOnClickListener(onButtonClickListener);
        radioButton.setChecked(settings.getTheme() == SendAnywhere.Theme.DARK);

        radioButton = (RadioButton) findViewById(R.id.buttonOn);
        radioButton.setOnClickListener(onButtonClickListener);
        radioButton.setChecked(settings.getTrustedDevicesOption() == SendAnywhere.TrustedDevicesOption.ON);
        radioButton = (RadioButton) findViewById(R.id.buttonOff);
        radioButton.setOnClickListener(onButtonClickListener);
        radioButton.setChecked(settings.getTrustedDevicesOption() == SendAnywhere.TrustedDevicesOption.OFF);
        radioButton = (RadioButton) findViewById(R.id.buttonAsk);
        radioButton.setOnClickListener(onButtonClickListener);
        radioButton.setChecked(settings.getTrustedDevicesOption() == SendAnywhere.TrustedDevicesOption.ASK);

        radioButton = (RadioButton) findViewById(R.id.buttonRename);
        radioButton.setOnClickListener(onButtonClickListener);
        radioButton.setChecked(settings.getDuplicateFileOption() == SendAnywhere.DuplicateFileOption.RENAME);
        radioButton = (RadioButton) findViewById(R.id.buttonOverwrite);
        radioButton.setOnClickListener(onButtonClickListener);
        radioButton.setChecked(settings.getDuplicateFileOption() == SendAnywhere.DuplicateFileOption.OVERWRITE);

        editProfileName = (EditText) findViewById(R.id.editProfileName);
        editProfileName.setText(settings.getProfileName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SendAnywhere.Settings settings = SendAnywhere.getSettings(this);
        settings.setProfileName(editProfileName.getText().toString());
        new SdkPreferences(this).save();
    }
}
