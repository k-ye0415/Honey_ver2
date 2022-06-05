package com.ioad.honey.Activity;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.ioad.honey.Fragment.JoinAddrFragment;
import com.ioad.honey.Fragment.JoinFragment;
import com.ioad.honey.R;

public class JoinActivity extends FragmentActivity {

//    Button btnJoinNext;
    JoinFragment joinFragment;
    JoinAddrFragment joinAddrFragment;
    int pageIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

//        btnJoinNext = findViewById(R.id.btn_next);
        joinFragment = new JoinFragment();
        joinAddrFragment = new JoinAddrFragment();


//        btnJoinNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (pageIndex == 1) {
//                    Toast.makeText(getApplicationContext(), "중복확인", Toast.LENGTH_SHORT).show();
//                    pageIndex++;
//                    changeFragment(pageIndex);
//                } else if (pageIndex == 2) {
//                    Toast.makeText(getApplicationContext(), "주소", Toast.LENGTH_SHORT).show();
//                    changeFragment(1);
//                }
//            }
//        });

    }

    public void changeFragment(int index) {
        switch (index) {
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.frg_join, joinFragment).commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().remove(joinFragment).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.frg_join, joinAddrFragment).commit();
                break;
        }
    }

    public void checkJoin(String id, String pw) {
        Log.d("TAG", "user Id ::: " + id);
        Log.d("TAG", "user pw ::: " + pw);
    }


}