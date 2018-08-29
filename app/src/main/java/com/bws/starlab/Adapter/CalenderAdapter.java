package com.bws.starlab.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bws.starlab.Models.CalenderModel;

import com.bws.starlab.R;

import java.util.List;

public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.ViewHolder> {
    private List<CalenderModel> list;

    public CalenderAdapter(List<CalenderModel> list) {
        this.list = list;
    }

    @Override
    public CalenderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_calender_list, parent, false);
        return new CalenderAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CalenderAdapter.ViewHolder holder, int position) {
        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#999999"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#004f92"));
        }
        CalenderModel myList = list.get(position);
        holder.textStartDate.setText(myList.getStartDate());
        holder.textCompanyName.setText(myList.getCompanyName());
        holder.textServiceName.setText(myList.getServiceName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textStartDate;
        public TextView textCompanyName;
        public TextView textServiceName;

        public ViewHolder(View itemView) {
            super(itemView);
            textStartDate = (TextView) itemView.findViewById(R.id.textStartDate);
            textCompanyName = (TextView) itemView.findViewById(R.id.textCompanyName);
            textServiceName = (TextView) itemView.findViewById(R.id.textServiceName);

        }
    }
}