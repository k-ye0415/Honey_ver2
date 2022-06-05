package com.ioad.honey.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ioad.honey.Bean.UserInfo;
import com.ioad.honey.R;
import com.ioad.honey.Task.LoginNetworkTask;
import com.ioad.honey.common.Constant;
import com.ioad.honey.common.Shared;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    Button btnLoginBack, btnLogin, btnJoin;
    EditText etLoginId, etLoginPw;
    TextView tvIdSearch, tvPwSearch;

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
                    Toast.makeText(LoginActivity.this, "아이디 및 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
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
            url = Constant.SERVER_IP +  "honey_login_confirm_j.jsp?cId=" + userId + "&cPw=" + userPw;
            LoginNetworkTask task = new LoginNetworkTask(LoginActivity.this, url, "login", "login");
            Object obj = task.execute().get();
            userInfos = (ArrayList<UserInfo>) obj;

            if (userInfos == null) {
                Toast.makeText(LoginActivity.this, "아이디 비밀번호 확인해주세요", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, "hello", Toast.LENGTH_SHORT).show();
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