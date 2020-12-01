package com.estmob.android.sendanywhere.sdk.ui.example;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.estmob.sdk.transfer.SendAnywhere;

import java.util.List;


/**
 * Created by francisco on 2017-01-11.
 */

public class DeviceListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<SendAnywhere.DeviceInfo> devices;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(com.estmob.sdk.transfer.R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
        SendAnywhere.getDeviceList(new SendAnywhere.DeviceListListener() {
            @Override
            public void onGetDeviceList(List<SendAnywhere.DeviceInfo> list) {
                devices = list;
                adapter.notifyDataSetChanged();
            }
        });
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

    private void deleteDevice(int position) {
        if (devices == null || devices.size() <= position) {
            return;
        }
        SendAnywhere.deleteDevice(devices.get(position).getId());
        devices.remove(position);
        adapter.notifyItemRemoved(position);
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<DeviceViewHolder> {
        @Override
        public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_device, parent, false);
            return new DeviceViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DeviceViewHolder holder, int position) {
            holder.update(position);
        }

        @Override
        public int getItemCount() {
            return devices != null ? devices.size() : 0;
        }
    }

    private class DeviceViewHolder extends RecyclerView.ViewHolder {

        private TextView textProfileName;
        private TextView textDeviceName;
        private Button buttonDelete;
        private View trusted;

        public DeviceViewHolder(View itemView) {
            super(itemView);
            textDeviceName = (TextView) itemView.findViewById(R.id.textDeviceName);
            textProfileName = (TextView) itemView.findViewById(R.id.textProfileName);
            trusted = itemView.findViewById(R.id.trusted);
            buttonDelete = (Button) itemView.findViewById(R.id.buttonDelete);
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        deleteDevice(pos);
                    }
                }
            });
        }

        public void update(int position) {
            SendAnywhere.DeviceInfo deviceInfo = devices.get(position);
            textProfileName.setText(deviceInfo.getProfileName());
            textDeviceName.setText(deviceInfo.getDeviceName());
            trusted.setVisibility(deviceInfo.isTrusted() ? View.VISIBLE : View.INVISIBLE);
        }
    }
}
