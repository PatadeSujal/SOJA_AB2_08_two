package com.soja.consumerapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActiveChatFragment extends Fragment {

    RecyclerView messageViewList;
    Button send;
    EditText inp;
    FirebaseAuth auth;
    FirebaseFirestore db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_active_chat, container, false);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        Bundle data = getArguments();

        messageViewList = root.findViewById(R.id.messageView);
        send = root.findViewById(R.id.send);
        inp = root.findViewById(R.id.ed_msg);
        List<MessageModel> messageList = new ArrayList<>();

        MessageAdapter adapter = new MessageAdapter(messageList);
        messageViewList.setLayoutManager(new LinearLayoutManager(getContext()));
        messageViewList.setAdapter(adapter);

        String chatId = auth.getCurrentUser().getEmail() + "x" + data.getString("seller_email");
        DocumentReference docRef = db.collection("Chat").document(chatId);


        docRef.get().addOnSuccessListener(documentSnapshot -> {

                if (!documentSnapshot.exists()) {
                    // **If chat document does not exist, create it with the 0th message**
                    Map<String, Object> firstMessage = new HashMap<>();
                    firstMessage.put("sender", auth.getCurrentUser().getDisplayName());
                    firstMessage.put("msg", "Hello! This is the start of our chat.");
                    firstMessage.put("Timestamp", Timestamp.now());

                    Map<String, Object> chatData = new HashMap<>();
                    chatData.put("0", firstMessage); // Insert the 0th message

                    docRef.set(chatData);
                }
            /*

                Map<String, Object> map = documentSnapshot.getData();

                if (map != null && !map.isEmpty()) {
                    // Extract numeric keys
                    List<Integer> keys = new ArrayList<>();
                    Toast.makeText(getContext(), "here!!", Toast.LENGTH_SHORT).show();
                    for (String key : map.keySet()) {
                        Map obj = (Map) map.get(key);

                        messageList.add(new MessageModel((String)obj.get("sender"),(String) obj.get("msg"),(Timestamp)obj.get("Timestamp")));

                        try {
                            keys.add(Integer.parseInt(key)); // Convert string keys to integers
                        } catch (NumberFormatException ignored) {} // Ignore non-numeric keys
                    }

                    Collections.sort(messageList, (m1, m2) -> {
                        Long t1 = m1.getTime().toDate().getTime();
                        Long t2 =m2.getTime().toDate().getTime();
                        return Long.compare(t1, t2);
                    });

                    // Find the highest key
                    int newKey = keys.isEmpty() ? 0 : Collections.max(keys) + 1;

                    // Add a new map at the next index
                    Map<String, Object> newProduct = new HashMap<>();
                    newProduct.put("sender", auth.getCurrentUser().getDisplayName());
                    newProduct.put("msg", "50");
                    newProduct.put("Timestamp", Timestamp.now());

                    // Add the new map to Firestore
                    docRef.update(String.valueOf(newKey), newProduct);
                }
                else
                {
                    Map<String, Object> newProduct = new HashMap<>();
                    newProduct.put("sender", auth.getCurrentUser().getDisplayName());
                    newProduct.put("msg", "50");
                    newProduct.put("Timestamp", Timestamp.now());
                    Toast.makeText(getContext(), auth.getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();
                    // Add the new map to Firestore
                    docRef.update(String.valueOf(0), newProduct);
                }

                */

        });

        send.setOnClickListener(v->{
            messageList.add(new MessageModel(auth.getCurrentUser().getDisplayName(), inp.getText().toString(), Timestamp.now()));
            adapter.notifyDataSetChanged();
        });

        return root;
    }
}