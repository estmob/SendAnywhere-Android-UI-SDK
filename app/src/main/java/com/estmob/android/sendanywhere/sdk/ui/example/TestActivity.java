package com.estmob.android.sendanywhere.sdk.ui.example;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by francisco on 2017-01-13.
 */

public class TestActivity extends AppCompatActivity {

    private View.OnClickListener onButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.buttonDeviceList) {
                Intent intent = new Intent(TestActivity.this, DeviceListActivity.class);
                startActivity(intent);
            } else if (v.getId() == R.id.buttonHistory) {
                Intent intent = new Intent(TestActivity.this, HistoryActivity.class);
                startActivity(intent);
            } else if (v.getId() == R.id.buttonReceivedNotifications) {
                Intent intent = new Intent(TestActivity.this, ReceivedNotificationsActivity.class);
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        findViewById(R.id.buttonDeviceList).setOnClickListener(onButtonClickListener);
        findViewById(R.id.buttonHistory).setOnClickListener(onButtonClickListener);
        findViewById(R.id.buttonReceivedNotifications).setOnClickListener(onButtonClickListener);
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
}
