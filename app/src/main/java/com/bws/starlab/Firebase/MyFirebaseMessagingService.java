package com.bws.starlab.Firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.bws.starlab.Commons.Common;
import com.bws.starlab.LoginActivity;
import com.bws.starlab.Models.UserModel;
import com.bws.starlab.PushNotificationActivity;
import com.bws.starlab.R;
import com.bws.starlab.Utils.DatabaseHelper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";

    DatabaseHelper db = DatabaseHelper.getInstance(this);
    String message;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        String s = remoteMessage.getData().toString();
        Log.d("dataChat",s);
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

       // UserModel userModel = new UserModel();
       // db.insertUserDetils(userModel);

        // Check if message contains a data payload.
        /*if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }*/

       /* Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        String message = remoteMessage.getNotification().getBody();
*/
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Use for start notification activity if click on notification
        Intent intent = new Intent(this, PushNotificationActivity.class);
        Intent intentArr[] = {intent};
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0, intentArr, 0);

      /*  RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_push);
        contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
        contentView.setTextViewText(R.id.title, "StarLab");
       // contentView.setTextViewText(R.id.textDate, "16-5-18");
        contentView.setTextViewText(R.id.text, "Change in schedule for maintenance activity\n" +
                "New schedule maintenance at 4:30 p.m");*/

        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_app_icon)
                .setContentIntent(pendingIntent)
                .setContentTitle("StarLab")
                .setContentText(message);
               // .setCustomBigContentView(contentView);


        Notification notification = mBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(1, notification);

    }
}