package com.ioad.honey.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ioad.honey.R;
import com.ioad.honey.adapter.MyPageTipAdapter;
import com.ioad.honey.bean.Tip;
import com.ioad.honey.bean.UserInfo;
import com.ioad.honey.common.Constant;
import com.ioad.honey.common.Shared;
import com.ioad.honey.task.SelectNetworkTask;

import java.util.ArrayList;
import java.util.Arrays;

public class MyPageActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private TextView tvUserName;
    private Button btnUserEdit, btnGoCart, btnGoHistory;
    private ListView lvTipList;

    private String userId, userPw, userNm, userPhone, userPost, userAddr, userAddrDetail, userEmail;
    private String url;
    private ArrayList<Tip> tips;
    private ArrayList<UserInfo> userInfos;
    private MyPageTipAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        tvUserName = findViewById(R.id.tv_user_name);
        btnUserEdit = findViewById(R.id.btn_user_edit);
        btnGoCart = findViewById(R.id.btn_go_cart);
        btnGoHistory = findViewById(R.id.btn_go_history);
        lvTipList = findViewById(R.id.lv_tip_list);

        userId = Shared.getStringPref(MyPageActivity.this, "USER_ID");

        btnUserEdit.setOnClickListener(btnOnClickListener);
        btnGoCart.setOnClickListener(btnOnClickListener);
        btnGoHistory.setOnClickListener(btnOnClickListener);

    }


    @Override
    protected void onResume() {
        super.onResume();
        getTipList();
        getUserInfo();
    }

    View.OnClickListener btnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = null;
            switch (view.getId()) {
                case R.id.btn_user_edit:
                    intent = new Intent(MyPageActivity.this, MyPageEditActivity.class);
                    intent.putExtra("USER_PW", userPw);
                    intent.putExtra("USER_NAME", userNm);
                    intent.putExtra("USER_PHONE", userPhone);
                    intent.putExtra("USER_POST", userPost);
                    intent.putExtra("USER_ADDR", userAddr);
                    intent.putExtra("USER_ADDR_DETAIL", userAddrDetail);
                    intent.putExtra("USER_EMAIL", userEmail);
                    startActivity(intent);
                    break;
                case R.id.btn_go_cart:
                    intent = new Intent(MyPageActivity.this, CartActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_go_history:
                    intent = new Intent(MyPageActivity.this, BuyHistoryActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };



    private void getTipList() {
        try {
            url = Constant.SERVER_IP + "honny_tip_m/MyPageCart.jsp?userId=" + userId;
            SelectNetworkTask task = new SelectNetworkTask(MyPageActivity.this, url, "select", "MypageCart");
            Object obj = task.execute().get();
            tips = (ArrayList<Tip>) obj;

            adapter = new MyPageTipAdapter(MyPageActivity.this, R.layout.tip_list_item, tips);
            lvTipList.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getUserInfo() {
        try {
            url = Constant.SERVER_IP + "honny_tip_m/mypageSelect.jsp?userId=" + userId;
            SelectNetworkTask task = new SelectNetworkTask(MyPageActivity.this, url, "select", "myPage");
            Object obj = task.execute().get();
            userInfos = (ArrayList<UserInfo>) obj;

            userPw = userInfos.get(0).getUserPw();
            userNm = userInfos.get(0).getUserNm();
            userPhone = userInfos.get(0).getUserPhone();
            userPost = userInfos.get(0).getUserPostNum();
            userAddr = userInfos.get(0).getUserAddr();
            userAddrDetail = userInfos.get(0).getUserAddrDetail();
            userEmail = userInfos.get(0).getUserEmail();

            tvUserName.setText(userNm + "님");
            btnGoCart.setText(userInfos.get(0).getCartCount());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}