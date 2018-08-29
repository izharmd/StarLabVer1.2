package com.bws.starlab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bws.starlab.Utils.DatabaseHelper;
import com.bws.starlab.Utils.PreferenceConnector;

public class AssetsDetailsActivity extends AppCompatActivity {

    TextView textAssetseequipmentId, textAssetseequipmentName, textAssetsModel,
            textAssetsMfr, textAssetsDescription,textFullName;
    Button btnSubmit;
    DatabaseHelper db = DatabaseHelper.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets_details);

        initView();
        clickEvent();
    }

    private void clickEvent() {

        findViewById(R.id.imv_Shutdown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteAllUserDtails();
                Intent i = new Intent(AssetsDetailsActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        findViewById(R.id.textBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssetsDetailsActivity.this,AssetsListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initView() {
        Bundle bundle = this.getIntent().getExtras();
        textAssetseequipmentId = (TextView) findViewById(R.id.textAssetseequipmentId);
        textAssetseequipmentName = (TextView) findViewById(R.id.textAssetseequipmentName);
        textAssetsModel = (TextView) findViewById(R.id.textAssetsModel);
        textAssetsMfr = (TextView) findViewById(R.id.textAssetsMfr);
        textAssetsDescription = (TextView) findViewById(R.id.textAssetsDescription);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        textAssetseequipmentId.setText(bundle.getString("equipId"));
        textAssetseequipmentName.setText(bundle.getString("equipmentName"));
        textAssetsModel.setText(bundle.getString("qeuipmentModel"));
        textAssetsMfr.setText(bundle.getString("qeuipmentMrf"));
        textAssetsDescription.setText(bundle.getString("qeuipmentDiscrption"));

        TextView textJob_header = (TextView) findViewById(R.id.textJob_header);
        textJob_header.setText("Assets Details");
        textFullName = (TextView)findViewById(R.id.textFullName);
        textFullName.setText("Hi  "+ PreferenceConnector.readString(AssetsDetailsActivity.this,"fullName",""));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
