package com.example.projet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);

        TextView inscrivezVous = (TextView) findViewById(R.id.textViewIncrivezVous);
        SpannableString contentInscrivezVous = new SpannableString(inscrivezVous.getText());
        contentInscrivezVous.setSpan(new UnderlineSpan(), 0, contentInscrivezVous.length(), 0);
        inscrivezVous.setText(contentInscrivezVous);

        TextView accesAnonyme = (TextView) findViewById(R.id.textViewAccesAnonymous);
        SpannableString contentAccesAnonyme = new SpannableString(accesAnonyme.getText());
        contentAccesAnonyme.setSpan(new UnderlineSpan(), 0, contentAccesAnonyme.length(), 0);
        accesAnonyme.setText(contentAccesAnonyme);

        TextView inscription = findViewById(R.id.textViewIncrivezVous);
        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateAccount.class);
                startActivity(intent);
            }
        });

        TextInputLayout emailLayout = findViewById(R.id.email);
        EditText editTextEmail = emailLayout.getEditText();

        TextInputLayout passwordLayout = findViewById(R.id.password);
        EditText editTextPassword = passwordLayout.getEditText();

        Button identification = (Button) findViewById(R.id.identification);
        identification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    emailLayout.setError("Obligatoire");
                    emailLayout.requestFocus();
                    return;
                } else {
                    emailLayout.setError(null);
                }

                String password = editTextPassword.getText().toString();

                if (TextUtils.isEmpty(password)) {
                    passwordLayout.setError("Obligatoire");
                    passwordLayout.requestFocus();
                    return;
                } else {
                    passwordLayout.setError(null);
                }

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // L'utilisateur est connecté avec succès
                                        if (user.isEmailVerified()) {
                                        String userId = user.getUid();

                                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                                            DatabaseReference myRef = database.getReference("users/" + userId + "/typeOfUser");

                                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    String valeur = dataSnapshot.getValue(String.class);

                                                    if (Objects.equals(valeur, "Registered")) {
                                                        Intent intent = new Intent(MainActivity.this, Inscrit_Main.class);
                                                        startActivity(intent);
                                                    }
                                                    else if (Objects.equals(valeur, "Employer")) {
                                                        Intent intent = new Intent(MainActivity.this, Employeur_Main.class);
                                                        startActivity(intent);
                                                    }
                                                    else if (Objects.equals(valeur, "EmploymentAgency")) {
                                                        Intent intent = new Intent(MainActivity.this, AgenceI_Main.class);
                                                        startActivity(intent);
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });

                                        } else {
                                            Toast.makeText(getApplicationContext(), "Veuillez vérifier votre e-mail avant de vous connecter", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        // La connexion a échoué
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

            }
        });

        TextView resetPassword = findViewById(R.id.resetPassword);
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ResetPassword.class);
                startActivity(intent);
            }
        });

        accesAnonyme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();

                mAuth.signInAnonymously()
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    Intent intent = new Intent(MainActivity.this, BlankPage.class);
                                    startActivity(intent);

                                } else {

                                }
                            }
                        });
            }
        });

    }
}