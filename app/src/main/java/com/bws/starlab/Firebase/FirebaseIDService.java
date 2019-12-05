/*
package com.bws.starlab.Firebase;

import android.util.Log;
import android.widget.Toast;

import com.bws.starlab.Commons.Common;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        Common.token = FirebaseInstanceId.getInstance().getToken();
       Log.d("Refreshed token: ",Common.token);

       // System.out.println("Token====" +refreshedToken);
       // Toast.makeText(this,refreshedToken,Toast.LENGTH_SHORT).show();


        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(Common.token);
    }

    */
/**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     *//*

    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
        //Common.token = token;
    }
}*/
