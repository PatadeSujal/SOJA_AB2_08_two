package com.soja.consumerapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    RecyclerView farmerListView;
    FirebaseFirestore db;
    FirebaseAuth auth;
    FarmerAdapter farmerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_chat, container, false);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        farmerListView = root.findViewById(R.id.farmerListView);
        farmerListView.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchChats();

        farmerAdapter = new FarmerAdapter(MainActivity.FarmerChatList, new FarmerAdapter.OnFarmerClickListener() {
            @Override
            public void onFarmerClick(FarmerModel farmer) {
                Bundle bundle = new Bundle();
                bundle.putString("seller", farmer.getName());
                bundle.putString("seller_email", farmer.getName());
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_chat_to_active_chat,bundle);
            }
        });

        farmerListView.setAdapter(farmerAdapter);
        farmerAdapter.notifyDataSetChanged();




        return root;
    }

    private void fetchChats() {

        CollectionReference chatCollection = db.collection("Chat");
        String userEmail = auth.getCurrentUser().getEmail();

        chatCollection.get().addOnSuccessListener(querySnapshot -> {
            MainActivity.FarmerChatList.clear();

            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                String chatId = document.getId(); // Document ID (sellerEmail_userEmail or userEmail_sellerEmail)

                if (chatId.contains(userEmail)) {
                    // Extract seller email
                    String[] emails = chatId.split("x");
                    String sellerEmail = emails[0].equals(userEmail) ? emails[1] : emails[0];

                    // Add to chat list
                    MainActivity.FarmerChatList.add(new FarmerModel(sellerEmail,""));
                }
            }

            // Notify adapter of data change
            farmerAdapter.notifyDataSetChanged();
        });
    }
}