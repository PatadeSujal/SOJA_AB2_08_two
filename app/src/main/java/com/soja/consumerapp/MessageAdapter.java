package com.soja.consumerapp;

import android.graphics.drawable.Drawable;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    List<MessageModel> messageList;
    FirebaseAuth auth;

    public MessageAdapter(List<MessageModel> ml)
    {
        messageList = ml;
        auth = FirebaseAuth.getInstance();
    }


    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        MessageModel m = messageList.get(position);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.text.getLayoutParams();

        if (m.getSender().equals(auth.getCurrentUser().getEmail())) {
            params.gravity = Gravity.END; // Align to right for the sender
            holder.text.setBackgroundResource(R.drawable.btn_background);
        } else {
            params.gravity = Gravity.START; // Align to left for the receiver
            holder.text.setBackgroundResource(R.drawable.input_background);
        }

        holder.text.setLayoutParams(params);
        holder.text.setText(m.getMsg());
    }


    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder
    {
        TextView text;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.msg);
        }
    }
}
