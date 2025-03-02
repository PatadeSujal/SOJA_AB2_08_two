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
import java.util.Comparator;
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
        Bundle bundle = getArguments();

        messageViewList = root.findViewById(R.id.messageView);
        send = root.findViewById(R.id.send);
        inp = root.findViewById(R.id.ed_msg);
        List<MessageModel> messageList = new ArrayList<>();

        MessageAdapter adapter = new MessageAdapter(messageList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        messageViewList.setLayoutManager(layoutManager);
        messageViewList.setAdapter(adapter);

        String chatId = bundle.getString("seller_email") + "x" + auth.getCurrentUser().getEmail()  ;
        DocumentReference docRef = db.collection("Chat").document(chatId);


        docRef.addSnapshotListener((documentSnapshot, error) -> {
            if (error != null) {
                return;
            }

            // If document doesn't exist, create an empty document.
            if (documentSnapshot == null || !documentSnapshot.exists()) {
                docRef.set(new HashMap<>()).addOnSuccessListener(unused -> {});
                // Clear list in case there are old messages
                messageList.clear();
                adapter.notifyDataSetChanged();
                return;
            }

            // Document exists. Retrieve its data.
            Map<String, Object> data = documentSnapshot.getData();
            List<MessageModel> tempList = new ArrayList<>();

            if (data != null && !data.isEmpty()) {
                for (String key : data.keySet()) {
                    Object obj = data.get(key);
                    if (obj instanceof Map) {
                        Map messageMap = (Map) obj;
                        String sender = (String) messageMap.get("sender");
                        String msg = (String) messageMap.get("msg");
                        Timestamp ts = (Timestamp) messageMap.get("Timestamp");
                        tempList.add(new MessageModel(sender, msg, ts));
                    }
                }
            }

            // Sort messages by timestamp in ascending order.
            Collections.sort(tempList, (m1, m2) -> m1.getTime().compareTo(m2.getTime()));

            // Clear the current list and update it with the new sorted list.
            messageList.clear();
            messageList.addAll(tempList);
            adapter.notifyDataSetChanged();

            // Optionally, scroll the RecyclerView to the last message.
            messageViewList.scrollToPosition(messageList.size() - 1);
        });


        send.setOnClickListener(v->{
            messageList.add(new MessageModel(auth.getCurrentUser().getDisplayName(), inp.getText().toString(), Timestamp.now()));
            adapter.notifyDataSetChanged();
            sendMessage(chatId, inp.getText().toString());
        });

        return root;
    }

    public void sendMessage(String chatId, String messageText) {
        DocumentReference docRef = db.collection("Chat").document(chatId);

        docRef.get().addOnSuccessListener(documentSnapshot -> {
            int newKey = 0; // Default index for the first message

            if (documentSnapshot.exists() && documentSnapshot.getData() != null) {
                // Extract numeric keys from existing messages
                List<Integer> keys = new ArrayList<>();
                for (String key : documentSnapshot.getData().keySet()) {
                    try {
                        keys.add(Integer.parseInt(key)); // Convert string keys to integers
                    } catch (NumberFormatException ignored) {}
                }
                if (!keys.isEmpty()) {
                    newKey = Collections.max(keys) + 1; // Find next available index
                }
            }

            // Create a new message map
            Map<String, Object> newMessage = new HashMap<>();
            newMessage.put("sender", auth.getCurrentUser().getDisplayName());
            newMessage.put("msg", messageText);
            newMessage.put("Timestamp", Timestamp.now());

            // Add the message to Firestore at the next index
            docRef.update(String.valueOf(newKey), newMessage);
        });
    }
}