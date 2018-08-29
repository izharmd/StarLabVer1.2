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
import com.bws.starlab.Models.AssetsListModel;
import com.bws.starlab.R;

import java.util.List;

public class AssetsListAdapter extends RecyclerView.Adapter<AssetsListAdapter.ViewHolder> /*implements View.OnClickListener*/ {
    private List<AssetsListModel> list;
    AssetsListModel myList;

    public AssetsListAdapter(List<AssetsListModel> list) {
        this.list = list;
    }

    @Override
    public AssetsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_assets_list, parent, false);
        return new AssetsListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AssetsListAdapter.ViewHolder holder, int position) {
        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#999999"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#004f92"));
        }
        myList = list.get(position);
        holder.textAssetseequiId.setText("Equipment Id : " + String.valueOf(myList.getEquipId()));
        holder.textAssetseequipmentName.setText("Equipment Name : " + myList.getEquipmentName());
        holder.textAssetsModel.setText("Equipment Model : " + myList.getEquipModwl());
        holder.textAssetsMfr.setText("Equipment MFR : " + myList.getEquipMrf());
        holder.textAssetsDescription.setText("Description : " + myList.getEquipDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textAssetseequiId;
        public TextView textAssetseequipmentName;
        public TextView textAssetsModel;
        public TextView textAssetsMfr;
        public TextView textAssetsDescription;

        public ViewHolder(final View itemView) {
            super(itemView);
            textAssetseequiId = itemView.findViewById(R.id.textAssetseequiId);
            textAssetseequipmentName = itemView.findViewById(R.id.textAssetseequipmentName);
            textAssetsModel = itemView.findViewById(R.id.textAssetsModel);
            textAssetsMfr = itemView.findViewById(R.id.textAssetsMfr);
            textAssetsDescription = itemView.findViewById(R.id.textAssetsDescription);
            // on item click
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();

                    if (pos == pos) {
                        //itemView.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.winter));
                    }
                    AssetsListModel clickedDataItem = list.get(pos);
                    Bundle bundle = new Bundle();
                    bundle.putString("equipId", String.valueOf(clickedDataItem.getEquipId()));
                    bundle.putString("equipmentName", clickedDataItem.getEquipmentName());
                    bundle.putString("qeuipmentModel", clickedDataItem.getEquipModwl());
                    bundle.putString("qeuipmentMrf", clickedDataItem.getEquipMrf());
                    bundle.putString("qeuipmentDiscrption", clickedDataItem.getEquipDescription());

                    Intent intent = new Intent(v.getContext(), AssetsDetailsActivity.class);
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                    ((Activity) v.getContext()).finish();
                }
            });
        }
    }
}