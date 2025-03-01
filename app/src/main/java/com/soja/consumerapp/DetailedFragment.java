package com.soja.consumerapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailedFragment extends Fragment {

    Button buy,nego;
    TextView name, price, desc, seller;
    ImageView img;
    FirebaseFirestore db;
    FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_detailed, container, false);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
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

        nego.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putString("seller", auth.getCurrentUser().getDisplayName());
            bundle.putString("seller_email", foodItem.getSeller());
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_detailed_to_active_chat,bundle);
        });


        return root;
    }
}