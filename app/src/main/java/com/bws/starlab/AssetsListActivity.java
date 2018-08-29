package com.bws.starlab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bws.starlab.Adapter.AssetsListAdapter;
import com.bws.starlab.Commons.Common;
import com.bws.starlab.Models.AssetsListModel;
import com.bws.starlab.Utils.DatabaseHelper;
import com.bws.starlab.Utils.PreferenceConnector;
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;

public class AssetsListActivity extends AppCompatActivity {

    List<AssetsListModel> arrayAssetsList;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    TextView textJob_header,textFullName;
    DatabaseHelper db = DatabaseHelper.getInstance(this);
    AsyncHttpClient client;
    ProgressDialog pDialog;
    public String assetsUrl = Common.base_URL + "Assets";
    String asynchResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets_list);

        initView();
        clickEvent();
    }

    private void clickEvent() {
        findViewById(R.id.imv_Shutdown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteAllUserDtails();
                Intent i = new Intent(AssetsListActivity.this, LoginActivity.class);
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

    private void initView() {

        textJob_header = (TextView)findViewById(R.id.textJob_header);
        textJob_header.setText("Assets");
        textFullName = (TextView)findViewById(R.id.textFullName);
        textFullName.setText("Hi  "+ PreferenceConnector.readString(AssetsListActivity.this,"fullName",""));

        pDialog = new ProgressDialog(AssetsListActivity.this);

        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayAssetsList = new ArrayList<AssetsListModel>();
        try {
            invokeAssets();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

//    call assetsList API using AsyncHttpClient
    private void invokeAssets() throws UnsupportedEncodingException {
        client = new AsyncHttpClient();
        client.setTimeout(30000);
        client.get(this, assetsUrl, new JsonHttpResponseHandler() {
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
                    new SweetAlertDialog(AssetsListActivity.this, SweetAlertDialog.WARNING_TYPE)
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
                                AssetsListModel assetsListModel = new AssetsListModel();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                assetsListModel.setEquipId(jsonObject.getInt("equipId"));
                                assetsListModel.setEquipmentName(jsonObject.getString("equipmentName"));
                                assetsListModel.setEquipModwl(jsonObject.getString("model"));
                                assetsListModel.setEquipMrf(jsonObject.getString("mfr"));
                                assetsListModel.setEquipDescription(jsonObject.getString("description"));
                                arrayAssetsList.add(assetsListModel);
                            }
                            RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
                            if (animator instanceof SimpleItemAnimator) {
                                ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
                            }
                            //use for recycleview devider
                            Drawable dividerDrawable = ContextCompat.getDrawable(AssetsListActivity.this, R.drawable.line_divider);
                            recyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
                            adapter = new AssetsListAdapter(arrayAssetsList);
                            recyclerView.setAdapter(adapter);
                        } else {
                            pDialog.dismiss();
                            new SweetAlertDialog(AssetsListActivity.this, SweetAlertDialog.ERROR_TYPE)
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
