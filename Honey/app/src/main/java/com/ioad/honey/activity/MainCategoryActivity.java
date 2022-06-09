package com.ioad.honey.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ioad.honey.adapter.TabPagerAdapter;
import com.ioad.honey.R;
import com.ioad.honey.common.DBHelper;
import com.ioad.honey.common.Shared;
import com.ioad.honey.common.Util;
import com.ioad.honey.task.ImageLoadTask;

public class MainCategoryActivity extends AppCompatActivity {

    Context mContext;
    BottomNavigationView bottomNavigationView;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    TabPagerAdapter tabPagerAdapter;
    String userId;
    ImageLoadTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_category);

        mContext = getApplicationContext();
        userId = Shared.getStringPref(mContext, "USER_ID");
        Log.e("TAG", "user ID ::::: " + Shared.getStringPref(mContext, "USER_ID"));

        SQLiteDatabase db;
        DBHelper helper;
        helper = new DBHelper(MainCategoryActivity.this);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        task = new ImageLoadTask();


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.pager);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()) {
                    case R.id.tab_cart:
                        if (userId.length() == 0) {
//                            Toast.makeText(mContext, R.string.please_login, Toast.LENGTH_SHORT).show();
                            Util.showToast(mContext, "로그인 해주세요");
                        } else {
                            intent = new Intent(mContext, CartActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.tab_buyHistory:
                        intent = new Intent(mContext, BuyHistoryActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.tab_mypage:
                        if (userId.length() == 0) {
                            intent = new Intent(mContext, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            intent = new Intent(mContext, MyPageActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.tab_search:
                        intent = new Intent(mContext, SearchActivity.class);
                        startActivity(intent);
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
                        tab.setText(R.string.category_home);
                        break;
                    case 1:
                        tab.setIcon(android.R.drawable.ic_btn_speak_now);
                        tab.setText(R.string.category_korean);
                        break;
                    case 2:
                        tab.setIcon(android.R.drawable.ic_btn_speak_now);
                        tab.setText(R.string.category_school);
                        break;
                    case 3:
                        tab.setIcon(android.R.drawable.ic_btn_speak_now);
                        tab.setText(R.string.category_soup);
                        break;
                    case 4:
                        tab.setIcon(android.R.drawable.ic_btn_speak_now);
                        tab.setText(R.string.category_instant);
                        break;
                    case 5:
                        tab.setIcon(android.R.drawable.ic_btn_speak_now);
                        tab.setText(R.string.category_dessert);
                        break;
                    case 6:
                        tab.setIcon(android.R.drawable.ic_btn_speak_now);
                        tab.setText(R.string.category_vegan);
                        break;
                }
            }
        }).attach();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("TAG", "onPause");
        task.isCancelled();
    }

}