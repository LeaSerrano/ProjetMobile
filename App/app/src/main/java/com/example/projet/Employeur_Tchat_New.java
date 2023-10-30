package com.example.projet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Employeur_Tchat_New extends Fragment {

    private String secondUserUid;
    private boolean alreadyExists = false;

    public Employeur_Tchat_New newInstance() {
        Employeur_Tchat_New fragment = new Employeur_Tchat_New();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.f_inscrit_tchat_new, container, false);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String currentUserUid = currentUser.getUid();

        DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference().child("tchat");

        TextInputLayout emailLayout = view.findViewById(R.id.email);
        EditText editTextEmail = emailLayout.getEditText();

        ImageView create = view.findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                DatabaseReference usersRef = databaseReference.child("users");

                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                        for (DataSnapshot child : children) {

                            String typeOfUser = (String) child.child("typeOfUser").getValue();

                            if (typeOfUser.equals("EmploymentAgency") || typeOfUser.equals("Employer")) {
                                String email2 = (String) child.child("email1").getValue();

                                if (email.equals(email2)) {
                                    secondUserUid = child.getKey();
                                }
                            }
                            if (typeOfUser.equals("Registered")) {
                                String email2 = (String) child.child("email").getValue();

                                if (email.equals(email2)) {
                                    secondUserUid = child.getKey();
                                }
                            }
                        }

                        String conversation = currentUserUid + "-" + secondUserUid;

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("tchat");
                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                                for (DataSnapshot child : children) {
                                    String conversationIDS = child.getKey();
                                    String[] userIds = conversationIDS.split("-");

                                    if ((Objects.equals(userIds[0], currentUserUid) && Objects.equals(userIds[1], secondUserUid)) ||  (Objects.equals(userIds[0], secondUserUid) && Objects.equals(userIds[1], currentUser))) {
                                        alreadyExists = true;
                                    }
                                }

                                if (!alreadyExists) {
                                    messagesRef.child(conversation).setValue("");
                                    ((Employeur_Main) getActivity()).setConversationList();
                                }
                                else {
                                    Toast.makeText(getActivity(), "La conversation existe déjà", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });

        return view;
    }
}