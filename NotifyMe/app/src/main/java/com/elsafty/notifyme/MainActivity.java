package com.elsafty.notifyme;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
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
    private static final int REQUEST_CODE_FOR_PENDING_INTENT = 276;
    private Button btnNotifyMe, btnUpdateME , btnCancelMe;

    private NotificationManager mNotificationManager;

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
        btnCancelMe  = findViewById(R.id.cancel_me);
        btnCancelMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelNotification();
            }
        });
        toggle(true,false,false);


        createNotificationChannel();

    }

    private void updateNotification() {
        NotificationCompat.Builder builder = getNotificationBuilder();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.mascot_1);
        builder.setStyle(new NotificationCompat.
                BigPictureStyle().
                bigPicture(bitmap).
                setBigContentTitle("Notification Updated!"));

        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
        toggle(false,false,true);
    }

    private void cancelNotification() {
        mNotificationManager.cancel(NOTIFICATION_ID);
        toggle(true,false,false);
    }

    public void toggle(boolean notify_me,boolean update_me,boolean cancel_me){
        btnNotifyMe.setEnabled(notify_me);
        btnUpdateME.setEnabled(update_me);
        btnCancelMe.setEnabled(cancel_me);
    }



    public void sendNotification() {
        NotificationCompat.Builder builder = getNotificationBuilder();
        mNotificationManager.notify(NOTIFICATION_ID,
                builder.build());
        toggle(false,true,true);
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
                REQUEST_CODE_FOR_PENDING_INTENT,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_android)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentTitle("You've been notified!")
                .setContentText("This is your notification text.")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent);
        return builder;
    }
}