package com.bws.starlab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.bws.starlab.Utils.DatabaseHelper;
import com.bws.starlab.Utils.PreferenceConnector;

public class NotificationDetailsActivity extends AppCompatActivity {
    TextView textNotificationBody;
    TextView textJob_header,textFullName;
    DatabaseHelper db = DatabaseHelper.getInstance(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);

        initView();
        clickEvent();
    }

    private void clickEvent() {

        findViewById(R.id.imv_Shutdown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteAllUserDtails();
                Intent i = new Intent(NotificationDetailsActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        findViewById(R.id.textBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotificationDetailsActivity.this,NotificationActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initView() {

        textNotificationBody = (TextView)findViewById(R.id.textNotificationBody);
        textJob_header = (TextView) findViewById(R.id.textJob_header);
        textJob_header.setText("Notification");
        textFullName = (TextView)findViewById(R.id.textFullName);
        textFullName.setText("Hi  "+PreferenceConnector.readString(NotificationDetailsActivity.this,"fullName",""));
        Bundle bundle = this.getIntent().getExtras();
        textNotificationBody.setText(bundle.getString("notificationBody"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(NotificationDetailsActivity.this,NotificationActivity.class);
        startActivity(intent);
        finish();
    }
}
