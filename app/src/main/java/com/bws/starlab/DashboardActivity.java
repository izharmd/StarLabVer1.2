package com.bws.starlab;
import android.content.Intent;
import androidx.core.content.IntentCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.bws.starlab.Utils.DatabaseHelper;
import com.bws.starlab.Utils.PreferenceConnector;
import com.google.android.gms.maps.model.LatLng;

public class DashboardActivity extends AppCompatActivity {
    LinearLayout ll_MyJob,ll_assets,ll_notification,ll_MyAccount,ll_mycalender;
    ImageView imv_Shutdown;
    TextView textFullName;
   // DatabaseHelper db = DatabaseHelper.getInstance(this);

    LatLng origin;
    LatLng destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dahboard);

        inintView();
        clicevent();



        origin = new LatLng(22.24353,88.3456789);
        destination = new LatLng(22.23456,88.2345678);



       /* GeoApiContext geoApiContext = new GeoApiContext.Builder()
                .apiKey("AIzaSyDFVOM4ifkX8zm9l237F4S4iXAJaKTcato")
                .build();

        // - Perform the actual request
        DirectionsResult directionsResult = null;

        try {
            directionsResult = DirectionsApi.newRequest(geoApiContext)
                    .mode(TravelMode.DRIVING)
                    .origin(String.valueOf(origin))
                    .destination(String.valueOf(destination))
                    .await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // - Parse the result
        DirectionsRoute route = directionsResult.routes[0];
        DirectionsLeg leg = route.legs[0];
        Duration duration = leg.duration;*/
    }





    private void clicevent() {

        ll_MyJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,MyJobActivity.class);
                startActivity(intent);
            }
        });
        ll_assets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,AssetsListActivity.class);
                startActivity(intent);
            }
        });

        ll_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DashboardActivity.this,NotificationActivity.class);
                startActivity(intent);
            }
        });
        ll_mycalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,MyCalenderActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.imv_Shutdown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceConnector.writeString(DashboardActivity.this,"ISLOGIN","");
               Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }

    private void inintView() {
        ll_MyJob = (LinearLayout)findViewById(R.id.ll_MyJob);
        ll_assets = (LinearLayout)findViewById(R.id.ll_assets);
        ll_notification = (LinearLayout)findViewById(R.id.ll_notification);
        ll_MyAccount = (LinearLayout)findViewById(R.id.ll_MyAccount);
        ll_mycalender = (LinearLayout)findViewById(R.id.ll_mycalender);
        imv_Shutdown = (ImageView) findViewById(R.id.imv_Shutdown);
        textFullName = (TextView)findViewById(R.id.textFullName);
        textFullName.setText("Hi  "+PreferenceConnector.readString(DashboardActivity.this,"fullName",""));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        DashboardActivity.this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

       /* Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        ComponentName cn = intent.getComponent();
        Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
        startActivity(mainIntent);*/
    }
}
