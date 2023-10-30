package com.example.projet;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Offer {

    public String name;
    public String job;
    public String description;
    public String period;
    public String salary;
    public String category;
    public String userId;

    public Offer() {
        // Default constructor required for calls to DataSnapshot.getValue(Message.class)
    }

    public Offer(String name, String job, String description, String period, String salary, String category, String userId) {
        this.name = name;
        this.job = job;
        this.description = description;
        this.period = period;
        this.salary = salary;
        this.category = category;
        this.userId = userId;


    }
}
