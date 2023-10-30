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
import com.google.firebase.database.Query;

public class Inscrit_PourVous_Fragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private DatabaseReference databaseReference;
    private String searchQuery = "";

    public Inscrit_PourVous_Fragment() {
        // Required empty public constructor
    }

    public void updateSearchQuery(String query) {
        searchQuery = query.toLowerCase(); // Convert the query to lowercase for case-insensitive search

        Query queryRef;
        if (searchQuery.isEmpty()) {
            queryRef = null; // Set the query to null to display no results
        } else {
            String lowercaseQuery = searchQuery.substring(0, 1).toUpperCase() + searchQuery.substring(1);
            queryRef = databaseReference.orderByChild("name")
                    .startAt(lowercaseQuery)
                    .endAt(lowercaseQuery + "\uf8ff");
        }

        FirebaseRecyclerOptions<OfferInscrits> updatedOptions;
        if (queryRef != null) {
            updatedOptions = new FirebaseRecyclerOptions.Builder<OfferInscrits>()
                    .setQuery(queryRef, OfferInscrits.class)
                    .build();
        } else {
            updatedOptions = new FirebaseRecyclerOptions.Builder<OfferInscrits>()
                    .setQuery(databaseReference.orderByChild("name").equalTo("non_existing_value"), OfferInscrits.class)
                    .build();
        }

        mAdapter.updateOptions(updatedOptions);
        mAdapter.startListening();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_inscrit_pourvous, container, false);

        // Initialize RecyclerView and layout manager
        mRecyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("offers");

        // Set up FirebaseRecyclerOptions with the original query
        FirebaseRecyclerOptions<OfferInscrits> options = new FirebaseRecyclerOptions.Builder<OfferInscrits>()
                .setQuery(databaseReference.orderByChild("name").equalTo("non_existing_value"), OfferInscrits.class) // Display no results initially
                .build();

        // Initialize the adapter with FirebaseRecyclerOptions
        mAdapter = new MyAdapter(options, getContext());

        // Set the adapter on the RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        // Update the search query directly without using EditText
        updateSearchQuery(""); // Pass an empty string to initially display no results

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}
