package com.bws.starlab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.bluehomestudio.progresswindow.ProgressWindow;
import com.bluehomestudio.progresswindow.ProgressWindowConfiguration;
import com.bws.starlab.Adapter.CalenderAdapter;
import com.bws.starlab.Commons.Common;
import com.bws.starlab.Models.CalenderModel;
//import com.bws.starlab.Utils.DatabaseHelper;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class MyCalenderActivity extends AppCompatActivity {
    TextView textJob_header, textFullName;
    private RecyclerView recyclerView;
    List<CalenderModel> arrayCalenderLis;
    private RecyclerView.Adapter adapter;
    ImageView imv_calenderPrev, imv_calenderNext;

   // DatabaseHelper db = DatabaseHelper.getInstance(this);
    int year;
    int currentMonth = 0;
    AsyncHttpClient client;
    ProgressDialog pDialog;
   // public String mycaledarUrl = Common.base_URL + "Calender";
    public String calendarJobUrl = Common.base_URL + "JobDetails/Get";
    String asynchResult = "";
    //String calendarDate;
    // Button btnAssetsDetails;

    private ProgressWindow progressWindow ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_calender);
        Date date = new Date();
        SimpleDateFormat  formatter = new SimpleDateFormat("dd-MMM-yyyy");
        Common.calendarDate = formatter.format(date);
        Common.calendarDate = formatter.format(date);

        //ProgressBar progressBar =  (ProgressBar)findViewById(R.id.pb_main_progress);


