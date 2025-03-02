package com.soja.consumerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.FoodItemViewHolder> {

    private List<FoodItemModel> foodItemList;
    private FoodItemAdapter.OnFoodItemClickListener listener;
    private Context context;

    public interface OnFoodItemClickListener {
        void onFoodItemClick(FoodItemModel foodItem);
    }
    public FoodItemAdapter(List<FoodItemModel> ls, Context c, FoodItemAdapter.OnFoodItemClickListener lis)
    {
        foodItemList = ls;
        listener = lis;
        context = c;
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

        Glide.with(context)
                .load(foodItemList.get(position).getImage())
                .into(holder.img);

        holder.itemView.setOnClickListener(v -> listener.onFoodItemClick(foodItemList.get(position)));
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
            img = itemView.findViewById(R.id.img_foodItem);
        }

    }

}
