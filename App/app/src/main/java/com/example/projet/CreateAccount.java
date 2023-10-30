package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class CreateAccount extends AppCompatActivity {

    private Inscrit_CreateAccount registeredAccount;
    private Employeur_CreateAccount employerAccount;
    private AgenceI_CreateAccount employmentAgencyAccount;

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_create_account);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        PagerAdapter adapter = new PagerAdapter(this);
        adapter.addFragment(new Inscrit_CreateAccount(), "Inscrit");
        adapter.addFragment(new Employeur_CreateAccount(), "Employeur");
        adapter.addFragment(new AgenceI_CreateAccount(), "Agence d'intérim");

        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(adapter.getPageTitle(position).toString())).attach();

    }

}