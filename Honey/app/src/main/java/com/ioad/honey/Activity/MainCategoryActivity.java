package com.ioad.honey.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ioad.honey.Adapter.TabPagerAdapter;
import com.ioad.honey.R;

public class MainCategoryActivity extends AppCompatActivity {

    Context mContext;
    BottomNavigationView bottomNavigationView;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    TabPagerAdapter tabPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_category);

        mContext = getApplicationContext();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.pager);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()) {
                    case R.id.tab_cart:
                        Toast.makeText(mContext, "tab_cart", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tab_buyHistory:
                        Toast.makeText(mContext, "tab_buyHistory", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tab_mypage:
                        Toast.makeText(mContext, "tab_mypage", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tab_search:
                        Toast.makeText(mContext, "tab_search", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

        getViewPager();
    }


    private void getViewPager() {
        tabPagerAdapter = new TabPagerAdapter(this);
        viewPager.setAdapter(tabPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setIcon(android.R.drawable.ic_btn_speak_now);
                        tab.setText("HOME");
                        break;
                }
            }
        }).attach();
    }
}