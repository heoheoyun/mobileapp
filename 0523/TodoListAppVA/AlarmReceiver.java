package com.example.todolistappva;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String todoText = intent.getStringExtra("todoText");
        NotificationHelper helper = new NotificationHelper(context);
        NotificationCompat.Builder nb = helper.getChannelNotification("할 일 알림", todoText);
        helper.getManager().notify((int) System.currentTimeMillis(), nb.build());
    }
}
