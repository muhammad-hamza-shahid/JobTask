package com.example.jobtask.utilities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.jobtask.MainActivity;
import com.example.jobtask.R;

public class NotificationBroadcastReciever extends BroadcastReceiver {
  //  private AlarmManager alarmMgr ;
  //  private PendingIntent alarmIntent;
  private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID ="primary_notification_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (intent.getAction() != null && intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

            Intent serviceIntent = new Intent(context, Service.class);
            context.startService(serviceIntent);
        } else {
            deliverEmptyNotification(context);
        }

    }

    private void deliverEmptyNotification(Context context) {
        Intent contentIntent = new Intent(context, MainActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notif)
                .setContentTitle("Drink Alert")
                .setContentText("Need some drinks open app now")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        mNotificationManager.notify(NOTIFICATION_ID, builder.build());

    }

    private void deliverOnlineNotification(Context context) {
        Intent contentIntent = new Intent(context, MainActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stand_up)
                .setContentTitle("Drink Alert")
                .setContentText("Need some drinks open app now")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        mNotificationManager.notify(NOTIFICATION_ID, builder.build());

    }

    private void deliverFavouriteNotification(Context context) {
        Intent contentIntent = new Intent(context, MainActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews collapsedView = new RemoteViews("com.example.jobtask",
                R.layout.notification);
        collapsedView.setTextViewText(R.id.txt_drink_name,"sdf");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stand_up)
                .setContentTitle("Drink Alert")
                .setCustomContentView(collapsedView)
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        mNotificationManager.notify(NOTIFICATION_ID, builder.build());

    }
}
