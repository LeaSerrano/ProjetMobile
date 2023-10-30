package com.example.projet;

public class Message {
    public String text;
    public String sender;

    public Message() {
        // Default constructor required for calls to DataSnapshot.getValue(Message.class)
    }

    public Message(String text, String sender) {
        this.text = text;
        this.sender = sender;
    }
}

