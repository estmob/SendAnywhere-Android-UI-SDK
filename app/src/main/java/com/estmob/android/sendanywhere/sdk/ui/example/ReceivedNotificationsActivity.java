package com.estmob.android.sendanywhere.sdk.ui.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.estmob.sdk.transfer.SendAnywhere;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by francisco on 2017-01-12.
 */

public class ReceivedNotificationsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<SendAnywhere.ReceivedNotification> notifications;

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
        SendAnywhere.getReceivedNotifications(new SendAnywhere.ReceivedNotificationListener() {
            @Override
            public void onGetReceivedNotifications(List<SendAnywhere.ReceivedNotification> list) {
                notifications = list;
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

    private void deleteReceivedNotification(int position) {
        if (notifications == null || notifications.size() <= position) {
            return;
        }
        SendAnywhere.deleteReceivedNotification(notifications.get(position).getId());
        notifications.remove(position);
        adapter.notifyItemRemoved(position);
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<ReceivedNotificationViewHolder> {
        @Override
        public ReceivedNotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_received_notification, parent, false);
            return new ReceivedNotificationViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ReceivedNotificationViewHolder holder, int position) {
            holder.update(position);
        }

        @Override
        public int getItemCount() {
            return notifications != null ? notifications.size() : 0;
        }
    }

    private class ReceivedNotificationViewHolder extends RecyclerView.ViewHolder {

        private TextView textProfileName;
        private TextView textDeviceName;
        private TextView textSentAt;
        private TextView textExpireAt;
        private TextView textFileCount;
        private TextView textSize;
        private Button buttonDelete;

        public ReceivedNotificationViewHolder(View itemView) {
            super(itemView);
            textDeviceName = (TextView) itemView.findViewById(R.id.textDeviceName);
            textProfileName = (TextView) itemView.findViewById(R.id.textProfileName);
            textSize = (TextView) itemView.findViewById(R.id.textSize);
            textFileCount = (TextView) itemView.findViewById(R.id.textFileCount);
            textSentAt = (TextView) itemView.findViewById(R.id.textSendAt);
            textExpireAt = (TextView) itemView.findViewById(R.id.textExpireAt);
            buttonDelete = (Button) itemView.findViewById(R.id.buttonDelete);
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        deleteReceivedNotification(pos);
                    }
                }
            });
        }

        public void update(int position) {
            SendAnywhere.ReceivedNotification notification = notifications.get(position);
            textProfileName.setText(notification.getProfileName());
            textDeviceName.setText(notification.getDeviceName());
            textSize.setText(getSizeString(notification.getSize()));
            textFileCount.setText(String.format("%d files", notification.getFileCount()));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
            textSentAt.setText(dateFormat.format(notification.getSendAt()));
            textExpireAt.setText(String.format("Expire At %s", dateFormat.format(notification.getExireAt())));
        }
    }
}
