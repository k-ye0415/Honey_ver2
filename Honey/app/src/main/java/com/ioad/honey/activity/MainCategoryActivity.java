package com.ioad.honey.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ioad.honey.adapter.TabPagerAdapter;
import com.ioad.honey.R;
import com.ioad.honey.bean.UserInfo;
import com.ioad.honey.common.DBHelper;
import com.ioad.honey.common.Shared;
import com.ioad.honey.common.Util;
import com.ioad.honey.fragment.AddrBottomSheet;
import com.ioad.honey.task.ImageLoadTask;

import java.util.ArrayList;

public class MainCategoryActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    private Context mContext;
    private TextView tvSelectAddr;
    private BottomNavigationView bottomNavigationView;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private TabPagerAdapter tabPagerAdapter;
    private String userId;
    private ImageLoadTask task;
    //    private DBHelper helper;
//    private Cursor cursor;
//    private ArrayList<UserInfo> userInfos;
    private SlidingPaneLayout sliding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_category);

        mContext = getApplicationContext();
        userId = Shared.getStringPref(mContext, "USER_ID");
        Log.e(TAG, "user ID ::::: " + userId);

        SQLiteDatabase db;
        DBHelper helper;
        helper = new DBHelper(MainCategoryActivity.this);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        task = new ImageLoadTask();

        tvSelectAddr = findViewById(R.id.tv_select_addr);
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

        tvSelectAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddrBottomSheet bottomSheet = new AddrBottomSheet();
                bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
            }
        });
        getViewPager();

//        sliding.setPanelStat();

        if (userId.length() == 0) {
            tvSelectAddr.setText("주소지를 선택해주세요");
        } else {

        }

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

//    private void getMyAddressList() {
//        cursor = helper.selectAddressData("DELIVERY_ADDR");
//        userInfos.clear();
//        while (cursor.moveToNext()) {
//            String addr = cursor.getString(0);
//            String addrDetail = cursor.getString(1);
//            String date = cursor.getString(2);
//
//            UserInfo userInfo = new UserInfo(addr, addrDetail, date);
//            userInfos.add(userInfo);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        Log.e(TAG, data);
                    }
                }
        }
    }
}