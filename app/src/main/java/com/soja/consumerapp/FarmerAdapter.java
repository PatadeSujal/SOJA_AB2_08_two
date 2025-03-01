package com.soja.consumerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FarmerAdapter extends RecyclerView.Adapter<FarmerAdapter.FarmerViewHolder> {
    private List<FarmerModel> farmers;
    private OnFarmerClickListener listener;

    public interface OnFarmerClickListener {
        void onFarmerClick(FarmerModel farmer);
    }

    public FarmerAdapter(List<FarmerModel> farmers, OnFarmerClickListener listener) {
        this.farmers = farmers;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FarmerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_farmer, parent, false);
        return new FarmerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FarmerViewHolder holder, int position) {
        FarmerModel farmer = farmers.get(position);
        holder.tvFarmerName.setText(farmer.getName());
        holder.imgFarmer.setImageResource(R.drawable.ic_launcher_background); // Replace with Glide/Picasso for real images

        holder.itemView.setOnClickListener(v -> listener.onFarmerClick(farmer));
    }

    @Override
    public int getItemCount() {
        return farmers.size();
    }

    public static class FarmerViewHolder extends RecyclerView.ViewHolder {
        TextView tvFarmerName;
        ImageView imgFarmer;

        public FarmerViewHolder(View itemView) {
            super(itemView);
            tvFarmerName = itemView.findViewById(R.id.tv_farmer_name);
            imgFarmer = itemView.findViewById(R.id.img_farmer);
        }
    }
}
