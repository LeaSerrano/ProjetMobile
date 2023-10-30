package com.example.projet;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class Inscrit_Home_Page extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView tabRecommande, tabPourVous, tabEnregistre;
    private int selectedTabNumber = 0;

    private ImageView filterIcon;


    public Inscrit_Home_Page() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_home_inscrits, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        tabRecommande = view.findViewById(R.id.Recommande);
        tabPourVous = view.findViewById(R.id.PourVous);
        tabEnregistre = view.findViewById(R.id.Enregistre);
        filterIcon = view.findViewById(R.id.filterIcon);


        setupViewPager();

        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        selectTab(selectedTabNumber);
        filterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Inscrit_Filtre_Activity.class);
                startActivity(intent);
            }
        });



        return view;
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new Inscrit_Recommande_Fragment(), "Recommande");
        adapter.addFragment(new Inscrit_PourVous_Fragment(), "Pour Vous");
        adapter.addFragment(new Inscrit_Enregistre_Fragment(), "Enregistre");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        tabRecommande.setText("Recommande");
        tabPourVous.setText("Pour Vous");
        tabEnregistre.setText("Enregistre");
    }

    private void selectTab(int tabNumber) {
        if (selectedTabNumber != tabNumber) {
            viewPager.setCurrentItem(tabNumber, true);
            setSelectedTabStyle(tabNumber);
            selectedTabNumber = tabNumber;
        }
    }

    private void setSelectedTabStyle(int tabNumber) {
        // Set the default style for all tabs
        tabRecommande.setTextColor(Color.parseColor("#909090"));
        tabPourVous.setTextColor(Color.parseColor("#909090"));
        tabEnregistre.setTextColor(Color.parseColor("#909090"));

        tabRecommande.setTypeface(null, Typeface.NORMAL);
        tabPourVous.setTypeface(null, Typeface.NORMAL);
        tabEnregistre.setTypeface(null, Typeface.NORMAL);

        // Set the selected tab style
        switch (tabNumber) {
            case 0:
                tabRecommande.setTextColor(Color.parseColor("#000000"));
                tabRecommande.setTypeface(null, Typeface.BOLD);
                break;
            case 1:
                tabPourVous.setTextColor(Color.parseColor("#000000"));
                tabPourVous.setTypeface(null, Typeface.BOLD);
                break;
            case 2:
                tabEnregistre.setTextColor(Color.parseColor("#000000"));
                tabEnregistre.setTypeface(null, Typeface.BOLD);
                break;
        }
    }
}
