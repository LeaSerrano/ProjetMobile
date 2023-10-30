package com.example.projet;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Employeur_CreateAccount extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_employer_create_account, container, false);

        FirebaseApp.initializeApp(getContext());

        TextInputLayout companyNameLayout = view.findViewById(R.id.companyName);
        EditText editTextCompanyName = companyNameLayout.getEditText();

        TextInputLayout serviceOrDepartmentNameLayout = view.findViewById(R.id.serviceOrDepartmentName);
        EditText editTextServiceOrDepartmentName = serviceOrDepartmentNameLayout.getEditText();

        TextInputLayout subServiceOrSubDepartmentNameLayout = view.findViewById(R.id.subServiceOrSubDepartmentName);
        EditText editTextSubServiceOrSubDepartmentName = subServiceOrSubDepartmentNameLayout.getEditText();

        TextInputLayout nationalNumberLayout = view.findViewById(R.id.nationalNumber);
        EditText editTextNationalNumber = nationalNumberLayout.getEditText();

        TextInputLayout name1Layout = view.findViewById(R.id.name1);
        EditText editTextName1 = name1Layout.getEditText();

        TextInputLayout name2Layout = view.findViewById(R.id.name2);
        EditText editTextName2 = name2Layout.getEditText();

        TextInputLayout email1Layout = view.findViewById(R.id.email1);
        EditText editTextEmail1 = email1Layout.getEditText();

        TextInputLayout email2Layout = view.findViewById(R.id.email2);
        EditText editTextEmail2 = email2Layout.getEditText();

        TextInputLayout phoneNumber1Layout = view.findViewById(R.id.phoneNumber1);
        EditText editTextPhoneNumber1 = phoneNumber1Layout.getEditText();

        TextInputLayout phoneNumber2Layout = view.findViewById(R.id.phoneNumber2);
        EditText editTextPhoneNumber2 = phoneNumber2Layout.getEditText();

        TextInputLayout adressLayout = view.findViewById(R.id.adress);
        EditText editTextAdress = adressLayout.getEditText();

        TextInputLayout websiteLayout = view.findViewById(R.id.website);
        EditText editWebsite = websiteLayout.getEditText();

        TextInputLayout linkedinLayout = view.findViewById(R.id.linkedin);
        EditText editLinkedin = linkedinLayout.getEditText();

        TextInputLayout facebookLayout = view.findViewById(R.id.facebook);
        EditText editFacebook = facebookLayout.getEditText();

        TextInputLayout passwordLayout = view.findViewById(R.id.password);
        EditText editPassword = passwordLayout.getEditText();

        Button validate = view.findViewById(R.id.validate);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String companyName = editTextCompanyName.getText().toString();

                if (TextUtils.isEmpty(companyName)) {
                    companyNameLayout.setError("Obligatoire");
                    companyNameLayout.requestFocus();
                    return;
                } else {
                    companyNameLayout.setError(null);
                }

                String serviceOrDepartmentName = editTextServiceOrDepartmentName.getText().toString();

                String subServiceOrSubDepartmentName = editTextSubServiceOrSubDepartmentName.getText().toString();

                String nationalNumber = editTextNationalNumber.getText().toString();

                if (TextUtils.isEmpty(nationalNumber)) {
                    nationalNumberLayout.setError("Obligatoire");
                    nationalNumberLayout.requestFocus();
                    return;
                } else {
                    nationalNumberLayout.setError(null);
                }

                String name1 = editTextName1.getText().toString();

                if (TextUtils.isEmpty(name1)) {
                    name1Layout.setError("Obligatoire");
                    name1Layout.requestFocus();
                    return;
                } else {
                    name1Layout.setError(null);
                }

                String name2 = editTextName2.getText().toString();

                String email1 = editTextEmail1.getText().toString();

                if (TextUtils.isEmpty(email1)) {
                    email1Layout.setError("Obligatoire");
                    email1Layout.requestFocus();
                    return;
                } else {
                    email1Layout.setError(null);
                }

                String email2 = editTextEmail2.getText().toString();

                String phoneNumber1 = editTextPhoneNumber1.getText().toString();

                String phoneNumber2 = editTextPhoneNumber2.getText().toString();

                String adress = editTextAdress.getText().toString();

                if (TextUtils.isEmpty(adress)) {
                    adressLayout.setError("Obligatoire");
                    adressLayout.requestFocus();
                    return;
                } else {
                    adressLayout.setError(null);
                }

                String website = editWebsite.getText().toString();

                String linkedin = editLinkedin.getText().toString();

                String facebook = editFacebook.getText().toString();

                String password = editPassword.getText().toString();

                if (TextUtils.isEmpty(password)) {
                    passwordLayout.setError("Obligatoire");
                    passwordLayout.requestFocus();
                    return;
                } else {
                    passwordLayout.setError(null);
                }

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                DatabaseReference mUsersRef = FirebaseDatabase.getInstance().getReference().child("users");

                mAuth.createUserWithEmailAndPassword(email1, password)
                        .addOnCompleteListener(task -> {
                            try {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    String userId = user.getUid();
                                    mUsersRef.child(userId).child("companyName").setValue(companyName);

                                    if (!serviceOrDepartmentName.isEmpty()) {
                                        mUsersRef.child(userId).child("serviceOrDepartmentName").setValue(serviceOrDepartmentName);
                                    }
                                    if (!subServiceOrSubDepartmentName.isEmpty()) {
                                        mUsersRef.child(userId).child("subServiceOrSubDepartmentName").setValue(subServiceOrSubDepartmentName);
                                    }

                                    mUsersRef.child(userId).child("nationalNumber").setValue(nationalNumber);

                                    mUsersRef.child(userId).child("name1").setValue(name1);

                                    if (!name2.isEmpty()) {
                                        mUsersRef.child(userId).child("name2").setValue(name2);
                                    }

                                    mUsersRef.child(userId).child("email1").setValue(email1);

                                    if (!email2.isEmpty()) {
                                        mUsersRef.child(userId).child("email2").setValue(email2);
                                    }
                                    if (!phoneNumber1.isEmpty()) {
                                        mUsersRef.child(userId).child("phoneNumber1").setValue(phoneNumber1);
                                    }
                                    if (!phoneNumber2.isEmpty()) {
                                        mUsersRef.child(userId).child("phoneNumber2").setValue(phoneNumber2);
                                    }

                                    mUsersRef.child(userId).child("adress").setValue(adress);

                                    if (!website.isEmpty()) {
                                        mUsersRef.child(userId).child("website").setValue(website);
                                    }
                                    if (!linkedin.isEmpty()) {
                                        mUsersRef.child(userId).child("linkedin").setValue(linkedin);
                                    }
                                    if (!facebook.isEmpty()) {
                                        mUsersRef.child(userId).child("facebook").setValue(facebook);
                                    }

                                    mUsersRef.child(userId).child("typeOfUser").setValue("Employer");

                                    user.sendEmailVerification();

                                    Intent intent = new Intent(getActivity(), VerifyAccount.class);
                                    startActivity(intent);
                                } else {
                                    throw task.getException();
                                }
                            } catch (FirebaseAuthUserCollisionException e) {
                                Toast.makeText(getActivity(), "L'adresse e-mail est déjà utilisée par un autre compte.", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(getActivity(), "Une erreur est survenue lors de la création du compte.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        return view;
    }
}