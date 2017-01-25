package com.estmob.android.sendanywhere.sdk.ui.example.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.estmob.sdk.transfer.SendAnywhere;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by francisco on 2017-01-11.
 */

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<SendAnywhere.TransferHistory> historyList;

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
        SendAnywhere.getHistory(new SendAnywhere.HistoryListener() {
            @Override
            public void onGetHistory(List<SendAnywhere.TransferHistory> list) {
                historyList = list;
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

    private static String getSizeString(long bytes) {
        final int unit = 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        char pre = "KMGTPE".charAt(exp - 1);
        return String.format("%.2f %cB", bytes / Math.pow(unit, exp), pre);
    }

    private static String getDateString(Context context, long dateTimeMilli) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dateTimeMilli);

        if (cal.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)) {
            return android.text.format.DateUtils
                    .formatDateTime(context,
                            dateTimeMilli, (DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_WEEKDAY));
        } else {
            return android.text.format.DateUtils
                    .formatDateTime(context,
                            dateTimeMilli, (DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_WEEKDAY));
        }
    }

    private void deleteHistory(int position) {
        if (historyList == null || historyList.size() <= position) {
            return;
        }
        SendAnywhere.TransferHistory history = historyList.get(position);
        SendAnywhere.deleteHistory(history.getId());
        if (history.getType() == SendAnywhere.TransferType.SHARE) {
            SendAnywhere.deleteKey(history.getKey());
        }
        historyList.remove(position);
        adapter.notifyItemRemoved(position);
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<HistoryViewHolder> {
        @Override
        public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_history, parent, false);
            return new HistoryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(HistoryViewHolder holder, int position) {
            holder.update(position);
        }

        @Override
        public int getItemCount() {
            return historyList != null ? historyList.size() : 0;
        }
    }

    private class HistoryViewHolder extends RecyclerView.ViewHolder {

        private Button buttonDelete;
        private TextView textType;
        private TextView textDate;
        private TextView textProfile;
        private TextView textTime;
        private TextView textLink;
        private TextView textDevice;
        private TextView textFileCount;
        private TextView textSize;
        private TextView textState;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            buttonDelete = (Button) itemView.findViewById(R.id.buttonDelete);
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        deleteHistory(pos);
                    }
                }
            });
            textType = (TextView) itemView.findViewById(R.id.textType);
            textDate = (TextView) itemView.findViewById(R.id.textDate);
            textProfile = (TextView) itemView.findViewById(R.id.textProfile);
            textTime = (TextView) itemView.findViewById(R.id.textTime);
            textLink = (TextView) itemView.findViewById(R.id.textLink);
            textDevice = (TextView) itemView.findViewById(R.id.textDevice);
            textSize = (TextView) itemView.findViewById(R.id.textSize);
            textFileCount = (TextView) itemView.findViewById(R.id.textFileCount);
            textState = (TextView) itemView.findViewById(R.id.textState);
        }

        public void update(int position) {
            final SendAnywhere.TransferHistory history = historyList.get(position);
            textType.setText(history.getType().name());
            textDate.setText(getDateString(HistoryActivity.this, history.getFinishedTime()));
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
            textTime.setText(dateFormat.format(history.getFinishedTime()));
            textLink.setText(history.getLink());
            textSize.setText(getSizeString(history.getSize()));
            textFileCount.setText(String.format("%d files", history.getFileCount()));
            textState.setText(history.getState().name());
            textProfile.setText("");
            textDevice.setText("");
            if (history.getType() == SendAnywhere.TransferType.SHARE) {
                textLink.setVisibility(View.VISIBLE);
                textProfile.setVisibility(View.INVISIBLE);
                textDevice.setVisibility(View.INVISIBLE);
            } else {
                textLink.setVisibility(View.INVISIBLE);
                textProfile.setVisibility(View.VISIBLE);
                textDevice.setVisibility(View.VISIBLE);
                SendAnywhere.getDevice(history.getPeerDeviceId(), new SendAnywhere.DeviceListener() {
                    @Override
                    public void onGetDevice(SendAnywhere.DeviceInfo deviceInfo) {
                        if (deviceInfo != null && deviceInfo.getId().equals(history.getPeerDeviceId())) {
                            textProfile.setText(deviceInfo.getProfileName());
                            textDevice.setText(deviceInfo.getDeviceName());
                        }
                    }
                });
            }
        }
    }
}
