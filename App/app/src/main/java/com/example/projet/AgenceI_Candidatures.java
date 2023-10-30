package com.example.projet;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AgenceI_Candidatures extends Fragment {

    private ArrayAdapter<String> adapter;
    private List candidaturesList = new ArrayList<>();


    public AgenceI_Candidatures() {
        // Required empty public constructor
    }

    public static AgenceI_Candidatures newInstance() {
        AgenceI_Candidatures fragment = new AgenceI_Candidatures();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_agencei_candidatures, container, false);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String currentUserId = currentUser.getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("candidatures");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                candidaturesList.clear();

                for (DataSnapshot offerSnapshot : dataSnapshot.getChildren()) {
                    DatabaseReference offer = offerSnapshot.getRef();

                    offer.child("userEmail").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Vérifier si la valeur existe
                            if (dataSnapshot.exists()) {
                                // Récupérer la valeur de l'élément "userEmail"
                                String userEmail = dataSnapshot.getValue(String.class);
                                candidaturesList.add(userEmail);
                                adapter.notifyDataSetChanged();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("TAG", "Failed to read value.", databaseError.toException());
            }
        });


        ListView listView = view.findViewById(R.id.candidatureList);
        listView.setSelector(android.R.color.transparent);

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, candidaturesList);
        listView.setAdapter(adapter);


        return view;
    }
}