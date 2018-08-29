package com.bws.starlab;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bws.starlab.Commons.Common;
import com.bws.starlab.Utils.DatabaseHelper;
import com.bws.starlab.Utils.PreferenceConnector;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("Token===",token);
        Toast.makeText(SplashActivity.this,token,Toast.LENGTH_SHORT).show();

        populateDetails();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (userName != null) {
                    Intent mainIntent = new Intent(SplashActivity.this, DashboardActivity.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    //To populate the details
    public void populateDetails() {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);
        Cursor cursor = dbHelper.GetUser();
        if (cursor.moveToFirst()) {
            do {
                userName = cursor.getString(0);

            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}
