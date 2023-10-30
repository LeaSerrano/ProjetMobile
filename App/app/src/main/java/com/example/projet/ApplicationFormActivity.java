package com.example.projet;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ApplicationFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recevoir_notifs_offres);

        // Get a reference to the back button
        Button backButton = findViewById(R.id.button_back);

        // Set a click listener on the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the ProfileFragment
                onBackPressed();
            }
        });
    }
}
