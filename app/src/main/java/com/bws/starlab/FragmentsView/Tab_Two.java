package com.bws.starlab.FragmentsView;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bws.starlab.Commons.Common;
import com.bws.starlab.R;

/**
 * Created by BWS on 25/06/2018.
 */

public class Tab_Two extends Fragment {
    View rootview;
    TextView textTipOne,textMejority,textSingl30,textChannal8,textChannal12,textRepater,textBottalTop,
            textOther,textCalibrationDueDate;
    CheckBox checkBoxOneYr,checkBoxSixMonth,checkBoxOther;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup viewGroup, Bundle savedInstanceState) {
         rootview = inflater.inflate(R.layout.tab_two, viewGroup, false);

        initView();

        return rootview;
    }

    private void initView() {

        textTipOne = (TextView)rootview.findViewById(R.id.textTipOne);
        textMejority = (TextView)rootview.findViewById(R.id.textMejority);
        textSingl30 = (TextView)rootview.findViewById(R.id.textSingl30);
        textChannal8 = (TextView)rootview.findViewById(R.id.textChannal8);
        textChannal12 = (TextView)rootview.findViewById(R.id.textChannal12);
        textRepater = (TextView)rootview.findViewById(R.id.textRepater);
        textBottalTop = (TextView)rootview.findViewById(R.id.textBottalTop);
        textOther = (TextView)rootview.findViewById(R.id.textOther);
        textCalibrationDueDate = (TextView)rootview.findViewById(R.id.textCalibrationDueDate);

        checkBoxOneYr = (CheckBox)rootview.findViewById(R.id.checkBoxOneYr);
        checkBoxSixMonth = (CheckBox)rootview.findViewById(R.id.checkBoxSixMonth);
        checkBoxOther = (CheckBox)rootview.findViewById(R.id.checkBoxOther);

        if(Common.isOneYear){
            checkBoxOneYr.setEnabled(false);
            checkBoxOneYr.setChecked(true);
        }

        if(Common.isSixMonth){
            checkBoxSixMonth.setEnabled(false);
            checkBoxSixMonth.setChecked(true);
        }

        if(Common.isOther){
            checkBoxOther.setEnabled(false);
            checkBoxOther.setChecked(true);
        }
        if(checkBoxOther.isChecked()){
            textCalibrationDueDate.setVisibility(View.VISIBLE);
        }

        textTipOne.setText(Common.tipOneTips);
        textMejority.setText(Common.majorityPipettesServiced);
        textSingl30.setText(Common.single30);
        textChannal8.setText(Common.channel8);
        textChannal12.setText(Common.channel12);
        textRepater.setText(Common.repeaters);
        textBottalTop.setText(Common.bottleTops);
        textOther.setText(Common.other);
        textCalibrationDueDate.setText(Common.calibrationDueOtherDate);
    }
}