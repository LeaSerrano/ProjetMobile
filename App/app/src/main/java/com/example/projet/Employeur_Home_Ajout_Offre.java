package com.example.projet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Employeur_Home_Ajout_Offre extends Fragment {

    String userName;
    boolean offerSent = false;

    public Employeur_Home_Ajout_Offre newInstance() {
        Employeur_Home_Ajout_Offre fragment = new Employeur_Home_Ajout_Offre();
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

        View view = inflater.inflate(R.layout.add_offer_form, container, false);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String currentUserUid = currentUser.getUid();

        DatabaseReference offersRef = FirebaseDatabase.getInstance().getReference().child("offers");

        TextInputLayout offerNameLayout = view.findViewById(R.id.offerName);
        EditText editTextOfferName = offerNameLayout.getEditText();

        TextInputLayout jobLayout = view.findViewById(R.id.job);
        EditText editTextJob = jobLayout.getEditText();

        TextInputLayout descriptionLayout = view.findViewById(R.id.description);
        EditText editTextDescription = descriptionLayout.getEditText();

        TextInputLayout periodLayout = view.findViewById(R.id.period);
        EditText editTextPeriod = periodLayout.getEditText();

        TextInputLayout salaryLayout = view.findViewById(R.id.salary);
        EditText editTextSalary = salaryLayout.getEditText();

        TextInputLayout categoryLayout = view.findViewById(R.id.category);
        EditText editTextCategory = categoryLayout.getEditText();

        Button validate = view.findViewById(R.id.validate);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                DatabaseReference usersReference = databaseReference.child("users");


                DatabaseReference typeOfCurrentUserRef = usersReference.child(currentUserUid).child("typeOfUser");

                typeOfCurrentUserRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String typeOfUser = dataSnapshot.getValue(String.class);

                        if (typeOfUser.equals("EmploymentAgency") || typeOfUser.equals("Employer")) {
                            DatabaseReference nameRef = usersReference.child(currentUserUid).child("name1");

                            nameRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    onReceived(dataSnapshot.getValue(String.class));
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                }
                            });
                        } else if (typeOfUser.equals("Registered")) {
                            DatabaseReference nameRef = usersReference.child(currentUserUid).child("name");
                            nameRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    onReceived(dataSnapshot.getValue(String.class));
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });*/

                String offerName = editTextOfferName.getText().toString();
                String job = editTextJob.getText().toString();
                String description = editTextDescription.getText().toString();
                String period = editTextPeriod.getText().toString();
                String salary = editTextSalary.getText().toString();
                String category = editTextCategory.getText().toString();

                if (TextUtils.isEmpty(offerName)) {
                    offerNameLayout.setError("Obligatoire");
                    offerNameLayout.requestFocus();
                    return;
                } else {
                    offerNameLayout.setError(null);
                }

                if (TextUtils.isEmpty(job)) {
                    jobLayout.setError("Obligatoire");
                    jobLayout.requestFocus();
                    return;
                } else {
                    jobLayout.setError(null);
                }

                if (TextUtils.isEmpty(description)) {
                    descriptionLayout.setError("Obligatoire");
                    descriptionLayout.requestFocus();
                    return;
                } else {
                    descriptionLayout.setError(null);
                }

                if (TextUtils.isEmpty(period)) {
                    periodLayout.setError("Obligatoire");
                    periodLayout.requestFocus();
                    return;
                } else {
                    periodLayout.setError(null);
                }

                if (TextUtils.isEmpty(salary)) {
                    salaryLayout.setError("Obligatoire");
                    salaryLayout.requestFocus();
                    return;
                } else {
                    salaryLayout.setError(null);
                }

                if (TextUtils.isEmpty(category)) {
                    categoryLayout.setError("Obligatoire");
                    categoryLayout.requestFocus();
                    return;
                } else {
                    categoryLayout.setError(null);
                }

                Offer offer = new Offer(offerName, job, description, period, salary, category, currentUserUid);
                offersRef.push().setValue(offer);

                ((Employeur_Main) getActivity()).setOfferList();
            }
        });

        return view;
    }

}