package com.soja.consumerapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailedFragment extends Fragment {

    Button buy,nego;
    TextView name, price, desc, seller;
    ImageView img;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_detailed, container, false);

        Bundle data = getArguments();

        FoodItemModel foodItem = (FoodItemModel) data.getSerializable("foodItem");

        buy = root.findViewById(R.id.buy);
        nego = root.findViewById(R.id.negotiate);
        name = root.findViewById(R.id.name);
        price = root.findViewById(R.id.price);
        desc = root.findViewById(R.id.desc);
        seller = root.findViewById(R.id.farmer);
        img = root.findViewById(R.id.image);

        name.setText(foodItem.getName());
        price.setText(foodItem.getPrice());
        desc.setText(foodItem.getDescription());
        seller.setText(foodItem.getSeller());

        Glide.with(getContext())
                .load(foodItem.getImage())
                .into(img);


        return root;
    }
}