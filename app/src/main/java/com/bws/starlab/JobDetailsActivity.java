package com.bws.starlab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bws.starlab.Commons.Common;
import com.bws.starlab.FragmentsView.MapFragment;
import com.bws.starlab.FragmentsView.ServiceDetailsFragment;
import com.bws.starlab.FragmentsView.TabOne;
import com.bws.starlab.FragmentsView.Tab_Four;
import com.bws.starlab.FragmentsView.Tab_Three;
import com.bws.starlab.FragmentsView.Tab_Two;
import com.bws.starlab.FragmentsView.UpdatereportFragment;
//import com.bws.starlab.Utils.DatabaseHelper;
import com.bws.starlab.Utils.PreferenceConnector;

import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class JobDetailsActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    Fragment fragment = null;

    Button btnTabCustomerDetails, btnTabPippeteDetails, btnTabLocation, btnTabCertificates;
    TextView textWorkOrderNo, textAccountManager, textMyContact, textEmail, textLabContact,
            textLabEmail, textPurchasingContact, textPurchasingEmail, textCompany, textDepartment,
            textAddress, textPoNo, textDate, textKeyTelephone, textkeyMobile, textLabTelephone,
            textLabMobile, textAccountNo, textPostCode, textJob_header, textFullName;
    ImageView imv_viewMap, imv_Home, imv_UpdateReport, imv_ViewSchedule;

   // DatabaseHelper db = DatabaseHelper.getInstance(this);
    AsyncHttpClient client;
    ProgressDialog pDialog;
    public String customerDetailUrl = Common.base_URL + "JobDetails/GetID";
    String asynchResult = "";
    LatLng origin;
    LatLng  destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        initView();
        clickEvent();

       getLatLongFromZipCode();

        /*  origin = new LatLng(Double.parseDouble(Common.sourceLat), Double.parseDouble(Common.sourceLong));
         destination = new LatLng(Double.parseDouble(Common.destLat), Double.parseDouble(Common.destLong));



        GeoApiContext geoApiContext = new GeoApiContext.Builder()
                .apiKey("AIzaSyDFVOM4ifkX8zm9l237F4S4iXAJaKTcato")
                .build();

        // - Perform the actual request
        DirectionsResult directionsResult = null;
        try {
            directionsResult = DirectionsApi.newRequest(geoApiContext)
                    .mode(TravelMode.DRIVING)
                    .origin(origin)
                    .destination(destination)
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

    private void getLatLongFromZipCode() {

        try {
            Location location1 = new Location("");
            location1.setLatitude(Double.parseDouble(Common.sourceLat));
            location1.setLongitude(Double.parseDouble(Common.sourceLong));

            Location location2 = new Location("");
            location2.setLatitude(Double.parseDouble(Common.destLat));
            location2.setLongitude(Double.parseDouble(Common.destLong));

            int distanceInKm = (int) location1.distanceTo(location2) / 1000;

            double miles = distanceInKm / 1.6;
            String strMiles = Double.toString(miles);

           /* int totalMin = 0;
            int hours = 0;
            int minutes = 0;

            if (distanceInKm >= 40) {
                int totalHrs = (int) distanceInKm / 40;// 40 km per hours

                totalMin = totalHrs * 60;
                hours = (int) totalMin / 60; //since both are ints, you get an int
                minutes = (int) totalMin % 60;
            } else {
                totalMin = distanceInKm * 100 / 40;
                hours = (int) totalMin / 60; //since both are ints, you get an int
                minutes = (int) totalMin % 60;
            }*/
            Common.estimatedDriveTimeInMinutes = strMiles + " Miles";
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void clickEvent() {

        findViewById(R.id.imv_Shutdown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //db.deleteAllUserDtails();
                PreferenceConnector.writeString(JobDetailsActivity.this,"ISLOGIN","");
                Intent i = new Intent(JobDetailsActivity.this, LoginActivity.class);
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

        btnTabCustomerDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new TabOne();

                textJob_header.setText("Customer");

                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    btnTabCustomerDetails.setBackgroundResource(R.drawable.ic_tab_yellow);
                    btnTabPippeteDetails.setBackgroundResource(R.drawable.ic_tab_gray);
                    btnTabLocation.setBackgroundResource(R.drawable.ic_tab_gray);
                    btnTabCertificates.setBackgroundResource(R.drawable.ic_tab_gray);

                    imv_Home.setEnabled(true);
                    imv_viewMap.setEnabled(true);
                    imv_UpdateReport.setEnabled(true);
                    imv_ViewSchedule.setEnabled(true);

                    imv_Home.setImageResource(R.mipmap.ic_home);
                    imv_viewMap.setImageResource(R.mipmap.ic_location);
                    imv_UpdateReport.setImageResource(R.mipmap.ic_update_report);
                    imv_ViewSchedule.setImageResource(R.mipmap.ic_view_schedule);

                } else {
                    Toast.makeText(JobDetailsActivity.this, "Error in creating fragment", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnTabPippeteDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new Tab_Two();
                textJob_header.setText("Pipette");

                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    btnTabCustomerDetails.setBackgroundResource(R.drawable.ic_tab_gray);
                    btnTabPippeteDetails.setBackgroundResource(R.drawable.ic_tab_yellow);
                    btnTabLocation.setBackgroundResource(R.drawable.ic_tab_gray);
                    btnTabCertificates.setBackgroundResource(R.drawable.ic_tab_gray);

                    imv_Home.setEnabled(true);
                    imv_viewMap.setEnabled(true);
                    imv_UpdateReport.setEnabled(true);
                    imv_ViewSchedule.setEnabled(true);

                    imv_Home.setImageResource(R.mipmap.ic_home);
                    imv_viewMap.setImageResource(R.mipmap.ic_location);
                    imv_UpdateReport.setImageResource(R.mipmap.ic_update_report);
                    imv_ViewSchedule.setImageResource(R.mipmap.ic_view_schedule);

                } else {
                    Toast.makeText(JobDetailsActivity.this, "Error in creating fragment", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnTabLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment = new Tab_Three();

                textJob_header.setText("Clinic Details");
                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    btnTabCustomerDetails.setBackgroundResource(R.drawable.ic_tab_gray);
                    btnTabPippeteDetails.setBackgroundResource(R.drawable.ic_tab_gray);
                    btnTabLocation.setBackgroundResource(R.drawable.ic_tab_yellow);
                    btnTabCertificates.setBackgroundResource(R.drawable.ic_tab_gray);

                    imv_Home.setEnabled(true);
                    imv_viewMap.setEnabled(true);
                    imv_UpdateReport.setEnabled(true);
                    imv_ViewSchedule.setEnabled(true);

                    imv_Home.setImageResource(R.mipmap.ic_home);
                    imv_viewMap.setImageResource(R.mipmap.ic_location);
                    imv_UpdateReport.setImageResource(R.mipmap.ic_update_report);
                    imv_ViewSchedule.setImageResource(R.mipmap.ic_view_schedule);

                } else {
                    Toast.makeText(JobDetailsActivity.this, "Error in creating fragment", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnTabCertificates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new Tab_Four();
                textJob_header.setText("Certificate");

                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    btnTabCustomerDetails.setBackgroundResource(R.drawable.ic_tab_gray);
                    btnTabPippeteDetails.setBackgroundResource(R.drawable.ic_tab_gray);
                    btnTabLocation.setBackgroundResource(R.drawable.ic_tab_gray);
                    btnTabCertificates.setBackgroundResource(R.drawable.ic_tab_yellow);
                    btnTabCertificates.requestFocus();

                    imv_Home.setEnabled(true);
                    imv_viewMap.setEnabled(true);
                    imv_UpdateReport.setEnabled(true);
                    imv_ViewSchedule.setEnabled(true);

                    imv_Home.setImageResource(R.mipmap.ic_home);
                    imv_viewMap.setImageResource(R.mipmap.ic_location);
                    imv_UpdateReport.setImageResource(R.mipmap.ic_update_report);
                    imv_ViewSchedule.setImageResource(R.mipmap.ic_view_schedule);

                } else {
                    Toast.makeText(JobDetailsActivity.this, "Error in creating fragment", Toast.LENGTH_SHORT).show();
                }
            }
        });

// for footer tab

        imv_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(JobDetailsActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();

            }
        });

        imv_viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imv_Home.setImageResource(R.mipmap.ic_home);
                imv_viewMap.setImageResource(R.mipmap.ic_location_hover);
                imv_UpdateReport.setImageResource(R.mipmap.ic_update_report);
                imv_ViewSchedule.setImageResource(R.mipmap.ic_view_schedule);

                btnTabCustomerDetails.setBackgroundResource(R.drawable.ic_tab_gray);
                btnTabPippeteDetails.setBackgroundResource(R.drawable.ic_tab_gray);
                btnTabLocation.setBackgroundResource(R.drawable.ic_tab_gray);
                btnTabCertificates.setBackgroundResource(R.drawable.ic_tab_gray);

                Common.pDialog.setMessage("Please wait...");
                Common.pDialog.show();
                Common.pDialog.setCancelable(false);

              /*  Common.pDialog = ProgressDialog.show(JobDetailsActivity.this, "Please wait...", " ", true);
                Common.pDialog.setIndeterminate(true);*/

                fragment = new MapFragment();

                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    imv_viewMap.setEnabled(false);
                    imv_Home.setEnabled(true);
                    imv_UpdateReport.setEnabled(true);
                    imv_ViewSchedule.setEnabled(true);
                    textJob_header.setText("View Map");
                    Common.pDialog.dismiss();
                } else {
                    Toast.makeText(JobDetailsActivity.this, "Error in creating fragment", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imv_UpdateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imv_Home.setImageResource(R.mipmap.ic_home);
                imv_viewMap.setImageResource(R.mipmap.ic_location);
                imv_UpdateReport.setImageResource(R.mipmap.ic_update_report_hover);
                imv_ViewSchedule.setImageResource(R.mipmap.ic_view_schedule);

                btnTabCustomerDetails.setBackgroundResource(R.drawable.ic_tab_gray);
                btnTabPippeteDetails.setBackgroundResource(R.drawable.ic_tab_gray);
                btnTabLocation.setBackgroundResource(R.drawable.ic_tab_gray);
                btnTabCertificates.setBackgroundResource(R.drawable.ic_tab_gray);

                fragment = new UpdatereportFragment();

                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    imv_viewMap.setEnabled(true);
                    imv_Home.setEnabled(true);
                    imv_UpdateReport.setEnabled(false);
                    imv_ViewSchedule.setEnabled(true);
                    textJob_header.setText("Update Report");
                } else {
                    Toast.makeText(JobDetailsActivity.this, "Error in creating fragment", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imv_ViewSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imv_Home.setImageResource(R.mipmap.ic_home);
                imv_viewMap.setImageResource(R.mipmap.ic_location);
                imv_UpdateReport.setImageResource(R.mipmap.ic_update_report);
                imv_ViewSchedule.setImageResource(R.mipmap.ic_view_schedule_hover);

                btnTabCustomerDetails.setBackgroundResource(R.drawable.ic_tab_gray);
                btnTabPippeteDetails.setBackgroundResource(R.drawable.ic_tab_gray);
                btnTabLocation.setBackgroundResource(R.drawable.ic_tab_gray);
                btnTabCertificates.setBackgroundResource(R.drawable.ic_tab_gray);

                fragment = new ServiceDetailsFragment();

                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    imv_viewMap.setEnabled(true);
                    imv_Home.setEnabled(true);
                    imv_UpdateReport.setEnabled(true);
                    imv_ViewSchedule.setEnabled(false);
                    textJob_header.setText("Service Details");
                } else {
                    Toast.makeText(JobDetailsActivity.this, "Error in creating fragment", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //    call customerDetails API using AsyncHttpClient
    private void customerDetails() throws JSONException, UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("workOrderNo", Common.workOrderNo);
        invokeCustomerDetails(jsonObject);
    }

    private void invokeCustomerDetails(JSONObject jsonObject) throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(jsonObject.toString());
        client = new AsyncHttpClient();
        client.setTimeout(3000);
        client.post(this, customerDetailUrl, entity, "application/json", new JsonHttpResponseHandler() {
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
                    new SweetAlertDialog(JobDetailsActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Server not responding try again.")
                            .show();

                } else {

                    try {
                        JSONObject jsonObject1 = new JSONObject(asynchResult);
                        status = jsonObject1.getString("status");
                        message = jsonObject1.getString("message");
// NOTE - For WORK No "42" data coming from server only for 42 but, for others data is coming all so taking
 //                       taking last of data plese dont confuse
                        if (status.equals("SUCCESS")) {
                            JSONArray jsonArray = jsonObject1.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                if(i == 1){
                                    break; // A unlabeled break is enough. You don't need a labeled break here.
                                }

                                textWorkOrderNo.setText(jsonObject.getString("orderNo"));// Changed
                                textAccountManager.setText(jsonObject.getString("accountManagerName")); //Changed
                                textMyContact.setText(jsonObject.getString("keyContact"));
                                textEmail.setText(jsonObject.getString("keyEmail"));
                                textKeyTelephone.setText(jsonObject.getString("keyTelephone"));
                                textkeyMobile.setText(jsonObject.getString("keyMobile"));
                                textLabContact.setText(jsonObject.getString("labContact"));
                                textLabEmail.setText(jsonObject.getString("labEmail"));
                                textLabTelephone.setText(jsonObject.getString("labTelephone"));
                                textLabMobile.setText(jsonObject.getString("labMobile"));
                                textPurchasingContact.setText(jsonObject.getString("purchasingContact"));
                                textPurchasingEmail.setText(jsonObject.getString("purchasingEmail"));
                                textCompany.setText(jsonObject.getString("companyID"));
                                textDepartment.setText(jsonObject.getString("department"));
                                textAddress.setText(jsonObject.getString("address"));
                                textPoNo.setText(jsonObject.getString("pOno"));
                                textDate.setText(jsonObject.getString("cusDate"));
                                textAccountNo.setText(jsonObject.getString("cusAccountNo"));
                                textPostCode.setText(jsonObject.getString("cusPostcode"));
                                Common.CreatedBy = jsonObject.getString("createdBy");
                                Common.RoleID = jsonObject.getString("roleID");

                                // Service details In Web TAB
                                // Sechedule In mobile TAB
                                Common.weekCommencing = jsonObject.getString("weekCommencing");
                                Common.serviceID = jsonObject.getString("serviceName");      // Changed
                                Common.isExternalClean = jsonObject.getString("isExternalClean");
                                Common.specialRequirementsClinic = jsonObject.getString("specialRequirementsClinic");
                                Common.scheduleAvailable = jsonObject.getString("scheduleAvailable");
                                Common.assignDateForm = jsonObject.getString("assignDateForm");
                                Common.assignDateTo = jsonObject.getString("assignDateTo");
                                Common.isNeedCar = jsonObject.getInt("isNeedCar");
                                Common.isOverNightStay = jsonObject.getInt("isOverNightStay");
                                Common.numberOverNightStay = jsonObject.getInt("numberOverNightStay");

                                Common.commentsTech = jsonObject.getString("commentsTech");
                                Common.assignTechnicianName = jsonObject.getString("assignTechnicianName");

                                // pippette

                                Common.tipOneTips = jsonObject.getString("tipOneTips");
                                Common.majorityPipettesServiced = jsonObject.getString("majorityPipettesServicedName");//Changed
                                Common.single30 = jsonObject.getString("single30");
                                Common.channel8 = jsonObject.getString("channel8");
                                Common.channel12 = jsonObject.getString("channel12");
                                Common.repeaters = jsonObject.getString("repeaters");
                                Common.bottleTops = jsonObject.getString("bottleTops");
                                Common.other = jsonObject.getString("other");
                                Common.isOneYear = jsonObject.getBoolean("isOneYear");
                                Common.isSixMonth = jsonObject.getBoolean("isSixMonth");
                                Common.isOther = jsonObject.getBoolean("isOther");

                                Common.calibrationDueOtherDate = jsonObject.getString("calibrationDueOtherDate");

                                //Location // Clinic Details


                                Common.clinicLocation = jsonObject.getString("clinicLocation");
                                Common.isSturdyTable = jsonObject.getBoolean("isSturdyTable");
                                Common.specialCondition = jsonObject.getString("specialCondition");
                                Common.isOnSiteParkingAvailable = jsonObject.getBoolean("isOnSiteParkingAvailable");
                                Common.parkingAvailableDetails = jsonObject.getString("parkingAvailableDetails");
                                Common.isOutsideBuilding = jsonObject.getBoolean("isOutsideBuilding");
                                Common.isPayAndDisplay = jsonObject.getBoolean("isPayAndDisplay");
                                Common.parkingCost = jsonObject.getString("parkingCost");
                                Common.isOnSiitePreBooked = jsonObject.getBoolean("isOnSiitePreBooked");
                                Common.nearestCarParkDetails = jsonObject.getString("nearestCarParkDetails");
                                Common.techniciansGainAccess = jsonObject.getString("techniciansGainAccess");
                                Common.isTechniciansAroundSite = jsonObject.getString("isTechniciansAroundSite");
                                Common.isPassProvided = jsonObject.getString("isPassProvided");

                                Common.techsInLocationClinic = jsonObject.getString("techsInLocationClinic");


                                // Certificate

                                Common.isUSBStick = jsonObject.getBoolean("isUSBStick");
                                Common.isCertificatesEmailed = jsonObject.getBoolean("isCertificatesEmailed");
                                Common.previousClinicComments = jsonObject.getString("previousClinicComments");
                                Common.invoicingRequirements = jsonObject.getString("invoicingRequirements");

                                //Update Report

                                Common.techniciansAttended1 =(jsonObject.getString("techniciansAttended1"));
                                Common.techniciansAttended2 = (jsonObject.getString("techniciansAttended2"));
                                Common.techniciansAttended3 = (jsonObject.getString("techniciansAttended3"));
                                Common.techniciansAttended4 =(jsonObject.getString("techniciansAttended4"));
                                Common.pipettesServicedSingle = (jsonObject.getString("pipettesServicedSingle"));
                                Common.pipettesServiced8ch = (jsonObject.getString("pipettesServiced8ch"));
                                Common.pipettesServiced12ch = (jsonObject.getString("pipettesServiced12ch"));
                                Common.pipettesServicedOther =(jsonObject.getString("pipettesServicedOther"));
                                Common.pipettesReturnedSingle = (jsonObject.getString("pipettesReturnedSingle"));
                                Common.pipettesReturned8ch = (jsonObject.getString("pipettesReturned8ch"));
                                Common.pipettesReturned12ch =(jsonObject.getString("pipettesReturned12ch"));
                                Common.pipettesReturnedOther =(jsonObject.getString("pipettesReturnedOther"));
                                Common.comments = (jsonObject.getString("comments"));
                                Common.recommendationsNextClinic = (jsonObject.getString("recommendationsNextClinic"));
                                Common.isCustomerHappy = jsonObject.getString("isCustomerHappy");
                                Common.similarDatesAllocated = (jsonObject.getString("similarDatesAllocated"));

                                // For Customer

                                Common.orderNo = (jsonObject.getString("orderNo"));//Add
                                Common.accountManager = (jsonObject.getString("accountManagerName"));//Changed
                                Common.keyContact = (jsonObject.getString("keyContact"));
                                Common.keyEmail = (jsonObject.getString("keyEmail"));
                                Common.keyTelephone = (jsonObject.getString("keyTelephone"));
                                Common.keyMobile = (jsonObject.getString("keyMobile"));
                                Common.labContact = (jsonObject.getString("labContact"));
                                Common.labEmail = (jsonObject.getString("labEmail"));
                                Common.labTelephone = (jsonObject.getString("labTelephone"));
                                Common.labMobile = (jsonObject.getString("labMobile"));
                                Common.purchasingContact = (jsonObject.getString("purchasingContact"));
                                Common.purchasingEmail = (jsonObject.getString("purchasingEmail"));
                                Common.companyID = (jsonObject.getString("companyID"));
                                Common.department = (jsonObject.getString("department"));
                                Common.address = (jsonObject.getString("address"));
                                Common.pOno = (jsonObject.getString("pOno"));
                                Common.cusDate = (jsonObject.getString("cusDate"));
                                Common.cusAccountNo = (jsonObject.getString("cusAccountNo"));
                                Common.cusPostcode = (jsonObject.getString("cusPostcode"));


                            }
                        } else {
                            pDialog.dismiss();
                            new SweetAlertDialog(JobDetailsActivity.this, SweetAlertDialog.ERROR_TYPE)
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

    private void initView() {
        btnTabCustomerDetails = (Button) findViewById(R.id.btnTabCustomerDetails);
        btnTabPippeteDetails = (Button) findViewById(R.id.btnTabPippeteDetails);
        btnTabLocation = (Button) findViewById(R.id.btnTabLocation);
        btnTabCertificates = (Button) findViewById(R.id.btnTabCertificates);

        textJob_header = (TextView) findViewById(R.id.textJob_header);
        textJob_header.setText("Customer");
        textFullName = (TextView) findViewById(R.id.textFullName);
        textFullName.setText("Hi  " + PreferenceConnector.readString(JobDetailsActivity.this, "fullName", ""));

        textWorkOrderNo = (TextView) findViewById(R.id.textWorkOrderNo);
        textAccountManager = (TextView) findViewById(R.id.textAccountManager);
        textMyContact = (TextView) findViewById(R.id.textMyContact);
        textEmail = (TextView) findViewById(R.id.textKeyEmail);
        textLabContact = (TextView) findViewById(R.id.textLabContact);
        textLabEmail = (TextView) findViewById(R.id.textLabEmail);
        textPurchasingContact = (TextView) findViewById(R.id.textPurchasingContact);
        textPurchasingEmail = (TextView) findViewById(R.id.textPurchasingEmail);
        textCompany = (TextView) findViewById(R.id.textCompany);
        textDepartment = (TextView) findViewById(R.id.textDepartment);
        textAddress = (TextView) findViewById(R.id.textAddress);

        textAddress.setMovementMethod(new ScrollingMovementMethod());

        textPoNo = (TextView) findViewById(R.id.textPoNo);
        textDate = (TextView) findViewById(R.id.textDate);
        textKeyTelephone = (TextView) findViewById(R.id.textKeyTelephone);
        textkeyMobile = (TextView) findViewById(R.id.textkeyMobile);
        textLabMobile = (TextView) findViewById(R.id.textLabMobile);
        textLabTelephone = (TextView) findViewById(R.id.textLabTelephone);
        textAccountNo = (TextView) findViewById(R.id.textAccountNo);
        textPostCode = (TextView) findViewById(R.id.textPostCode);

        imv_Home = (ImageView) findViewById(R.id.imv_Home);
        imv_viewMap = (ImageView) findViewById(R.id.imv_viewMap);
        imv_UpdateReport = (ImageView) findViewById(R.id.imv_UpdateReport);
        imv_ViewSchedule = (ImageView) findViewById(R.id.imv_ViewSchedule);

        btnTabCustomerDetails.setBackgroundResource(R.drawable.ic_tab_yellow);
        btnTabPippeteDetails.setBackgroundResource(R.drawable.ic_tab_gray);
        btnTabLocation.setBackgroundResource(R.drawable.ic_tab_gray);
        btnTabCertificates.setBackgroundResource(R.drawable.ic_tab_gray);

        pDialog = new ProgressDialog(JobDetailsActivity.this);
        Common.pDialog = new ProgressDialog(JobDetailsActivity.this);

        try {
            customerDetails();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