// Note: Link  --->>> https://github.com/Applandeo/Material-Calendar-View


        initview();
        clickEvent();


        List<EventDay> events = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        //events.add(new EventDay(calendar, R.drawable.sample_icon));

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setEvents(events);
        try {
            calendarView.setDate(calendar);
        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }


        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {

                arrayCalenderLis.clear();

                Calendar clickedDayCalendar = eventDay.getCalendar();
                int d = clickedDayCalendar.get(Calendar.DATE);
                int m = clickedDayCalendar.get(Calendar.MONTH);
                int y = clickedDayCalendar.get(Calendar.YEAR);
                int month = m+1;

                String strDate = String.valueOf(d)+"/"+String.valueOf(month)+"/"+String.valueOf(y);
                SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
                Date date = null;
                try {
                    date = format1.parse(strDate);
                    Common.calendarDate = format2.format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    calendarJob();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });


        calendarView.setOnPreviousPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
                //Toast.makeText(MyCalenderActivity.this,"priview",Toast.LENGTH_SHORT).show();
            }
        });

        calendarView.setOnForwardPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
                //Toast.makeText(MyCalenderActivity.this,"forwrd",Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void progressConfigurations(){
        progressWindow = ProgressWindow.getInstance(this);
        ProgressWindowConfiguration progressWindowConfiguration = new ProgressWindowConfiguration();
        progressWindowConfiguration.backgroundColor = Color.parseColor("#32000000") ;
        progressWindowConfiguration.progressColor = Color.BLUE ;
        progressWindow.setConfiguration(progressWindowConfiguration);
    }
    //    call customerDetails API using AsyncHttpClient
    private void myCalendar() throws JSONException, UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("userid", PreferenceConnector.readString(this,"userid",""));
        jsonObject.put("rolename", PreferenceConnector.readString(this,"rolename",""));


        invokeCalendar(jsonObject);
    }

    private void invokeCalendar(JSONObject jsonObject) throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(jsonObject.toString());
        client = new AsyncHttpClient();
        client.setTimeout(3000);
        client.post(this, calendarJobUrl, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                pDialog.setMessage("Please wait...");
                pDialog.show();
                pDialog.setCancelable(false);

               // progressConfigurations();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                asynchResult = new String(String.valueOf(response));
                progressWindow.hideProgress();

                String status, message;
                if (asynchResult.isEmpty()) {
                    pDialog.dismiss();
                    new SweetAlertDialog(MyCalenderActivity.this, SweetAlertDialog.WARNING_TYPE)
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

                                CalenderModel calenderModel = new CalenderModel();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                calenderModel.setStartDate(jsonObject.getString("startDate"));
                                calenderModel.setCompanyName(jsonObject.getString("companyName"));
                                calenderModel.setServiceName(jsonObject.getString("serviceName"));
                                arrayCalenderLis.add(calenderModel);
                            }
                            adapter = new CalenderAdapter(arrayCalenderLis);
                            recyclerView.setAdapter(adapter);
                        } else {
                           /* pDialog.dismiss();
                            new SweetAlertDialog(MyCalenderActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText(message)
                                    .show();*/

                            progressWindow.hideProgress();
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
                progressWindow.hideProgress();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Status code", String.valueOf(+statusCode));
            }
        });
    }



    //    call All Job API using AsyncHttpClient
    private void calendarJob() throws JSONException, UnsupportedEncodingException {

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date = format.format(Date.parse(Common.calendarDate));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userid", PreferenceConnector.readString(this,"userid",""));
        jsonObject.put("rolename", PreferenceConnector.readString(this,"rolename",""));
        jsonObject.put("JobStatus", "DATEWISEJOB");
        jsonObject.put("DATE_FILTER", date);
        invokecalendarJob(jsonObject);
    }



    private void invokecalendarJob(JSONObject jsonObject) throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(jsonObject.toString());
        client = new AsyncHttpClient();
        client.setTimeout(300000);
        client.post(this, calendarJobUrl, entity, "application/json", new JsonHttpResponseHandler() {
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
                    new SweetAlertDialog(MyCalenderActivity.this, SweetAlertDialog.WARNING_TYPE)
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

                                CalenderModel calenderModel = new CalenderModel();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                calenderModel.setStartDate(jsonObject.getString("date"));
                                calenderModel.setCompanyName(jsonObject.getString("companyName"));
                                calenderModel.setServiceName(jsonObject.getString("service"));
                                arrayCalenderLis.add(calenderModel);
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                            adapter = new CalenderAdapter(arrayCalenderLis);
                            recyclerView.setAdapter(adapter);
                        } else {

                            pDialog.dismiss();
                            recyclerView.setVisibility(View.GONE);

                            Toast.makeText(MyCalenderActivity.this,"No data on this date.",Toast.LENGTH_SHORT).show();

                            /*new SweetAlertDialog(MyCalenderActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                    //.setTitleText("Oops...")
                                    .setContentText(message)
                                    .show();*/
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




    @Override
    protected void onPause() {
        super.onPause();
    }
    private void initview() {
        imv_calenderPrev = (ImageView) findViewById(R.id.imv_calenderPrev);
        imv_calenderNext = (ImageView) findViewById(R.id.imv_calenderNext);
        textJob_header = (TextView) findViewById(R.id.textJob_header);
        textJob_header.setText("My Calendar");
        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pDialog = new ProgressDialog(MyCalenderActivity.this);
        textFullName = (TextView) findViewById(R.id.textFullName);
        textFullName.setText("Hi  " + PreferenceConnector.readString(MyCalenderActivity.this, "fullName", ""));

        arrayCalenderLis = new ArrayList<CalenderModel>();

        //use for recycleview devider
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.line_divider);
        recyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));

        try {
            //myCalendar();
            calendarJob();
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
                //db.deleteAllUserDtails();
                PreferenceConnector.writeString(MyCalenderActivity.this,"ISLOGIN","");
                Intent i = new Intent(MyCalenderActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });


        imv_calenderNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentMonth <= 12 && currentMonth > 1) {
                    currentMonth = currentMonth + 1;
                    String nextmonth1 = String.valueOf(currentMonth);
                    Log.d("rtyui", nextmonth1);
                }
            }
        });


        imv_calenderPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentMonth <= 12 && currentMonth > 1) {
                    currentMonth = currentMonth - 1;
                    Log.d("prev==", String.valueOf(currentMonth));
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

}
