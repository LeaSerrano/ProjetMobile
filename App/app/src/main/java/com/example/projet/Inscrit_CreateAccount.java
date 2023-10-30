package com.example.projet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Inscrit_CreateAccount extends Fragment {
    private Uri mFileUri = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_inscrit_create_account, container, false);

        FirebaseApp.initializeApp(getContext());

        TextInputLayout nameLayout = view.findViewById(R.id.name);
        EditText editTextName = nameLayout.getEditText();

        TextInputLayout firstnameLayout = view.findViewById(R.id.firstname);
        EditText editTextFirstname = firstnameLayout.getEditText();

        TextInputLayout nationalityLayout = view.findViewById(R.id.nationality);
        EditText editTextNationality = nationalityLayout.getEditText();

        TextInputLayout birthDateLayout = view.findViewById(R.id.birthDate);
        EditText editTextBirthDate = birthDateLayout.getEditText();

        TextInputLayout phoneNumberLayout = view.findViewById(R.id.phoneNumber);
        EditText editTextPhoneNumber = phoneNumberLayout.getEditText();

        TextInputLayout emailLayout = view.findViewById(R.id.email);
        EditText editTextEmail = emailLayout.getEditText();

        TextInputLayout cityLayout = view.findViewById(R.id.city);
        EditText editTextCity = cityLayout.getEditText();

        ActivityResultLauncher<String[]> fileChooserLauncher = registerForActivityResult(
                new ActivityResultContracts.OpenDocument(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        mFileUri = uri;
                    }
                }
        );

        Button cvButton = view.findViewById(R.id.cv);
        cvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileChooserLauncher.launch(new String[]{"*/*"});
            }
        });

        TextInputLayout commentLayout = view.findViewById(R.id.comment);
        EditText editTextComment = commentLayout.getEditText();

        TextInputLayout passwordLayout = view.findViewById(R.id.password);
        EditText editTextPassword = passwordLayout.getEditText();

        TextInputLayout cvLayout = view.findViewById(R.id.cvLayout);

        Button validate = view.findViewById(R.id.validate);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    nameLayout.setError("Obligatoire");
                    nameLayout.requestFocus();
                    return;
                } else {
                    nameLayout.setError(null);
                }

                String firstName = editTextFirstname.getText().toString();

                if (TextUtils.isEmpty(firstName)) {
                    firstnameLayout.setError("Obligatoire");
                    firstnameLayout.requestFocus();
                    return;
                } else {
                    firstnameLayout.setError(null);
                }

                String nationality = editTextNationality.getText().toString();
                String dateOfBirth = editTextBirthDate.getText().toString();
                String phone = editTextPhoneNumber.getText().toString();
                String email = editTextEmail.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    emailLayout.setError("Obligatoire");
                    emailLayout.requestFocus();
                    return;
                } else {
                    emailLayout.setError(null);
                }

                String city = editTextCity.getText().toString();
                String comment = editTextComment.getText().toString();
                String password = editTextPassword.getText().toString();

                if (TextUtils.isEmpty(password)) {
                    passwordLayout.setError("Obligatoire");
                    passwordLayout.requestFocus();
                    return;
                } else {
                    passwordLayout.setError(null);
                }

                if (mFileUri == null) {
                    cvLayout.setError("Obligatoire");
                    cvLayout.requestFocus();
                    return;
                } else {
                    cvLayout.setError(null);
                }

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseStorage mStorage = FirebaseStorage.getInstance();
                StorageReference mImagesRef = mStorage.getReference().child("CV");
                DatabaseReference mUsersRef = FirebaseDatabase.getInstance().getReference().child("users");

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            try {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    String userId = user.getUid();
                                    mUsersRef.child(userId).child("name").setValue(name);
                                    mUsersRef.child(userId).child("firstName").setValue(firstName);

                                    if (!nationality.isEmpty()) {
                                        mUsersRef.child(userId).child("nationality").setValue(nationality);
                                    }
                                    if (!dateOfBirth.isEmpty()) {
                                        mUsersRef.child(userId).child("birthDate").setValue(dateOfBirth);
                                    }
                                    if (!phone.isEmpty()) {
                                        mUsersRef.child(userId).child("phoneNumber").setValue(phone);
                                    }

                                    mUsersRef.child(userId).child("email").setValue(email);

                                    if (!city.isEmpty()) {
                                        mUsersRef.child(userId).child("city").setValue(city);
                                    }
                                    if (!comment.isEmpty()) {
                                        mUsersRef.child(userId).child("comment").setValue(comment);
                                    }

                                    mUsersRef.child(userId).child("typeOfUser").setValue("Registered");

                                    StorageReference imageRef = mImagesRef.child(userId).child("CV_" + firstName + "_" + name + ".pdf");
                                    imageRef.putFile(mFileUri);

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