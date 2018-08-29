package com.bws.starlab.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bws.starlab.AssetsDetailsActivity;
import com.bws.starlab.Models.NotificationModel;
import com.bws.starlab.NotificationDetailsActivity;
import com.bws.starlab.R;

import java.util.List;

/**
 * Created by BWS on 24/05/2018.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> /*implements View.OnClickListener*/ {
    private List<NotificationModel> list;
    NotificationModel myList;

    public NotificationAdapter(List<NotificationModel> list) {
        this.list = list;
    }

    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification_list, parent, false);
        return new NotificationAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NotificationAdapter.ViewHolder holder, int position) {
        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#999999"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#004f92"));
        }
        myList = list.get(position);
        holder.textNotificationBody.setText(myList.getNotificationBody());
        holder.textNotificationDate.setText(myList.getGetNotificationDate());
        holder.textNotificationTime.setText(myList.getGetNotificationTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textNotificationBody;
        public TextView textNotificationDate;
        public TextView textNotificationTime;

        public ViewHolder(final View itemView) {
            super(itemView);
            textNotificationBody = itemView.findViewById(R.id.textNotificationBody);
            textNotificationDate = itemView.findViewById(R.id.textNotificationDate);
            textNotificationTime = itemView.findViewById(R.id.textNotificationTime);
            // on item click
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    if (pos == pos) {
                        // itemView.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.winter));
                    }

                    NotificationModel clickedDataItem = list.get(pos);

                    Bundle bundle = new Bundle();
                    bundle.putString("notificationBody", clickedDataItem.getNotificationBody());
                    Intent intent = new Intent(v.getContext(), NotificationDetailsActivity.class);
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                    ((Activity) v.getContext()).finish();

                }
            });
        }
    }
}