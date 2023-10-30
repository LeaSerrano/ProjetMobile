package com.example.projet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Employeur_Offres extends Fragment {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private DatabaseReference databaseRef;

    public Employeur_Offres() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        databaseRef = FirebaseDatabase.getInstance().getReference().child("offers");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_employeur_offer, container, false);

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

        FloatingActionButton addButton = view.findViewById(R.id.add_offer_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Employeur_Main) getActivity()).setCreateOffer();
            }
        });

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