package com.bws.starlab.FragmentsView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bws.starlab.Commons.Common;
import com.bws.starlab.R;
/**
 * Created by BWS on 25/06/2018.
 */

public class TabOne extends Fragment {

    View rootview;
    TextView textWorkOrderNo, textAccountManager, textMyContact, textEmail, textLabContact,
            textLabEmail, textPurchasingContact, textPurchasingEmail, textCompany, textDepartment,
            textAddress, textPoNo, textDate, textKeyTelephone, textkeyMobile,
            textLabTelephone, textLabMobile,textAccountNo, textPostCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.tab_one, viewGroup, false);

        initView();

        return rootview;
    }


    private void initView() {

        textWorkOrderNo = (TextView) rootview.findViewById(R.id.textWorkOrderNo);
        textAccountManager = (TextView) rootview.findViewById(R.id.textAccountManager);
        textMyContact = (TextView) rootview.findViewById(R.id.textMyContact);
        textEmail = (TextView) rootview.findViewById(R.id.textKeyEmail);
        textLabContact = (TextView) rootview.findViewById(R.id.textLabContact);
        textLabEmail = (TextView) rootview.findViewById(R.id.textLabEmail);
        textPurchasingContact = (TextView) rootview.findViewById(R.id.textPurchasingContact);
        textPurchasingEmail = (TextView) rootview.findViewById(R.id.textPurchasingEmail);
        textCompany = (TextView) rootview.findViewById(R.id.textCompany);
        textDepartment = (TextView) rootview.findViewById(R.id.textDepartment);
        textAddress = (TextView) rootview.findViewById(R.id.textAddress);
        textPoNo = (TextView) rootview.findViewById(R.id.textPoNo);
        textDate = (TextView) rootview.findViewById(R.id.textDate);
        textKeyTelephone = (TextView) rootview.findViewById(R.id.textKeyTelephone);
        textkeyMobile = (TextView) rootview.findViewById(R.id.textkeyMobile);
        textLabMobile = (TextView) rootview.findViewById(R.id.textLabMobile);
        textLabTelephone = (TextView) rootview.findViewById(R.id.textLabTelephone);
        textAccountNo = (TextView) rootview.findViewById(R.id.textAccountNo);
        textPostCode = (TextView) rootview.findViewById(R.id.textPostCode);

        textWorkOrderNo.setText(Common.workOrderNo);
        textAccountManager.setText(Common.accountManager);
        textMyContact.setText(Common.keyContact);
        textEmail.setText(Common.keyEmail);
        textKeyTelephone.setText(Common.keyTelephone);
        textkeyMobile.setText(Common.keyMobile);
        textLabContact.setText(Common.labContact);
        textLabEmail.setText(Common.labEmail);
        textLabTelephone.setText(Common.labTelephone);
        textLabMobile.setText(Common.labMobile);
        textPurchasingContact.setText(Common.purchasingContact);
        textPurchasingEmail.setText(Common.purchasingEmail);
        textCompany.setText(Common.companyID);
        textDepartment.setText(Common.department);
        textAddress.setText(Common.address);
        textPoNo.setText(Common.pOno);
        textDate.setText(Common.cusDate);
        textAccountNo.setText(Common.cusAccountNo);
        textPostCode.setText(Common.cusPostcode);

    }
}