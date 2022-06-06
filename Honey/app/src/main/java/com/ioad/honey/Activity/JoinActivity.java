package com.ioad.honey.Activity;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ioad.honey.Fragment.JoinAddrFragment;
import com.ioad.honey.Fragment.JoinElseFragment;
import com.ioad.honey.Fragment.JoinFragment;
import com.ioad.honey.Fragment.TOSFragment;
import com.ioad.honey.R;

public class JoinActivity extends FragmentActivity {

    TOSFragment tosFragment;
    JoinFragment joinFragment;
    JoinAddrFragment joinAddrFragment;
    JoinElseFragment joinElseFragment;
    int pageIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        tosFragment = new TOSFragment();
        joinFragment = new JoinFragment();
        joinAddrFragment = new JoinAddrFragment();
        joinElseFragment = new JoinElseFragment();

    }

    public void changeFragment(int index) {
        switch (index) {
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.frg_join, tosFragment).commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.frg_join, joinFragment).commit();
//                getSupportFragmentManager().beginTransaction().remove(joinFragment).commit();
//                getSupportFragmentManager().beginTransaction().replace(R.id.frg_join, joinAddrFragment).commit();
                break;
            case 3:
                getSupportFragmentManager().beginTransaction().remove(joinFragment).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.frg_join, joinElseFragment).commit();
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG", "onResume pageIndex " + pageIndex);
        Intent intent = getIntent();
        int intentInt = intent.getIntExtra("PAGE_INDEX", 1);
        if (intentInt == 1) {
            changeFragment(pageIndex);
        } else {
            Log.e("TAG", "onResume intent " + intentInt);
            pageIndex = intentInt;
            changeFragment(pageIndex);
        }

    }
}