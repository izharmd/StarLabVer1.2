package com.bws.starlab.Firebase;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.bws.starlab.Commons.Common;
import com.bws.starlab.DashboardActivity;
import com.bws.starlab.LoginActivity;
import com.bws.starlab.MyJobActivity;
import com.bws.starlab.R;
import com.bws.starlab.SplashActivity;
import com.bws.starlab.Utils.PreferenceConnector;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Date;
import java.util.Map;

import androidx.core.app.NotificationCompat;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";

    String message;
    PendingIntent pendingIntent;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String remote = remoteMessage.toString();

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
        String ISLOGIN =   PreferenceConnector.readString(getApplicationContext(),"ISLOGIN","");
        if (ISLOGIN.equals("")) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);
        } else {
            Intent intent = new Intent(this, MyJobActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);
        }
        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        String channelId = "izhar";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(getNotificationIcon())
                        .setContentTitle("StarLab")
                        .setContentText(message)
                        .setColor(getResources().getColor(R.color.white))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(m /* ID of notification */, notificationBuilder.build());

    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_app_icon : R.drawable.ic_app_icon;
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("NEW_TOKEN",s);

        PreferenceConnector.writeString(getApplicationContext(),"TOKEN",s);

    }
}