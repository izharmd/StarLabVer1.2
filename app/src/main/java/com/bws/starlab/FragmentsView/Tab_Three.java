package com.bws.starlab.FragmentsView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bws.starlab.Commons.Common;
import com.bws.starlab.R;

public class Tab_Three extends Fragment  {
    View rootView;


    TextView textLocationClinic,textSpecialCondition,textParkingAvalDetails,textPayDiaplay,
            textOnSideParking,textTechniciansGain;
    CheckBox checkBoxsturdy,checkBoxParkingAvalOnSide,checkBoxOutSideBuild,checkBoxPayDisp,
            checkBoxOnSideParking;

    RadioButton rdTechArondSideYes,rdrdTechArondSideNo,rdPassProvdedYes,rdPassProvdedNo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_location, container, false);


       initview();

        return rootView;


    }

    private void initview() {
        textLocationClinic = (TextView)rootView.findViewById(R.id.textLocationClinic);
        textSpecialCondition = (TextView)rootView.findViewById(R.id.textSpecialCondition);
        textParkingAvalDetails = (TextView)rootView.findViewById(R.id.textParkingAvalDetails);
        textPayDiaplay = (TextView)rootView.findViewById(R.id.textPayDiaplay);
        textOnSideParking = (TextView)rootView.findViewById(R.id.textOnSideParking);
        textTechniciansGain = (TextView)rootView.findViewById(R.id.textTechniciansGain);



        textLocationClinic.setText(Common.clinicLocation);
        textSpecialCondition.setText(Common.specialCondition);
        textParkingAvalDetails.setText(Common.parkingAvailableDetails);
        textPayDiaplay.setText(Common.parkingCost);
        textOnSideParking.setText(Common.nearestCarParkDetails);
        textTechniciansGain.setText(Common.techniciansGainAccess);


        checkBoxsturdy = (CheckBox)rootView.findViewById(R.id.checkBoxsturdy);

        if(Common.isSturdyTable == true){
            checkBoxsturdy.setEnabled(true);
            checkBoxsturdy.setChecked(true);
        }else {
            checkBoxsturdy.setEnabled(false);
            checkBoxsturdy.setChecked(false);
        }

        checkBoxParkingAvalOnSide = (CheckBox)rootView.findViewById(R.id.checkBoxParkingAvalOnSide);
        if(Common.isOnSiteParkingAvailable == true){
            checkBoxParkingAvalOnSide.setEnabled(true);
            checkBoxParkingAvalOnSide.setChecked(true);
        }else {
            checkBoxParkingAvalOnSide.setEnabled(false);
            checkBoxParkingAvalOnSide.setChecked(false);
        }


        checkBoxOutSideBuild = (CheckBox)rootView.findViewById(R.id.checkBoxOutSideBuild);
        if(Common.isOutsideBuilding == true){
            checkBoxOutSideBuild.setEnabled(true);
            checkBoxOutSideBuild.setChecked(true);
        }else {
            checkBoxOutSideBuild.setEnabled(false);
            checkBoxOutSideBuild.setChecked(false);
        }

        checkBoxPayDisp = (CheckBox)rootView.findViewById(R.id.checkBoxPayDisp);

        if(Common.isPayAndDisplay == true){
            checkBoxPayDisp.setEnabled(true);
            checkBoxPayDisp.setChecked(true);
        }else {
            checkBoxPayDisp.setEnabled(false);
            checkBoxPayDisp.setChecked(false);
        }

        checkBoxOnSideParking = (CheckBox)rootView.findViewById(R.id.checkBoxOnSideParking);
        if(Common.isOnSiitePreBooked == true){
            checkBoxOnSideParking.setEnabled(true);
            checkBoxOnSideParking.setChecked(true);
        }else {
            checkBoxOnSideParking.setEnabled(false);
            checkBoxOnSideParking.setChecked(false);
        }

        rdTechArondSideYes = (RadioButton)rootView.findViewById(R.id.rdTechArondSideYes);
        rdrdTechArondSideNo = (RadioButton)rootView.findViewById(R.id.rdrdTechArondSideNo);
        rdPassProvdedYes = (RadioButton)rootView.findViewById(R.id.rdPassProvdedYes);
        rdPassProvdedNo = (RadioButton)rootView.findViewById(R.id.rdPassProvdedNo);

        if(Common.isTechniciansAroundSite.equals("Y")){
            rdTechArondSideYes.setChecked(true);
            rdrdTechArondSideNo.setEnabled(false);
        } else {
            rdTechArondSideYes.setEnabled(false);
            rdrdTechArondSideNo.setChecked(true);
        }



        if(Common.isPassProvided.equals("Y")){
            rdPassProvdedYes.setChecked(true);
            rdPassProvdedNo.setEnabled(false);
        } else {
            rdPassProvdedYes.setEnabled(false);
            rdPassProvdedNo.setChecked(true);
        }




    }
}