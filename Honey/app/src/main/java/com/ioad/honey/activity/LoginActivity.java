package com.ioad.honey.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ioad.honey.bean.UserInfo;
import com.ioad.honey.R;
import com.ioad.honey.common.Util;
import com.ioad.honey.task.LoginNetworkTask;
import com.ioad.honey.common.Constant;
import com.ioad.honey.common.Shared;
import com.ioad.honey.task.SelectNetworkTask;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private Button btnLogin, btnJoin;
    private ImageButton btnLoginBack;
    private EditText etLoginId, etLoginPw;
    private TextView tvIdSearch, tvPwSearch;

    private String url = null;
    private String userId;
    private String userPw;
    private ArrayList<UserInfo> userInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLoginBack = findViewById(R.id.btn_login_back);
        btnLogin = findViewById(R.id.btn_login);
        btnJoin = findViewById(R.id.btn_join);
        etLoginId = findViewById(R.id.et_login_id);
        etLoginPw = findViewById(R.id.et_login_pw);
        tvIdSearch = findViewById(R.id.tv_id_search);
        tvPwSearch = findViewById(R.id.tv_pw_search);

        btnLoginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etLoginId.getText() == null || etLoginPw.getText() == null) {
//                    Toast.makeText(LoginActivity.this, R.string.check_login, Toast.LENGTH_SHORT).show();
                    Util.showToast(LoginActivity.this, "아이디 및 비밀번호를 입력해주세요");
                } else {
                    userId = etLoginId.getText().toString();
                    userPw = etLoginPw.getText().toString();
                    userInfos = new ArrayList<>();

                    checkLogin();
                }

            }
        });
    }


    private void checkLogin() {
        try {
            url = Constant.SERVER_IP +  "honey/honey_login_confirm_j.jsp?cId=" + userId + "&cPw=" + userPw;
            SelectNetworkTask task = new SelectNetworkTask(LoginActivity.this, url, "login", "login_info");
            Object obj = task.execute().get();
            userInfos = (ArrayList<UserInfo>) obj;

            if (userInfos == null) {
//                Toast.makeText(LoginActivity.this, R.string.false_login, Toast.LENGTH_SHORT).show();
                Util.showToast(LoginActivity.this, "아이디 및 비밀번호를 확인해주세요");
            } else {
                String id = userInfos.get(0).getUserId();
                Shared.setStringPrf(LoginActivity.this, "USER_ID", id);
                Intent intent = new Intent(LoginActivity.this, MainCategoryActivity.class);
                startActivity(intent);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}