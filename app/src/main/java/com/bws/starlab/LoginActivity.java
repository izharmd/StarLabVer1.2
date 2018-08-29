package com.bws.starlab;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bws.starlab.Commons.Common;
import com.bws.starlab.Models.UserModel;
import com.bws.starlab.Utils.DatabaseHelper;
import com.bws.starlab.Utils.InternetConnection;
import com.bws.starlab.Utils.PreferenceConnector;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.firebase.iid.FirebaseInstanceId;
import com.loopj.android.http.AsyncHttpClient;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.app.ProgressDialog;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;

public class LoginActivity extends AppCompatActivity {

    Button btnSignIn, btnExit;
    EditText editUserName, editPassword;
    TextView textForgetPass;
    ImageView imv_hidePassword;
    CheckBox checkbox;
    boolean rememberMe;

    NiftyDialogBuilder animatedDialog;
    NiftyDialogBuilder animatedDialogExit;
    private int passwordNotVisible = 1;

    DatabaseHelper db = DatabaseHelper.getInstance(LoginActivity.this);

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    AsyncHttpClient client;
    ProgressDialog pDialog;
    ProgressDialog PD;
    String strPram;
    public String loginUrl = Common.base_URL + "Registration/Get";
    public String frogetPassUrl = Common.base_URL + "ForgetPassword/Get";
    String asynchResult = "";

    private static View systemUIView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiView();
        clickEvent();
    }

    // Click event
    private void clickEvent() {

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String password = editPassword.getText().toString();
                if (editUserName.getText().toString().trim().equals("")) {
                    editUserName.requestFocus();
                    editUserName.setError("can't be empty!");
                } else if (password.length() <= 0) {
                    editPassword.requestFocus();
                    editPassword.setError("can't be empty!");
                } else {

//  Use for send parameter as json object
                    try {
                        if (InternetConnection.checkConnection(LoginActivity.this)) {
                            loginUser();
                        } else {
                            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
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
            }
        });

        imv_hidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passwordNotVisible == 1) {
                    editPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordNotVisible = 0;
                    imv_hidePassword.setImageResource(R.drawable.ic_pass_show);
                } else {
                    editPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordNotVisible = 1;
                    imv_hidePassword.setImageResource(R.drawable.ic_pass_hide);
                }
                editPassword.setSelection(editPassword.length());
            }
        });


        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                animatedDialogExit.withTitle("StarLab")
                        .withMessage("Do you want to exit?")
                        .withDialogColor("#004f92")
                        .withTitleColor("#FFFFFF")
                        .withButton1Text("Yes")
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        })
                        .withButton2Text("No")
                        .setButton2Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                animatedDialogExit.dismiss();
                            }
                        })
                        .withDuration(500)
                        .withEffect(Effectstype.Fall)
                        .show();
            }
        });

        textForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animatedDialog
                        .withTitle("StarLab")
                        .setCustomView(R.layout.dialog_forgetpassword, LoginActivity.this)
                        .withDialogColor("#004f92")
                        .withTitleColor("#FFFFFF")
                        .withDuration(500)
                        .withEffect(Effectstype.Fall)
                        .show();
                final EditText editForgetPass = (EditText) animatedDialog.findViewById(R.id.editForgetPass);
                Button btnDialog_send = (Button) animatedDialog.findViewById(R.id.btnDialog_forgetPass);
                btnDialog_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        JSONObject requestObject = new JSONObject();
                        try {
                            requestObject.put("email", editForgetPass.getText().toString());
                            strPram = requestObject.toString();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //  Use for send parameter as json object
                        try {
                            if (InternetConnection.checkConnection(LoginActivity.this)) {
                                forgetPassPostRequest(frogetPassUrl, strPram);
                            } else {
                                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Please check your internet connection.")
                                        .show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        animatedDialog.dismiss();
                    }
                });
            }
        });

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkbox.isChecked() == true) {
                    rememberMe = true;
                } else {
                    rememberMe = false;
                }
            }
        });

    }

    //    call login API using AsyncHttpClient
    private void loginUser() throws JSONException, UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", editUserName.getText().toString());
        jsonObject.put("password", editPassword.getText().toString());
        invokeLogin(jsonObject);
    }

    private void invokeLogin(JSONObject jsonObject) throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(jsonObject.toString());
        client = new AsyncHttpClient();
        client.setTimeout(30000);
        client.post(this, loginUrl, entity, "application/json", new JsonHttpResponseHandler() {

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
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Server not responding try again.")
                            .show();

                } else {
//    Save user details in local database
                    if (rememberMe == true) {
                        saveUserDetails();
                    }
                    try {
                        JSONObject jsonObject1 = new JSONObject(asynchResult);
                        status = jsonObject1.getString("status");
                        message = jsonObject1.getString("message");

                        if (status.equals("SUCCESS")) {
                            JSONArray jsonArray = jsonObject1.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                PreferenceConnector.writeString(LoginActivity.this, "userid", jsonObject.getString("userid"));
                                PreferenceConnector.writeString(LoginActivity.this, "rolename", jsonObject.getString("rolename"));
                                Common.userFristName = jsonObject.getString("fname");
                                Common.userLastName = jsonObject.getString("fname");

//   Save user name in sharepreference
                                PreferenceConnector.writeString(LoginActivity.this, "fullName", jsonObject.getString("fullname"));

                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            pDialog.dismiss();
                            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
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


    //       call forgetpassword API
    public void forgetPassPostRequest(String frogetPassUrl, String strPram) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, strPram);
        Request request = new Request.Builder().url(frogetPassUrl).post(body).build();

        pDialog.setMessage("Please wait...");
        pDialog.show();
        pDialog.setCancelable(false);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                pDialog.dismiss();
                try {
                    JSONObject result = new JSONObject(response.body().string());
                    System.out.println("Response=======" + result);
                    String status = result.getString("status");
                    final String message = result.getString("message");
                    if (status.equals("SUCCESS")) {

                        LoginActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                pDialog.dismiss();
                                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setContentText("Your password has sent on your email.")
                                        .show();
                            }
                        });

                    } else {
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                pDialog.dismiss();
                                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText(message)
                                        .show();
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void intiView() {
        db = DatabaseHelper.getInstance(this);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnExit = (Button) findViewById(R.id.btnExit);
        editUserName = (EditText) findViewById(R.id.editUserName);
        editPassword = (EditText) findViewById(R.id.editPassword);
        imv_hidePassword = (ImageView) findViewById(R.id.imv_hidePassword);
        textForgetPass = (TextView) findViewById(R.id.textForgetPass);
        checkbox = (CheckBox) findViewById(R.id.checkbox);

        pDialog = new ProgressDialog(LoginActivity.this);

        animatedDialog = NiftyDialogBuilder.getInstance(this);
        animatedDialogExit = NiftyDialogBuilder.getInstance(this);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        // Toast.makeText(LoginActivity.this,refreshedToken,Toast.LENGTH_SHORT).show();
    }

    // save user details in local database
    private void saveUserDetails() {
        UserModel userModel = new UserModel();
        userModel.setUserName(editUserName.getText().toString());
        userModel.setPassword(editPassword.getText().toString());

        if (db.insertUserDetils(userModel)) {
            //Toast.makeText(LoginActivity.this, "Successfully save", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data not Saved", Toast.LENGTH_SHORT).show();
        }
    }
}
