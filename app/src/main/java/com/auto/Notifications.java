package com.auto;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;

public class Notifications {
    private static final String CHANNEL = "autoChannel";
    private final NotificationManager manager;
    private final Context context;

    public Notifications(Context context) {
        this.context = context;
        this.manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(CHANNEL, "Auto Notif", NotificationManager.IMPORTANCE_LOW);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[] {300, 600});
            channel.setDescription("Auto biztositas");
            manager.createNotificationChannel(channel);
        }
    }

    public void sendNotif(String notif) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL)
                .setSmallIcon(androidx.core.R.id.icon).setContentTitle("Auto").setContentText(notif);
        manager.notify(9999, builder.build());
    }
}
