package com.example.projet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Inscrit_Recommande_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private DatabaseReference databaseRef;

    public Inscrit_Recommande_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get a reference to your Firebase Realtime Database
        databaseRef = FirebaseDatabase.getInstance().getReference().child("offers");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_inscrit_recommande, container, false);

        // Set up the RecyclerView with a LinearLayoutManager
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set up FirebaseRecyclerOptions
        FirebaseRecyclerOptions<OfferInscrits> options =
                new FirebaseRecyclerOptions.Builder<OfferInscrits>()
                        .setQuery(databaseRef, OfferInscrits.class)
                        .build();

        // Set up the adapter
        adapter = new MyAdapter(options, getContext());
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
