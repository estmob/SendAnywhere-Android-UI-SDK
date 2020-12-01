package com.estmob.android.sendanywhere.sdk.ui.example;

import android.Manifest;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.estmob.sdk.transfer.SendAnywhere;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int RESULT_CONTENTS_FOR_ACTIVITY = 1024;
    private static final int RESULT_CONTENTS_FOR_DIALOG = 1025;
    private static final int RESULT_CONTENTS_FOR_INTENT = 1026;
    private static final int CODE_PERMISSIONS = 100;
    private boolean permissionsGranted = false;
    private DialogInterface transferDialog;
    private DialogInterface.OnDismissListener onDismissListener = new DialogInterface.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {
            removeTransferDialog();
        }
    };

    private View.OnClickListener onButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.buttonReceive) {
                receive(Mode.ACTIVITY);
            } else if (v.getId() == R.id.buttonSend) {
                send(Mode.ACTIVITY);
            } else if (v.getId() == R.id.buttonActivity) {
                showActivity();
            } else if (v.getId() == R.id.buttonTest) {
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                startActivity(intent);
            } else if (v.getId() == R.id.buttonSettings) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            } else if (v.getId() == R.id.buttonSendDialog) {
                send(Mode.DIALOG);
            } else if (v.getId() == R.id.buttonRecvDialog) {
                receive(Mode.DIALOG);
            } else if (v.getId() == R.id.buttonSendIntent) {
                send(Mode.INTENT);
            } else if (v.getId() == R.id.buttonRecvIntent) {
                receive(Mode.INTENT);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<>();
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (permissions.isEmpty()) {
                permissionsGranted = true;
            } else {
                String[] arr = permissions.toArray(new String[permissions.size()]);
                requestPermissions(arr, CODE_PERMISSIONS);
            }
        } else {
            permissionsGranted = true;
        }

        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonSend).setOnClickListener(onButtonClickListener);
        findViewById(R.id.buttonReceive).setOnClickListener(onButtonClickListener);
        findViewById(R.id.buttonActivity).setOnClickListener(onButtonClickListener);
        findViewById(R.id.buttonTest).setOnClickListener(onButtonClickListener);
        findViewById(R.id.buttonSettings).setOnClickListener(onButtonClickListener);
        findViewById(R.id.buttonSendDialog).setOnClickListener(onButtonClickListener);
        findViewById(R.id.buttonRecvDialog).setOnClickListener(onButtonClickListener);
        findViewById(R.id.buttonSendIntent).setOnClickListener(onButtonClickListener);
        findViewById(R.id.buttonRecvIntent).setOnClickListener(onButtonClickListener);

        processIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        processIntent(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SendAnywhere.publishNearBy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SendAnywhere.closeNearBy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeTransferDialog();
    }

    private void removeTransferDialog() {
        if (transferDialog != null) {
            transferDialog.cancel();
            transferDialog = null;
        }
    }

    private void processIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        if (intent.getAction().equals(Intent.ACTION_VIEW)) {
            Uri uri = intent.getData();
            if (uri != null && permissionsGranted) {
                SendAnywhere.startReceiveActivity(this, uri.toString());
            }
        }
    }

    private void showSendDialog(Uri[] uris) {
        if (transferDialog == null) {
            transferDialog = SendAnywhere.showSendDialog(this, uris, onDismissListener, new SendAnywhere.ResultCallback() {
                @Override
                public void onResult(SendAnywhere.TransferResult result) {
                    Log.d("TransferResult", String.format("%s %s", result.getType().toString(), result.getState().toString()));
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == RESULT_CONTENTS_FOR_ACTIVITY || requestCode == RESULT_CONTENTS_FOR_DIALOG || requestCode == RESULT_CONTENTS_FOR_INTENT)
                && resultCode == RESULT_OK) {
            final Uri uri = data.getData();
            if (uri != null) {
                switch (requestCode) {
                    case RESULT_CONTENTS_FOR_ACTIVITY:
                        SendAnywhere.startSendActivity(this, new Uri[]{uri});
                        break;
                    case RESULT_CONTENTS_FOR_DIALOG:
                        showSendDialog(new Uri[]{uri});
                        break;
                    case RESULT_CONTENTS_FOR_INTENT:
                        startSendActivity(new Uri[]{uri});
                        break;
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ClipData clipData = data.getClipData();
                    if (clipData != null) {
                        Uri[] uris = new Uri[clipData.getItemCount()];
                        for (int i = 0; i < clipData.getItemCount(); ++i) {
                            ClipData.Item item = clipData.getItemAt(i);
                            uris[i] = item.getUri();
                        }
                        switch (requestCode) {
                            case RESULT_CONTENTS_FOR_ACTIVITY:
                                SendAnywhere.startSendActivity(this, uris);
                                break;
                            case RESULT_CONTENTS_FOR_DIALOG:
                                showSendDialog(uris);
                                break;
                            case RESULT_CONTENTS_FOR_INTENT:
                                startSendActivity(uris);
                                break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults != null && grantResults.length > 0) {
            boolean granted = true;
            for (int res : grantResults) {
                if (res != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                    break;
                }
            }
            if (granted) {
                permissionsGranted = true;
                SendAnywhere.publishNearBy();
            } else {
                finish();
            }
        }
    }

    private void receive(Mode mode) {
        if (!permissionsGranted) {
            return;
        }
        switch (mode) {
            case ACTIVITY:
                SendAnywhere.startReceiveActivity(MainActivity.this);
                break;
            case DIALOG:
                if (transferDialog == null) {
                    transferDialog = SendAnywhere.showReceiveDialog(this, onDismissListener, new SendAnywhere.ResultCallback() {
                        @Override
                        public void onResult(SendAnywhere.TransferResult result) {
                            Log.d("TransferResult", String.format("%s %s", result.getType().toString(), result.getState().toString()));
                        }
                    });
                }
                break;
            case INTENT:
            {
                Intent intent = new SendAnywhere.ReceiveIntentBuilder(this)
                        .setInformationTitle(R.string.example_info_title) // title of the information dialog
                        .setInformationText(R.string.example_info_text) // contents of the information dialog
                        .build();
                startActivity(intent);
            }
                break;
            default:
                break;
        }
    }

    private void send(Mode mode) {
        if (!permissionsGranted) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*", "audio/*", "video/*", "application/*"});
        int reqCode = 0;
        switch (mode) {
            case ACTIVITY:
                reqCode = RESULT_CONTENTS_FOR_ACTIVITY;
                break;
            case DIALOG:
                reqCode = RESULT_CONTENTS_FOR_DIALOG;
                break;
            case INTENT:
                reqCode = RESULT_CONTENTS_FOR_INTENT;
                break;
            default:
                break;
        }
        startActivityForResult(intent, reqCode);
    }

    private void showActivity() {
        if (!permissionsGranted) {
            return;
        }
        SendAnywhere.showActivity(this);
    }

    private void startSendActivity(Uri[] uris) {
        Intent intent = new SendAnywhere.SendIntentBuilder(this, uris)
                .setFeatureName(R.string.app_name) // feature name to be displayed in send activity
                .setFeatureUri(Uri.parse("https://send-anywhere.com")) // URI to open when you click feature name
                .setInformationTitle(R.string.example_info_title) // title of the information dialog
                .setInformationText(R.string.example_info_text) // contents of the information dialog
                .build();
        startActivity(intent);
    }

    enum Mode {
        ACTIVITY,
        DIALOG,
        INTENT
    }
}
