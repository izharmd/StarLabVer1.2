package com.bws.starlab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bws.starlab.Adapter.NotificationAdapter;
import com.bws.starlab.Commons.Common;
import com.bws.starlab.Models.NotificationModel;
import com.bws.starlab.Utils.DatabaseHelper;
import com.bws.starlab.Utils.InternetConnection;
import com.bws.starlab.Utils.PreferenceConnector;
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class NotificationActivity extends AppCompatActivity {

    TextView textJob_header, textFullName;
    List<NotificationModel> arrayNotificationList;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    DatabaseHelper db = DatabaseHelper.getInstance(this);
    AsyncHttpClient client;
    ProgressDialog pDialog;
    String asynchResult = "";
    public String notificationUrl = Common.base_URL + "Notification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        initView();
        clickEvent();
    }

    private void initView() {

        pDialog = new ProgressDialog(NotificationActivity.this);

        textJob_header = (TextView) findViewById(R.id.textJob_header);
        textJob_header.setText("Notification");
        textFullName = (TextView) findViewById(R.id.textFullName);
        textFullName.setText("Hi  " + PreferenceConnector.readString(NotificationActivity.this, "fullName", ""));
        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayNotificationList = new ArrayList<NotificationModel>();

        try {
            if (InternetConnection.checkConnection(NotificationActivity.this)) {
                notificationAPI();
            } else {
                new SweetAlertDialog(NotificationActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Please check your internet connection.")
                        .show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void clickEvent() {

        findViewById(R.id.imv_Shutdown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.deleteAllUserDtails();
                Intent i = new Intent(NotificationActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        findViewById(R.id.textBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void notificationAPI() throws JSONException, UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userid", PreferenceConnector.readString(this,"userid",""));
        jsonObject.put("rolename", PreferenceConnector.readString(this,"rolename",""));
        invokenotificationAPI(jsonObject);
    }

    private void invokenotificationAPI(JSONObject jsonObject) throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(jsonObject.toString());
        client = new AsyncHttpClient();
        client.setTimeout(3000);

        client.post(this, notificationUrl, entity, "application/json", new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                pDialog.setMessage("Please wait...");
                pDialog.show();
                pDialog.setCancelable(false);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                asynchResult = new String(String.valueOf(response));

                String status, message;

                if (asynchResult.isEmpty()) {
                    pDialog.dismiss();
                    new SweetAlertDialog(NotificationActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Server not responding try again.")
                            .show();

                } else {

                    try {
                        JSONObject jsonObject1 = new JSONObject(asynchResult);
                        status = jsonObject1.getString("status");
                        message = jsonObject1.getString("message");

                        if (status.equals("SUCCESS")) {
                            JSONArray jsonArray = jsonObject1.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                NotificationModel notificationModel = new NotificationModel();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                notificationModel.setNotificationBody(jsonObject.getString("notification"));
                                notificationModel.setGetNotificationDate(jsonObject.getString("dateA"));
                                notificationModel.setGetNotificationTime(jsonObject.getString("timeA"));
                                arrayNotificationList.add(notificationModel);
                            }

                            //use for recycleview devider
                            Drawable dividerDrawable = ContextCompat.getDrawable(NotificationActivity.this, R.drawable.line_divider);
                            recyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));

                            adapter = new NotificationAdapter(arrayNotificationList);
                            recyclerView.setAdapter(adapter);

                        } else {
                            pDialog.dismiss();
                            new SweetAlertDialog(NotificationActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText(message)
                                    .show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                pDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Status code", String.valueOf(+statusCode));

            }
        });
    }

}
