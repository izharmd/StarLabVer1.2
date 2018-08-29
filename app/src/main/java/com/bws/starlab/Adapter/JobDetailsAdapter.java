package com.bws.starlab.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bws.starlab.Commons.Common;
import com.bws.starlab.JobDetailsActivity;
import com.bws.starlab.Models.JobDetailsModel;
import com.bws.starlab.R;

import java.util.List;

public class JobDetailsAdapter extends RecyclerView.Adapter<JobDetailsAdapter.ViewHolder> {
    private List<JobDetailsModel> list;

    public JobDetailsAdapter(List<JobDetailsModel> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemlist_job_priority, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(JobDetailsAdapter.ViewHolder holder, int position) {

        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#999999"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#004f92"));
        }

        JobDetailsModel myList = list.get(position);
        holder.textCustomerName.setText("Company Name : " + myList.getCompanyName());
        holder.textDateTime.setText("Date : " + myList.getDate());
        holder.textWorkNo.setText("Work No : " + myList.getWorkOrderNo());
        holder.textService.setText("Service : " + myList.getService());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textCustomerName;
        public TextView textDateTime;
        public TextView textWorkNo;
        public TextView textService;

        public ViewHolder(final View itemView) {
            super(itemView);
            textCustomerName = itemView.findViewById(R.id.textCustomerName);
            textDateTime = itemView.findViewById(R.id.textDateTime);
            textWorkNo = itemView.findViewById(R.id.textAssetsDescription);
            textService = itemView.findViewById(R.id.textService);
            // on item click
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    if (pos == pos) {
                        //itemView.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.winter));
                    }
                    JobDetailsModel clickedDataItem = list.get(pos);
                    Common.workOrderNo = clickedDataItem.getWorkOrderNo();
                    Common.sourceLat = clickedDataItem.getSourceLat();
                    Common.sourceLong = clickedDataItem.getSourceLong();
                    Common.destLat = clickedDataItem.getDestLat();
                    Common.destLong = clickedDataItem.getDestLong();
                    Log.d("work no===",Common.workOrderNo);
                    Intent intent = new Intent(v.getContext(), JobDetailsActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

}