package com.bws.starlab.FragmentsView;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bws.starlab.Commons.Common;
import com.bws.starlab.R;

/**
 * Created by BWS on 15/05/2018.
 */

public class ServiceDetailsFragment extends Fragment {
    View rootView;

    CheckBox checkBoxNeedCar, checkBoxOverNightStay;
    RadioButton rdExYes, rdExNo,rdBoth;
    LinearLayout ll_No_OverNightStay;
    TextView textWeekComm, textServiceLevel, textSpecialReqr, textScheduleAvail, textDateFrom,
            textTechnicianName, textNumOverNightStay, textdateTo, textComments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_service_details, container, false);
        initview();
        return rootView;
    }

    private void initview() {
        ll_No_OverNightStay = rootView.findViewById(R.id.ll_No_OverNightStay);
        checkBoxNeedCar = rootView.findViewById(R.id.checkBoxNeedCar);
        checkBoxOverNightStay = rootView.findViewById(R.id.checkBoxOverNightStay);

        if (Common.isNeedCar == 0) {
            //checkBoxNeedCar.setChecked(false);
            checkBoxNeedCar.setEnabled(false);
        } else {
            checkBoxNeedCar.setEnabled(false);
            checkBoxNeedCar.setChecked(true);
            //checkBoxOverNightStay.setEnabled(false);
        }

        if (Common.isOverNightStay == 0) {
            checkBoxOverNightStay.setEnabled(false);
            ll_No_OverNightStay.setVisibility(View.GONE);
        } else {
            checkBoxOverNightStay.setChecked(true);
            checkBoxNeedCar.setEnabled(false);
            ll_No_OverNightStay.setVisibility(View.VISIBLE);
        }
        rdExYes = (RadioButton) rootView.findViewById(R.id.rdExYes);
        rdExNo = (RadioButton) rootView.findViewById(R.id.rdExNo);
        rdBoth = (RadioButton) rootView.findViewById(R.id.rdBoth);

        if (Common.isExternalClean.equals("Y")) {
            rdExYes.setChecked(true);
            rdExNo.setEnabled(false);
            rdBoth.setEnabled(false);
        } else if(Common.isExternalClean.equals("N")){
            rdExYes.setEnabled(false);
            rdExNo.setChecked(true);
            rdBoth.setEnabled(false);
        }else {
            rdExYes.setEnabled(false);
            rdExNo.setEnabled(false);
            rdBoth.setChecked(true);
        }

        textWeekComm = (TextView) rootView.findViewById(R.id.textWeekComm);
        textServiceLevel = (TextView) rootView.findViewById(R.id.textServiceLevel);
        textSpecialReqr = (TextView) rootView.findViewById(R.id.textSpecialReqr);
        textScheduleAvail = (TextView) rootView.findViewById(R.id.textScheduleAvail);
        textTechnicianName = (TextView) rootView.findViewById(R.id.textTechnicianName);
        textNumOverNightStay = (TextView) rootView.findViewById(R.id.textNumOverNightStay);
        textDateFrom = (TextView) rootView.findViewById(R.id.textDateFrom);
        textdateTo = (TextView) rootView.findViewById(R.id.textdateTo);
        textComments = (TextView) rootView.findViewById(R.id.textComments);

        textWeekComm.setText(Common.weekCommencing);
        textServiceLevel.setText(Common.serviceID);
        textSpecialReqr.setText(Common.specialRequirementsClinic);
        textScheduleAvail.setText(Common.scheduleAvailable);
        textTechnicianName.setText(Common.assignTechnicianName);
        textNumOverNightStay.setText(String.valueOf(Common.numberOverNightStay));
        textDateFrom.setText(Common.assignDateForm);
        textdateTo.setText(Common.assignDateTo);
        textComments.setText(Common.commentsTech);
    }
}