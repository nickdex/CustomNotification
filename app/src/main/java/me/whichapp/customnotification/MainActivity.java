package me.whichapp.customnotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity
{

    private static final int RQ_NOT = 90;
    private static final String CUSTOM_INTENT = "com.whichapp.updatenotif";
    private static final int NOTIF_ID = 10;

    private boolean toggle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BroadcastReceiver receiver  = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                if(intent.getAction().equals(CUSTOM_INTENT))
                {
                    Intent updateIntent = new Intent();
                    updateIntent.setAction(CUSTOM_INTENT);

                    PendingIntent updatePendingIntent = PendingIntent.getBroadcast(MainActivity.this, RQ_NOT, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_notification);
                    contentView.setTextViewText(R.id.title, "Custom notification");
                    contentView.setTextViewText(R.id.content, "Dummy content");
                    contentView.setImageViewResource(R.id.imageView, R.drawable.bell);
                    contentView.setImageViewResource(R.id.like_button, R.drawable.like);
                    contentView.setImageViewResource(R.id.button, R.drawable.paper);
                    contentView.setOnClickPendingIntent(R.id.like_button, updatePendingIntent);

                    if(toggle)
                    {
                        contentView.setImageViewResource(R.id.like_button, R.drawable.like);
                        toggle = false;
                    }
                    else {
                        contentView.setImageViewResource(R.id.like_button, R.drawable.heart);
                        toggle = true;
                    }

                    Notification.Builder builder = new Notification.Builder(MainActivity.this)
                            .setSmallIcon(R.drawable.bell)
                            .setAutoCancel(false)
                            // Set RemoteViews into Notification
                            .setContent(contentView);
                    // Create Notification Manager
                    NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                    Notification notification = builder.build();

                    // Build Notification with Notification Manager
                    notificationmanager.notify(NOTIF_ID, notification);
                }
            }
        };

        registerReceiver(receiver, new IntentFilter(CUSTOM_INTENT));
    }

    public void genNotif(View view)
    {
        toggle = true;

        Intent updateIntent = new Intent();
        updateIntent.setAction(CUSTOM_INTENT);

        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(this, RQ_NOT, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_notification);
        contentView.setTextViewText(R.id.title, "Custom notification");
        contentView.setTextViewText(R.id.content, "Dummy content");
        contentView.setImageViewResource(R.id.imageView, R.drawable.bell);
        contentView.setImageViewResource(R.id.like_button, R.drawable.like);
        contentView.setImageViewResource(R.id.button, R.drawable.paper);
        contentView.setOnClickPendingIntent(R.id.like_button, updatePendingIntent);
        toggle = true;

        Notification.Builder builder = new Notification.Builder(this)
                // Set Icon
                .setSmallIcon(R.drawable.bell)
                .setContent(contentView)
                .setAutoCancel(false);
                // Set RemoteViews into Notification


        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Notification notification = builder.build();

        // Build Notification with Notification Manager
        notificationmanager.notify(NOTIF_ID, notification);
    }
}
