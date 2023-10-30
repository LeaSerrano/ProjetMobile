package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//Not used
public class Inscrit_Offres_Details extends AppCompatActivity {
    private OfferInscrits offer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_inscrit_offre_click);

        // Get the offer details from the intent
        offer = (OfferInscrits) getIntent().getSerializableExtra("offer");

        // Display the offer details in your activity's layout
        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        TextView salaryTextView = findViewById(R.id.salaryTextView);
        TextView periodTextView = findViewById(R.id.periodTextView);
        TextView jobTextView = findViewById(R.id.jobTextView);
        TextView categoryTextView = findViewById(R.id.categoryTextView);

        titleTextView.setText(offer.getName());
        descriptionTextView.setText(offer.getDescription());
        salaryTextView.setText(offer.getSalary());
        periodTextView.setText(offer.getPeriod());
        jobTextView.setText(offer.getJob());
        categoryTextView.setText(offer.getCategory());

        // Add click listener to the back button
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Close the current activity and return to the previous one
            }
        });
    }
}
