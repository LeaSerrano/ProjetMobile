package com.example.projet;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class Employeur_Home_Fragment extends Fragment {

    private TextView tabOffres, tabCandidatures;

    //Value for this is between 1-2
    private int selectedTabNumber = 1;

    private ViewPager viewPager;

    private TabLayout tabLayout;

    public Employeur_Home_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.f_employer_home, container, false);

        viewPager = view.findViewById(R.id.viewPager);

        tabLayout = view.findViewById(R.id.tabLayout);
        tabOffres = view.findViewById(R.id.OffresTab);
        tabCandidatures = view.findViewById(R.id.CandidaturesTab);

        setupViewPager();

        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        selectTab(selectedTabNumber);

        return view;
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new Employeur_Offres(), "Offres");
        adapter.addFragment(new Employeur_Candidatures(), "Candidatures");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        tabOffres.setText("Offres");
        tabCandidatures.setText("Candidatures");
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
        tabOffres.setTextColor(Color.parseColor("#909090"));
        tabCandidatures.setTextColor(Color.parseColor("#909090"));

        tabOffres.setTypeface(null, Typeface.NORMAL);
        tabCandidatures.setTypeface(null, Typeface.NORMAL);

        // Set the selected tab style
        switch (tabNumber) {
            case 0:
                tabOffres.setTextColor(Color.parseColor("#000000"));
                tabOffres.setTypeface(null, Typeface.BOLD);
                break;
            case 1:
                tabCandidatures.setTextColor(Color.parseColor("#000000"));
                tabCandidatures.setTypeface(null, Typeface.BOLD);
                break;
        }
    }

}
