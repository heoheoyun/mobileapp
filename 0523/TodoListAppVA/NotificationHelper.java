package com.example.todolistappva;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import androidx.core.app.NotificationCompat;

public class NotificationHelper {
    private final NotificationManager mManager;
    private final Context mContext;
    public static final String CHANNEL_ID = "todo_channel_id";
    public static final String CHANNEL_NAME = "ToDo 알림 채널";

    public NotificationHelper(Context context) {
        mContext = context;
        mManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
        );
        channel.setDescription("할 일 알림을 위한 채널");
        mManager.createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification(String title, String content) {
        return new NotificationCompat.Builder(mContext, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_notification);
    }
}