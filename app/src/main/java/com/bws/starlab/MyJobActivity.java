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
import android.widget.ImageView;
import android.widget.TextView;

import com.bws.starlab.Adapter.JobDetailsAdapter;
import com.bws.starlab.Commons.Common;
import com.bws.starlab.Models.JobDetailsModel;
import com.bws.starlab.Utils.DatabaseHelper;
import com.bws.starlab.Utils.InternetConnection;
import com.bws.starlab.Utils.PreferenceConnector;
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.widget.Button;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MyJobActivity extends AppCompatActivity {
    TextView textJob_header, textFullName;
    ImageView imv_Shutdown, imv_header;
    private RecyclerView recyclerView;
    Button btnToday, btnAll;
    List<JobDetailsModel> arrayPrioriryJob;
    private RecyclerView.Adapter adapter;
    DatabaseHelper db = DatabaseHelper.getInstance(this);
    AsyncHttpClient client;
    ProgressDialog pDialog;
    String asynchResult = "";
    public String jobdetailsUrl = Common.base_URL + "JobDetails/Get";
    String JobDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_test);

        initView();
        clickEvent();
    }

    private void clickEvent() {

        findViewById(R.id.imv_Shutdown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.deleteAllUserDtails();
                Intent i = new Intent(MyJobActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayPrioriryJob.clear();
                btnToday.setBackgroundResource(R.drawable.ic_yellow);
                btnAll.setBackgroundResource(R.drawable.ic_grey);

                try {
                    if (InternetConnection.checkConnection(MyJobActivity.this)) {
                        JobDetails = "TODAYJOB";
                        jobDetails();
                        btnToday.setEnabled(false);
                        btnAll.setEnabled(true);
                    } else {
                        new SweetAlertDialog(MyJobActivity.this, SweetAlertDialog.WARNING_TYPE)
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
        });

        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayPrioriryJob.clear();
                btnToday.setBackgroundResource(R.drawable.ic_grey);
                btnAll.setBackgroundResource(R.drawable.ic_yellow);

                try {
                    if (InternetConnection.checkConnection(MyJobActivity.this)) {
                        JobDetails = "ALLJOB";
                        jobDetails();

                        btnAll.setEnabled(false);
                        btnToday.setEnabled(true);
                    } else {
                        new SweetAlertDialog(MyJobActivity.this, SweetAlertDialog.WARNING_TYPE)
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
        });

        findViewById(R.id.textBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    //    call All Job API using AsyncHttpClient
    private void jobDetails() throws JSONException, UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userid", PreferenceConnector.readString(this,"userid",""));
        jsonObject.put("rolename", PreferenceConnector.readString(this,"rolename",""));
        jsonObject.put("JobStatus", JobDetails);
        invokeAllJob(jsonObject);
    }

    private void invokeAllJob(JSONObject jsonObject) throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(jsonObject.toString());
        client = new AsyncHttpClient();
        client.setTimeout(30000);

        client.post(this, jobdetailsUrl, entity, "application/json", new JsonHttpResponseHandler() {

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
                    new SweetAlertDialog(MyJobActivity.this, SweetAlertDialog.WARNING_TYPE)
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
                                JobDetailsModel priorityJobModel = new JobDetailsModel();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                priorityJobModel.setCompanyName(jsonObject.getString("companyName"));
                                priorityJobModel.setDate(jsonObject.getString("date"));
                                priorityJobModel.setWorkOrderNo(jsonObject.getString("workOrderNo"));
                                priorityJobModel.setService(jsonObject.getString("service"));

                                priorityJobModel.setSourceLat(jsonObject.getString("sourceLat"));
                                priorityJobModel.setSourceLong(jsonObject.getString("sourceLong"));
                                priorityJobModel.setDestLat(jsonObject.getString("destinationLat"));
                                priorityJobModel.setDestLong(jsonObject.getString("destinationLong"));
                                arrayPrioriryJob.add(priorityJobModel);
                            }

                            recyclerView.setVisibility(View.VISIBLE);
                            adapter = new JobDetailsAdapter(arrayPrioriryJob);
                            recyclerView.setAdapter(adapter);

                        } else {
                            pDialog.dismiss();
                            recyclerView.setVisibility(View.INVISIBLE);
                            new SweetAlertDialog(MyJobActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("No jobs!..")
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

    private void initView() {

        imv_Shutdown = (ImageView) findViewById(R.id.imv_Shutdown);
        imv_header = (ImageView) findViewById(R.id.imv_header);
        textJob_header = (TextView) findViewById(R.id.textJob_header);
        textJob_header.setText("My Jobs");
        textFullName = (TextView) findViewById(R.id.textFullName);
        textFullName.setText("Hi  " + PreferenceConnector.readString(MyJobActivity.this, "fullName", ""));
        btnToday = (Button) findViewById(R.id.btnToday);
        btnAll = (Button) findViewById(R.id.btnAll);
        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        pDialog = new ProgressDialog(MyJobActivity.this);

        //use for recycleview devider
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.line_divider);
        recyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        arrayPrioriryJob = new ArrayList<JobDetailsModel>();

        try {
            if (InternetConnection.checkConnection(MyJobActivity.this)) {
                JobDetails = "TODAYJOB";
                jobDetails();
                btnToday.setEnabled(false);
                btnAll.setEnabled(true);
            } else {
                new SweetAlertDialog(MyJobActivity.this, SweetAlertDialog.WARNING_TYPE)
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}