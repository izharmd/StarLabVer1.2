package com.bws.starlab.FragmentsView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bws.starlab.Commons.Common;
import com.bws.starlab.R;

/**
 * Created by BWS on 25/06/2018.
 */

public class Tab_Four extends Fragment {

    View rootview;
    CheckBox checkBoxUSBmemory,checkBoxEmail;
    TextView textPrevClComment,textInvoiceReqr;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup viewGroup, Bundle savedInstanceState) {
         rootview = inflater.inflate(R.layout.tab_four, viewGroup, false);

         initView();
        return rootview;
    }

    private void initView() {
        checkBoxUSBmemory = (CheckBox)rootview.findViewById(R.id.checkBoxUSBmemory);
        checkBoxEmail = (CheckBox)rootview.findViewById(R.id.checkBoxEmail);
        textPrevClComment = (TextView)rootview.findViewById(R.id.textPrevClComment);
        textInvoiceReqr = (TextView)rootview.findViewById(R.id.textInvoiceReqr);

        if(Common.isUSBStick){
            checkBoxUSBmemory.setChecked(true);
            checkBoxUSBmemory.setEnabled(true);
        }
        if(Common.isCertificatesEmailed){
            checkBoxEmail.setChecked(true);
            checkBoxEmail.setEnabled(true);
        }
        textPrevClComment.setText(Common.previousClinicComments);
        textInvoiceReqr.setText(Common.invoicingRequirements);
    }
}