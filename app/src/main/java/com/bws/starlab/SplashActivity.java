package com.bws.starlab;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bws.starlab.Commons.Common;
//import com.bws.starlab.Utils.DatabaseHelper;
import com.bws.starlab.Utils.InternetConnection;
import com.bws.starlab.Utils.PreferenceConnector;
import com.google.firebase.iid.FirebaseInstanceId;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if (InternetConnection.checkConnection(SplashActivity.this)) {

                  String ISLOGIN =   PreferenceConnector.readString(SplashActivity.this,"ISLOGIN","");

                    if (ISLOGIN.equals("")) {
                        Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(mainIntent);
                        finish();
                    } else {
                        Intent mainIntent = new Intent(SplashActivity.this, DashboardActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                } else {
                    new SweetAlertDialog(SplashActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Please check your internet connection.")
                            .show();
                }






            }
        }, SPLASH_DISPLAY_LENGTH);
    }

   /* //To populate the details
    public void populateDetails() {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);
        Cursor cursor = dbHelper.GetUser();
        if (cursor.moveToFirst()) {
            do {
                userName = cursor.getString(0);

            } while (cursor.moveToNext());
        }
        cursor.close();
    }*/
}
