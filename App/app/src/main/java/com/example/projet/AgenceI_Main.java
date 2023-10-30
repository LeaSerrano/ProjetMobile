package com.example.projet;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AgenceI_Main extends AppCompatActivity {

    private int selectedTab = 1;
    private LinearLayout bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f_agencei_main);

        bottomBar = findViewById(R.id.bottomBar);

        final LinearLayout homeLayout = findViewById(R.id.HomeLayout);
        final LinearLayout NotifLayout = findViewById(R.id.NotificationsLayout);
        final LinearLayout ProfileLayout = findViewById(R.id.ProfileLayout);

        final ImageView homeImg = findViewById(R.id.HomeImage);
        final ImageView NotifImg = findViewById(R.id.NotifImage);
        final ImageView ProfileImg = findViewById(R.id.ProfileImage);


        final TextView homeTxt = findViewById(R.id.HomeText);
        final TextView NotifTxt = findViewById(R.id.NotifText);
        final TextView ProfileTxt = findViewById(R.id.ProfileText);




        //Set Home Fragment by default
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, AgenceI_Home_Fragment.class, null)
                .commit();


        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedTab != 1){

                    //Set Home Fragment by default
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, AgenceI_Home_Fragment.class,null)
                            .commit();

                    //UnSelect all the other tabs except for home
                    NotifTxt.setVisibility(View.GONE);
                    ProfileTxt.setVisibility(View.GONE);


                    NotifImg.setImageResource(R.drawable.notification_icon);
                    ProfileImg.setImageResource(R.drawable.profile_icon);

                    NotifLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    ProfileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    //Selecting the home tab
                    homeTxt.setVisibility(View.VISIBLE);
                    homeImg.setImageResource(R.drawable.home_icon);
                    homeLayout.setBackgroundResource(R.drawable.round_back_home_100);

                    //Create Animation for the selected tab (home)

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f,1f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    homeLayout.startAnimation(scaleAnimation);

                    //Setting first tab as selected tab
                    selectedTab = 1;


                }

            }
        });


        NotifLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedTab != 2){

                    //Set Notifications Fragment by default
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, AgenceI_Tchat_Home.class,null)
                            .commit();

                    //UnSelect all the other tabs except for Notifications
                    homeTxt.setVisibility(View.GONE);
                    ProfileTxt.setVisibility(View.GONE);


                    homeImg.setImageResource(R.drawable.home_icon);
                    ProfileImg.setImageResource(R.drawable.profile_icon);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    ProfileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    //Selecting the Notif tab
                    NotifTxt.setVisibility(View.VISIBLE);
                    NotifImg.setImageResource(R.drawable.notification_icon);
                    NotifLayout.setBackgroundResource(R.drawable.round_back_notif_100);

                    //Create Animation for the selected tab (notification)

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f,1f,1f, Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    NotifLayout.startAnimation(scaleAnimation);

                    //Setting second tab as selected tab
                    selectedTab = 2;


                }

            }
        });


        ProfileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedTab != 3){

                    //Set Profile Fragment by default
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer,BlankFragment.class,null)
                            .commit();

                    //UnSelect all the other tabs except for Profile
                    NotifTxt.setVisibility(View.GONE);
                    homeTxt.setVisibility(View.GONE);


                    NotifImg.setImageResource(R.drawable.notification_icon);
                    homeImg.setImageResource(R.drawable.home_icon);

                    NotifLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    //Selecting the Profile tab
                    ProfileTxt.setVisibility(View.VISIBLE);
                    ProfileImg.setImageResource(R.drawable.profile_icon);
                    ProfileLayout.setBackgroundResource(R.drawable.round_back_profile_100);

                    //Create Animation for the selected tab (profile)

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f,1f,1f, Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    ProfileLayout.startAnimation(scaleAnimation);

                    //Setting third tab as selected tab
                    selectedTab = 3;


                }

            }
        });

    }

    void setOfferList(/*String userName*/) {
        /*Bundle bundle = new Bundle();
        bundle.putString("userName", userName);*/

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, AgenceI_Home_Fragment.class, null)
                .commit();

        bottomBar.setVisibility(View.VISIBLE);
    }

    void setCreateOffer() {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, AgenceI_Home_Ajout_Offre.class, null)
                .commit();

        bottomBar.setVisibility(View.GONE);
    }

    void setConversationList() {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, AgenceI_Tchat_Home.class, null)
                .commit();

        bottomBar.setVisibility(View.VISIBLE);
    }

    void setCreateConversation() {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, AgenceI_Tchat_New.class, null)
                .commit();

        bottomBar.setVisibility(View.GONE);
    }

    void setConversation(String userName, String name, String conversationId) {
        Bundle bundle = new Bundle();
        bundle.putString("userName", userName);
        bundle.putString("name", name);
        bundle.putString("conversationId", conversationId);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, AgenceI_Tchat_Click.class, bundle)
                .commit();

        bottomBar.setVisibility(View.GONE);
    }
}