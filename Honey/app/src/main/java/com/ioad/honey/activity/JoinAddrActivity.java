package com.ioad.honey.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ioad.honey.R;
import com.ioad.honey.common.DBHelper;
import com.ioad.honey.common.Util;
import com.ioad.honey.task.AddressNetworkTask;
import com.ioad.honey.common.Shared;

public class JoinAddrActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private EditText etJoinAddr, etJoinAddrDetail;
    private Button btnSearch, btnNext;
    private String joinId, joinPw, address, addressDetail;
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    private DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_addr);

        btnSearch = findViewById(R.id.btn_join_addr_search);
        etJoinAddr = findViewById(R.id.et_join_addr);
        etJoinAddrDetail = findViewById(R.id.et_join_addr_detail);
        btnNext = findViewById(R.id.btn_addr_next);

//        Intent intent = getIntent();
//        joinId = intent.getStringExtra("JOIN_ID");
//        joinPw = intent.getStringExtra("JOIN_PW");
//
//        Log.e(TAG, "joinID ::::::: " + joinId);
//        Log.e(TAG, "joinPw ::::::: " + joinPw);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int status = AddressNetworkTask.getConnectivityStatus(JoinAddrActivity.this);
                if (status == AddressNetworkTask.TYPE_MOBILE || status == AddressNetworkTask.TYPE_WIFI) {
                    Intent intent = new Intent(JoinAddrActivity.this, DaumActivity.class);
                    startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY);
                } else {
//                    Toast.makeText(JoinAddrActivity.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
                    Util.showToast(JoinAddrActivity.this, "인터넷 연결을 확인해주세요");
                }
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressDetail = etJoinAddrDetail.getText().toString().trim();

//                if (address.equals("")) {
//                    Toast.makeText(JoinAddrActivity.this, "주소를 검색해주세요", Toast.LENGTH_SHORT).show();
//                } else if (addressDetail.equals("")) {
//                    Toast.makeText(JoinAddrActivity.this, "상세 주소를 검색해주세요", Toast.LENGTH_SHORT).show();
//                } else {
                Intent intent1 = new Intent(JoinAddrActivity.this, JoinActivity.class);
//                    intent1.putExtra("JOIN_ADDR", address + addressDetail);
                Shared.setStringPrf(JoinAddrActivity.this, "JOIN_ADDR", address);
                Shared.setStringPrf(JoinAddrActivity.this, "JOIN_ADDR_DETAIL", addressDetail);
                helper.insertAddressData("DELIVERY_ADDR", address, addressDetail);
                intent1.putExtra("PAGE_INDEX", 3);
                startActivity(intent1);
//                }

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
                        Log.e(TAG, data);
                        address = data;
                        etJoinAddr.setText(address);
                        etJoinAddrDetail.requestFocus();
                    }
                }
        }
    }

}