package com.ioad.honey.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ioad.honey.R;
import com.ioad.honey.common.Constant;
import com.ioad.honey.common.Shared;
import com.ioad.honey.task.AddressNetworkTask;
import com.ioad.honey.task.UpdateNetworkTask;

public class MyPageEditActivity extends AppCompatActivity {

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    EditText etMyId, etMyPw, etMyName, etMyPhone, etMyPost, etMyAddr, etMyAddrDetail, etMyEmail;
    Button btnMyEdit, btnLogout, btnEditAddrSearch, btnDaiLogout, btnDaiCancel;

    String userId, userPw, userNm, userPhone, userPost, userAddr, userAddrDetail, userEmail;
    String url;
    Dialog logoutDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_edit);

        userId = Shared.getStringPref(MyPageEditActivity.this, "USER_ID");

        etMyId = findViewById(R.id.et_my_id);
        etMyPw = findViewById(R.id.et_my_pw);
        etMyName = findViewById(R.id.et_my_name);
        etMyPhone = findViewById(R.id.et_my_phone);
        etMyAddr = findViewById(R.id.et_my_addr);
        etMyAddrDetail = findViewById(R.id.et_my_addr_detail);
        etMyEmail = findViewById(R.id.et_my_email);
        btnMyEdit = findViewById(R.id.btn_my_edit);
        btnLogout = findViewById(R.id.btn_logout);
        btnEditAddrSearch = findViewById(R.id.btn_edit_addr_search);

        Intent intent = getIntent();
        userPw = intent.getStringExtra("USER_PW");
        userNm = intent.getStringExtra("USER_NAME");
        userPhone = intent.getStringExtra("USER_PHONE");
        userPost = intent.getStringExtra("USER_POST");
        userAddr = intent.getStringExtra("USER_ADDR");
        userAddrDetail = intent.getStringExtra("USER_ADDR_DETAIL");
        userEmail = intent.getStringExtra("USER_EMAIL");

        btnEditAddrSearch.setOnClickListener(btnOnClickListener);
        btnMyEdit.setOnClickListener(btnOnClickListener);
        btnLogout.setOnClickListener(btnOnClickListener);

    }

    @Override
    protected void onResume() {
        super.onResume();
        etMyId.setText(userId);
        etMyPw.setText(userPw);
        etMyName.setText(userNm);
        etMyPhone.setText(userPhone);
        etMyAddr.setText(userAddr);
        etMyAddrDetail.setText(userAddrDetail);
        etMyEmail.setText(userEmail);
    }


    View.OnClickListener btnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_edit_addr_search:
                    int status = AddressNetworkTask.getConnectivityStatus(MyPageEditActivity.this);
                    if (status == AddressNetworkTask.TYPE_MOBILE || status == AddressNetworkTask.TYPE_WIFI) {
                        Intent intent = new Intent(MyPageEditActivity.this, DaumActivity.class);
                        startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY);
                    } else {
                        Toast.makeText(MyPageEditActivity.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    break;
                case R.id.btn_my_edit:
                    userPw = etMyPw.getText().toString().trim();
                    userNm = etMyName.getText().toString().trim();
                    userPhone = etMyPhone.getText().toString().trim();
                    userAddr = etMyAddr.getText().toString();
                    userAddrDetail = etMyAddrDetail.getText().toString().trim();
                    userEmail = etMyEmail.getText().toString().trim();
                    String tempURL = "userPw=" + userPw
                            + "&userName=" + userNm
                            + "&userTel=" + userPhone
                            + "&userPostNum=" + null
                            + "&userAddress1=" + userAddr
                            + "&userAddress2=" + userAddrDetail
                            + "&userEmail=" + userEmail
                            + "&userId=" + userId;
                    String result = updateUserInfo(tempURL);
                    if (result.equals("1")) {
                        Toast.makeText(MyPageEditActivity.this, "수정 완료 되었습니다", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btn_logout:
                    logoutDialog();
                    break;
            }
        }
    };


    private String updateUserInfo(String tempURL) {
        String result = null;
        try {
            url = Constant.SERVER_IP + "honny_tip_m/mypageUpdate.jsp?" + tempURL;
            Log.e("TAG", "update USER INFO URL :: " + url);
            UpdateNetworkTask task = new UpdateNetworkTask(MyPageEditActivity.this, url, "update");
            Object obj = task.execute().get();
            result = (String) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    private void logoutDialog(){
        logoutDialog = new Dialog(MyPageEditActivity.this);
        logoutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        logoutDialog.setContentView(R.layout.logout_dialog);

        WindowManager.LayoutParams lpDown = new WindowManager.LayoutParams();
        lpDown.copyFrom(logoutDialog.getWindow().getAttributes());
        lpDown.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lpDown.height = WindowManager.LayoutParams.WRAP_CONTENT;

        Window window = logoutDialog.getWindow();
        window.setAttributes(lpDown);

        logoutDialog.show();
        btnDaiCancel = logoutDialog.findViewById(R.id.btn_dia_cancel);
        btnDaiLogout = logoutDialog.findViewById(R.id.btn_dia_logout);

        btnDaiCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog.dismiss();
            }
        });

        btnDaiLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPageEditActivity.this, MainCategoryActivity.class);
                Shared.removeStringPrf(MyPageEditActivity.this, "USER_ID");
                startActivity(intent);
                Toast.makeText(MyPageEditActivity.this, "로그아웃 완료. 다음에 만나요~", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        Log.e("TAG", data);
                        userAddr = data;
                        userAddrDetail = "";
                        etMyAddrDetail.requestFocus();
                    }
                }
        }
    }
}