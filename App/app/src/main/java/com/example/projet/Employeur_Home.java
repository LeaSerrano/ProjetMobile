package com.example.projet;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Employeur_Home extends AppCompatActivity {

    private TextView TabOffres, TabCandidatures;

    //Value for this is between 1-2
    private int selectedTabNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f_employer_home);

        TabOffres = findViewById(R.id.OffresTab);
        TabCandidatures = findViewById(R.id.CandidaturesTab);


        // We will have our first fragment by default
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.FragmentContainer, Employeur_Offres.class, null)
                .commit();


        TabOffres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectTab(1);

            }
        });

        TabCandidatures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectTab(2);

            }
        });

    }

    private void selectTab(int TabNumber){

        TextView selectedTextView;

        TextView nonSelectedTextView;

        if(TabNumber == 1){
            selectedTextView = TabOffres;
            nonSelectedTextView = TabCandidatures;

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.FragmentContainer, Employeur_Offres.class, null)
                    .commit();

        }

        else {

            selectedTextView = TabCandidatures;
            nonSelectedTextView = TabOffres;

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.FragmentContainer, Employeur_Candidatures.class, null)
                    .commit();

        }

        float slideTo = (TabNumber - selectedTabNumber) * selectedTextView.getWidth();

        TranslateAnimation translateAnimation = new TranslateAnimation(0, slideTo,0,0);

        translateAnimation.setDuration(100);

        if(selectedTabNumber == 1){
            TabOffres.startAnimation(translateAnimation);
        }
        else {
            TabCandidatures.startAnimation(translateAnimation);
        }


        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                selectedTextView.setBackgroundResource(R.drawable.round_back_white_100);
                selectedTextView.setTypeface(null, Typeface.BOLD);
                selectedTextView.setTextColor(Color.BLACK);


                // For the Non Selected Tab
                nonSelectedTextView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                nonSelectedTextView.setTextColor(Color.parseColor("#80FFFFFF"));
                nonSelectedTextView.setTypeface(null, Typeface.NORMAL);



            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

}