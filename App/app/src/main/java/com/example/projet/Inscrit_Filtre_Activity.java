package com.example.projet;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Inscrit_Filtre_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscrits_filtre_layout);

        // Find the back arrow ImageButton
        ImageButton backButton = findViewById(R.id.back_button);

        // Set a click listener on the button to go back to the previous activity
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finish the current activity and go back to the previous activity
            }
        });
    }
}
