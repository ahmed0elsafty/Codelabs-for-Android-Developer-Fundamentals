package com.elsafty.notifyme;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final int NOTIFICATION_ID = 845;
    private static final int REQUESTCODE_MAIN_ACTIVITY = 276;
    private static final int REQUESTCODE_UPDATE_ACTION = 754;
    private static final int REQUESTCODE_DISMISS_ACTION = 954;
    private static final String UPDATE_NOTIFICATION_ACTION =
            BuildConfig.APPLICATION_ID + ".ACTION_UPDATE_NOTIFICATION";
    private static final String DISMISS_NOTIFICATION_ACTION =
            BuildConfig.APPLICATION_ID + ".ACTION_DISMISS_NOTIFICATION";
    private Button btnNotifyMe, btnUpdateME, btnCancelMe;
    private NotificationManager mNotificationManager;
    private NotificationReceiver mNotificationReceiver;
    private PendingIntent mDismissPendingIntent;
    private IntentFilter mFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNotifyMe = findViewById(R.id.notify_me);
        btnNotifyMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();
            }
        });
        btnUpdateME = findViewById(R.id.update_me);
        btnUpdateME.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNotification();
            }
        });
        btnCancelMe = findViewById(R.id.cancel_me);
        btnCancelMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelNotification();
            }
        });
        toggle(true, false, false);

        mNotificationReceiver = new NotificationReceiver();
        mFilter= new IntentFilter();
        mFilter.addAction(UPDATE_NOTIFICATION_ACTION);
        mFilter.addAction(DISMISS_NOTIFICATION_ACTION);
        createNotificationChannel();
        this.registerReceiver(mNotificationReceiver, mFilter);
    }

    private void updateNotification() {
        NotificationCompat.Builder builder = getNotificationBuilder();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mascot_1);
        builder.setStyle(new NotificationCompat.
                BigPictureStyle().
                bigPicture(bitmap).
                setBigContentTitle("Notification Updated!"));

        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
        toggle(false, false, true);
    }

    private void cancelNotification() {
        mNotificationManager.cancel(NOTIFICATION_ID);
        toggle(true, false, false);
    }

    public void toggle(boolean notify_me, boolean update_me, boolean cancel_me) {
        btnNotifyMe.setEnabled(notify_me);
        btnUpdateME.setEnabled(update_me);
        btnCancelMe.setEnabled(cancel_me);
    }


    public void sendNotification() {
        NotificationCompat.Builder builder = getNotificationBuilder();
        mNotificationManager.notify(NOTIFICATION_ID,
                builder.build());
        toggle(false, true, true);
    }

    public void createNotificationChannel() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Mascot Notification",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.setDescription("Notification from Mascot");
            channel.enableVibration(true);
            mNotificationManager.createNotificationChannel(channel);
        }
    }

    public NotificationCompat.Builder getNotificationBuilder() {

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                REQUESTCODE_MAIN_ACTIVITY,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Intent dismissIntent = new Intent(DISMISS_NOTIFICATION_ACTION);
        mDismissPendingIntent = PendingIntent.getBroadcast(this,
                REQUESTCODE_DISMISS_ACTION, dismissIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_android)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentTitle("You've been notified!")
                .setContentText("This is your notification text.")
                .addAction(updateAction())
                .addAction(dismissAction())
                .setDeleteIntent(mDismissPendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent);
        return builder;
    }

    public NotificationCompat.Action updateAction() {
        Intent updateIntent = new Intent(UPDATE_NOTIFICATION_ACTION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(this,
                REQUESTCODE_UPDATE_ACTION, updateIntent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Action action = new NotificationCompat.Action(R.drawable.ic_update,
                "UPDATE", updatePendingIntent);
        return action;
    }

    public NotificationCompat.Action dismissAction() {
        Intent dismissIntent = new Intent(DISMISS_NOTIFICATION_ACTION);
        mDismissPendingIntent = PendingIntent.getBroadcast(this,
                REQUESTCODE_DISMISS_ACTION, dismissIntent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Action action = new NotificationCompat.Action(R.drawable.ic_dismiss,
                "DISMISS", mDismissPendingIntent);
        return action;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNotificationReceiver);
    }

    class NotificationReceiver extends BroadcastReceiver {

        public NotificationReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case UPDATE_NOTIFICATION_ACTION:
                    updateNotification();
                    break;
                case DISMISS_NOTIFICATION_ACTION:
                    cancelNotification();
                    break;

            }
        }
    }
}