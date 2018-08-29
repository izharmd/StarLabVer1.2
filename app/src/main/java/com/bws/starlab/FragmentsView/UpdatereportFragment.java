package com.bws.starlab.FragmentsView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.bws.starlab.Commons.Common;
import com.bws.starlab.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by BWS on 09/05/2018.
 */

public class UpdatereportFragment extends Fragment {

    View rootView;
    EditText editTecOne, editTecTwo, editTecThree, editTecFour, editPepptOne, editPepptTwo,
            editPepptThree, editPepptfour, editPepptLabOne, editPepptLabTwo, editPepptLabThree,
            editPepptLabFour, editComment, editRecomendation, editSimilar;

    RadioButton rdYes, rdNo;
    Button btnSaveReport;

    AsyncHttpClient client;
    ProgressDialog pDialog;
    public String updateReportsUrl = Common.base_URL + "JobDetails/UpdateJob";
    public String getReportsUrl = Common.base_URL + "JobDetails/GetID";
    String asynchResult = "";
    String customerHappy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_update_report, container, false);

        initview();
        clickEvent();

        return rootView;
    }


    private void clickEvent() {

        rdYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rdYes.setChecked(true);
                rdNo.setChecked(false);
                customerHappy = "Y";

            }
        });

        rdNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rdNo.setChecked(true);
                rdYes.setChecked(false);
                customerHappy = "N";

            }
        });


        btnSaveReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    updatereport();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });


    }


    //    call login API using AsyncHttpClient
    private void getReportByOrderNo() throws JSONException, UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("WorkOrderNo", Common.workOrderNo);
        invokeGetReport(jsonObject);
    }

    private void invokeGetReport(JSONObject jsonObject) throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(jsonObject.toString());
        client = new AsyncHttpClient();
        client.setTimeout(3000);

        client.post(getContext(), getReportsUrl, entity, "application/json", new JsonHttpResponseHandler() {

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
                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
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
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                editTecOne.setText(jsonObject.getString("techniciansAttended1"));
                                editTecTwo.setText(jsonObject.getString("techniciansAttended2"));
                                editTecThree.setText(jsonObject.getString("techniciansAttended3"));
                                editTecFour.setText(jsonObject.getString("techniciansAttended4"));
                                editPepptOne.setText(jsonObject.getString("pipettesServicedSingle"));
                                editPepptTwo.setText(jsonObject.getString("pipettesServiced8ch"));
                                editPepptThree.setText(jsonObject.getString("pipettesServiced12ch"));
                                editPepptfour.setText(jsonObject.getString("pipettesServicedOther"));
                                editPepptLabOne.setText(jsonObject.getString("pipettesReturnedSingle"));
                                editPepptLabTwo.setText(jsonObject.getString("pipettesReturned8ch"));
                                editPepptLabThree.setText(jsonObject.getString("pipettesReturned12ch"));
                                editPepptLabFour.setText(jsonObject.getString("pipettesReturnedOther"));
                                editComment.setText(jsonObject.getString("comments"));
                                editRecomendation.setText(jsonObject.getString("recommendationsNextClinic"));
                                customerHappy = jsonObject.getString("isCustomerHappy");
                                editSimilar.setText(jsonObject.getString("similarDatesAllocated"));

                                if (customerHappy.equals("Y")) {
                                    rdYes.setChecked(true);
                                } else {
                                    rdNo.setChecked(true);
                                }

                            }
                        } else {
                            pDialog.dismiss();
                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
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


    //    call login API using AsyncHttpClient
    private void updatereport() throws JSONException, UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("WorkOrderNo", Common.workOrderNo);
        jsonObject.put("TechniciansAttended1", editTecOne.getText().toString());
        jsonObject.put("TechniciansAttended2", editTecTwo.getText().toString());
        jsonObject.put("TechniciansAttended3", editTecThree.getText().toString());
        jsonObject.put("TechniciansAttended4", editTecFour.getText().toString());

        jsonObject.put("PipettesServicedSingle", editPepptOne.getText().toString());
        jsonObject.put("PipettesServiced8ch", editPepptTwo.getText().toString());
        jsonObject.put("PipettesServiced12ch", editPepptThree.getText().toString());
        jsonObject.put("PipettesServicedOther", editPepptfour.getText().toString());

        jsonObject.put("PipettesReturnedSingle", editPepptLabOne.getText().toString());
        jsonObject.put("PipettesReturned8ch", editPepptLabTwo.getText().toString());
        jsonObject.put("PipettesReturned12ch", editPepptLabThree.getText().toString());
        jsonObject.put("PipettesReturnedOther", editPepptLabFour.getText().toString());

        jsonObject.put("Comments", editComment.getText().toString());
        jsonObject.put("RecommendationsNextClinic", editRecomendation.getText().toString());
        jsonObject.put("SimilarDatesAllocated", editSimilar.getText().toString());

        jsonObject.put("IsCustomerHappy", customerHappy);

        jsonObject.put("CreatedBy", Common.CreatedBy);
        jsonObject.put("RoleID", Common.RoleID);

        invokeupdatereport(jsonObject);
    }

    private void invokeupdatereport(JSONObject jsonObject) throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(jsonObject.toString());
        client = new AsyncHttpClient();
        client.setTimeout(30000);

        client.post(getContext(), updateReportsUrl, entity, "application/json", new JsonHttpResponseHandler() {

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
                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Server not responding try again.")
                            .show();

                } else {

                    try {
                        JSONObject jsonObject1 = new JSONObject(asynchResult);
                        status = jsonObject1.getString("status");
                        message = jsonObject1.getString("message");

                       // pDialog.dismiss();
                        new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                //.setTitleText("Oops...")
                                .setContentText(message)
                                .show();

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


    private void initview() {

        editTecOne = (EditText) rootView.findViewById(R.id.editTecOne);
        editTecTwo = (EditText) rootView.findViewById(R.id.editTecTwo);
        editTecThree = (EditText) rootView.findViewById(R.id.editTecThree);
        editTecFour = (EditText) rootView.findViewById(R.id.editTecFour);
        editPepptOne = (EditText) rootView.findViewById(R.id.editPepptOne);
        editPepptTwo = (EditText) rootView.findViewById(R.id.editPepptTwo);
        editPepptThree = (EditText) rootView.findViewById(R.id.editPepptThree);
        editPepptfour = (EditText) rootView.findViewById(R.id.editPepptfour);
        editPepptLabOne = (EditText) rootView.findViewById(R.id.editPepptLabOne);
        editPepptLabTwo = (EditText) rootView.findViewById(R.id.editPepptLabTwo);
        editPepptLabThree = (EditText) rootView.findViewById(R.id.editPepptLabThree);
        editPepptLabFour = (EditText) rootView.findViewById(R.id.editPepptLabFour);
        editComment = (EditText) rootView.findViewById(R.id.editComment);
        editRecomendation = (EditText) rootView.findViewById(R.id.editRecomendation);
        editSimilar = (EditText) rootView.findViewById(R.id.editSimilar);

        rdYes = (RadioButton) rootView.findViewById(R.id.rdYes);
        rdNo = (RadioButton) rootView.findViewById(R.id.rdNo);

        btnSaveReport = (Button) rootView.findViewById(R.id.btnSaveReport);


        pDialog = new ProgressDialog(getContext());

        /*editTecOne.setText(Common.techniciansAttended1);
        editTecTwo.setText(Common.techniciansAttended2);
        editTecThree.setText(Common.techniciansAttended3);
        editTecFour.setText(Common.techniciansAttended4);
        editPepptOne.setText(Common.pipettesServicedSingle);
        editPepptTwo.setText(Common.pipettesServiced8ch);
        editPepptThree.setText(Common.pipettesServiced12ch);
        editPepptfour.setText(Common.pipettesServicedOther);
        editPepptLabOne.setText(Common.pipettesReturnedSingle);
        editPepptLabTwo.setText(Common.pipettesReturned8ch);
        editPepptLabThree.setText(Common.pipettesReturned12ch);
        editPepptLabFour.setText(Common.pipettesReturnedOther);
        editComment.setText(Common.comments);
        editRecomendation.setText(Common.recommendationsNextClinic);
        customerHappy = Common.isCustomerHappy;
        editSimilar.setText(Common.similarDatesAllocated);

        if (customerHappy.equals("Y")) {
            rdYes.setChecked(true);
        } else {
            rdNo.setChecked(true);
        }*/


        try {
            getReportByOrderNo();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
