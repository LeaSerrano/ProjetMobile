package com.example.projet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OfferDetailsActivity extends AppCompatActivity {

    private TextView mNameTextView;
    private TextView mEmployerTextView;
    private TextView mDescriptionTextView;
    private TextView mPeriodTextView;
    private TextView mSalaryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_offer_detail_box);

        // Get references to TextViews
        mNameTextView = findViewById(R.id.offer_name);
        mEmployerTextView = findViewById(R.id.employer_name);
        mDescriptionTextView = findViewById(R.id.offer_description);
        mPeriodTextView = findViewById(R.id.offer_period);
        mSalaryTextView = findViewById(R.id.offer_salary);

        // Get the Intent that started this activity and extract the offer data
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String employer = intent.getStringExtra("employer");
        String description = intent.getStringExtra("description");
        String period = intent.getStringExtra("period");
        String salary = intent.getStringExtra("salary");

        // Set the offer data to the TextViews
        mNameTextView.setText(name);
        mEmployerTextView.setText(employer);
        mDescriptionTextView.setText(description);
        mPeriodTextView.setText(period);
        mSalaryTextView.setText(salary);
    }
}
