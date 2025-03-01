package com.soja.consumerapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    RecyclerView farmerListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_chat, container, false);

        farmerListView = root.findViewById(R.id.farmerListView);
        farmerListView.setLayoutManager(new LinearLayoutManager(getContext()));

        List farmerList = MainActivity.FarmerChatList;
        farmerList.add(new FarmerModel("Farmer A", ""));
        //farmerList.add(new FarmerModel("Farmer B", ""));


        FarmerAdapter farmerAdapter = new FarmerAdapter(farmerList, new FarmerAdapter.OnFarmerClickListener() {
            @Override
            public void onFarmerClick(FarmerModel farmer) {
                Toast.makeText(getContext(), "you clicked " + farmer.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        farmerListView.setAdapter(farmerAdapter);
        farmerAdapter.notifyDataSetChanged();




        return root;
    }
}