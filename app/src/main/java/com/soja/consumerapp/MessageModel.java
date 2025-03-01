package com.soja.consumerapp;

import com.google.firebase.Timestamp;

import java.io.Serializable;

public class MessageModel implements Serializable {
    String sender, msg;
    Timestamp time;

    MessageModel(String s, String m, Timestamp t) {
        sender = s;
        msg = m;
        time = t;
    }

    public String getSender() {
        return sender;
    }


    public String getMsg() {
        return msg;
    }

    public Timestamp getTime() {
        return time;
    }
}