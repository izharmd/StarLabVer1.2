package com.bws.starlab.Adapter;

import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bws.starlab.Models.JobDetailsModel;
import com.bws.starlab.R;

import java.util.List;

/**
 * Created by BWS on 31/08/2019.
 */

public class AssignTechnisianAdapter extends RecyclerView.Adapter<AssignTechnisianAdapter.ViewHolder> {
    private List<JobDetailsModel> list;

    public AssignTechnisianAdapter(List<JobDetailsModel> list) {
        this.list = list;
    }

    @Override
    public AssignTechnisianAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemlist_job_priority, parent, false);
        return new AssignTechnisianAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AssignTechnisianAdapter.ViewHolder holder, int position) {

        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#999999"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#004f92"));
        }

        JobDetailsModel myList = list.get(position);
        holder.textTechnicianName.setText("Company Name : " + myList.getCompanyName());
        holder.textDateFrom.setText("Date : " + myList.getDate());
        holder.textdateTo.setText("Work No : " + myList.getWorkOrderNoName());
        holder.textNumOverNightStay.setText("Service : " + myList.getService());
        holder.textComments.setText("Service : " + myList.getService());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBoxNeedCar, checkBoxOverNightStay;
        LinearLayout ll_No_OverNightStay;
        TextView  textDateFrom,textTechnicianName, textNumOverNightStay, textdateTo, textComments;

        public ViewHolder(final View itemView) {
            super(itemView);
            textTechnicianName = (TextView) itemView.findViewById(R.id.textTechnicianName);
            textNumOverNightStay = (TextView) itemView.findViewById(R.id.textNumOverNightStay);
            textDateFrom = (TextView) itemView.findViewById(R.id.textDateFrom);
            textdateTo = (TextView) itemView.findViewById(R.id.textdateTo);
            textComments = (TextView) itemView.findViewById(R.id.textComments);

        }
    }

}