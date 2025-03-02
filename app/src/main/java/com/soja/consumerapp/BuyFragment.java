package com.soja.consumerapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class BuyFragment extends Fragment {

    private EditText stateInput, cityInput, addressInput, pincodeInput, phoneInput;
    private Button proceedBtn;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private String userEmail;

    public BuyFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buy, container, false);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Get references to UI elements
        stateInput = view.findViewById(R.id.state);
        cityInput = view.findViewById(R.id.city);
        addressInput = view.findViewById(R.id.address);
        pincodeInput = view.findViewById(R.id.pincode);
        phoneInput = view.findViewById(R.id.phone);
        proceedBtn = view.findViewById(R.id.proceedBtn);

        // Get current user
        FirebaseUser user = auth.getCurrentUser();
        if (user != null && user.getEmail() != null) {
            userEmail = user.getEmail();
            loadUserData(); // Fetch existing data if present
        } else {
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
        }

        // Handle button click
        proceedBtn.setOnClickListener(v -> saveSellerDetails());

        return view;
    }

    private void loadUserData() {
        DocumentReference docRef = db.collection("SellerIdentity").document(userEmail);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                // Fill EditTexts with stored data
                stateInput.setText(documentSnapshot.getString("state"));
                cityInput.setText(documentSnapshot.getString("city"));
                addressInput.setText(documentSnapshot.getString("address"));
                pincodeInput.setText(documentSnapshot.getString("pincode"));
                phoneInput.setText(documentSnapshot.getString("phone"));
            } else {
                // Document doesn't exist, user can enter new details
                Toast.makeText(getContext(), "No existing data found. Enter new details.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e ->
                Toast.makeText(getContext(), "Error loading data: " + e.getMessage(), Toast.LENGTH_SHORT).show()
        );
    }

    private void saveSellerDetails() {
        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(getContext(), "Error fetching email", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get input values
        String state = stateInput.getText().toString().trim();
        String city = cityInput.getText().toString().trim();
        String address = addressInput.getText().toString().trim();
        String pincode = pincodeInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(state) || TextUtils.isEmpty(city) || TextUtils.isEmpty(address) ||
                TextUtils.isEmpty(pincode) || TextUtils.isEmpty(phone)) {
            Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare data for Firestore
        Map<String, Object> sellerData = new HashMap<>();
        sellerData.put("state", state);
        sellerData.put("city", city);
        sellerData.put("address", address);
        sellerData.put("pincode", pincode);
        sellerData.put("phone", phone);

        // Store or update data in Firestore
        db.collection("BuyerIdentity")
                .document(userEmail)
                .set(sellerData)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(getContext(), "Details saved successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
