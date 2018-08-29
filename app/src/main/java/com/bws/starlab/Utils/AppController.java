package com.bws.starlab.Utils;

import android.app.Application;
import android.database.SQLException;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    private static AppController mInstance;


    public static synchronized AppController getInstance() {
        return mInstance;
    }


    @Override
    public void onCreate() {
        mInstance = this;

        //open database
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(mInstance);
        try {
            dbHelper.createDataBase();
            System.out.println("database created.");
        } catch (Exception ioe) {
            throw new Error("Unable to create database");
        }
        try {
            dbHelper.openDataBase();
           // dbHelper.updateDB();
           // System.out.println("database opened.");
        } catch (SQLException sqle) {
            throw sqle;
        }

//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/MONTSERRAT-BLACK.OTF")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );
    }

}
