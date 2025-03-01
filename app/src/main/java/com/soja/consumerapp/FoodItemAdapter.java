package com.soja.consumerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.FoodItemViewHolder> {

    private List<FoodItemModel> foodItemList;

    public FoodItemAdapter(List<FoodItemModel> ls)
    {
        foodItemList = ls;
    }

    @NonNull
    @Override
    public FoodItemAdapter.FoodItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fooditem, parent, false);


        return new FoodItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemAdapter.FoodItemViewHolder holder, int position) {
        holder.tv_name.setText(foodItemList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return foodItemList.size();
    }

    public static class FoodItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_name;
        ImageView img;
        public FoodItemViewHolder(View itemView)
        {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_FoodItemName);
        }

    }

}
