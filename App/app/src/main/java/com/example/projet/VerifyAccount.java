package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class VerifyAccount extends AppCompatActivity {

    private EditText verificationCodeEditText;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_verify_account);

        Button validate = findViewById(R.id.validate);
        validate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerifyAccount.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}