package com.bws.starlab.Firebase;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.bws.starlab.MyJobActivity;
import com.bws.starlab.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";

    String message;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
     //   Log.d(TAG, "From: " + remoteMessage.getFrom());
     //   Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

       // String s = remoteMessage.getData().toString();
       // String s1 = remoteMessage.getNotification().getBody();
       // Log.d("dataChat",s);
        try
        {
            Map<String, String> params = remoteMessage.getData();
            JSONObject object = new JSONObject(params);
            Log.e("JSON_OBJECT", object.toString());

            message = object.getString("msg");
            Log.d("message===",message);

        }catch (Exception e){
            e.printStackTrace();
        }



        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Use for start notification activity if click on notification
        Intent intent = new Intent(this, MyJobActivity.class);
        Intent intentArr[] = {intent};
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0, intentArr, 0);

        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_app_icon)
                .setContentIntent(pendingIntent)
                .setContentTitle("StarLab")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message));
               // .setContentText(message);
        // .setCustomBigContentView(contentView);


        Notification notification = mBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(1, notification);

    }
}