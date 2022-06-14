package com.ioad.honey.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.ioad.honey.R;
import com.ioad.honey.bean.UserInfo;
import com.ioad.honey.common.BaseActivity;
import com.ioad.honey.common.Constant;
import com.ioad.honey.common.DBHelper;
import com.ioad.honey.common.Shared;
import com.ioad.honey.task.InsertNetworkTask;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BuyActivity extends BaseActivity {

    private final String TAG = getClass().getSimpleName();

    private EditText etBuyAddr, etBuyAddrDetail, etBuyRequest;
    private Spinner buySpinner;
    private Button btnBuySearch, btnGoBuy;
    private RadioGroup rgBuyKind;
    private RadioButton rbBuyCard, rbBuyBank;
    private TextView tvBuyPriceResult, tvBuyTip, tvBuyTot;

    private ArrayAdapter<CharSequence> adapter;
    int totalPrice;
    DecimalFormat priceFormat = new DecimalFormat("###,###");
    long now;
    Date date;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-hhmmss");
    private DBHelper helper;
    private Cursor cursor;
    private ArrayList<UserInfo> userInfos;
    private String requestStr, userId, url, buyCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        etBuyAddr = findViewById(R.id.et_buy_addr);
        etBuyAddrDetail = findViewById(R.id.et_buy_addr_detail);
        etBuyRequest = findViewById(R.id.et_buy_request);
        buySpinner = findViewById(R.id.buy_spinner);
        btnBuySearch = findViewById(R.id.btn_buy_search);
        btnGoBuy = findViewById(R.id.btn_go_buy);
        rgBuyKind = findViewById(R.id.rg_buy_kind);
        tvBuyPriceResult = findViewById(R.id.tv_buy_price_result);
        tvBuyTip = findViewById(R.id.tv_buy_tip);
        tvBuyTot = findViewById(R.id.tv_buy_total);

        helper = new DBHelper(BuyActivity.this);
        userInfos = new ArrayList<>();

        Intent intent = getIntent();
        totalPrice = intent.getIntExtra("TOTAL_PRICE", 0);
        Log.e(TAG, "total price " + totalPrice);
        userId = Shared.getStringPref(BuyActivity.this, "USER_ID");

        setActivityView();

        btnBuySearch.setOnClickListener(btnOnClickListener);
        btnGoBuy.setOnClickListener(btnOnClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectSQLite();
    }

    @Override
    public void setActivityView() {
        super.setActivityView();
        adapter = ArrayAdapter.createFromResource(this, R.array.request, android.R.layout.simple_spinner_dropdown_item);
        buySpinner.setAdapter(adapter);
        buySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                requestStr = (String) adapterView.getItemAtPosition(position);
                Log.e(TAG, requestStr);
                if (position == 4) {
                    etBuyRequest.setVisibility(View.VISIBLE);
                    etBuyRequest.requestFocus();
                    requestStr = null;
                } else {
                    requestStr = (String) adapterView.getItemAtPosition(position);
                    etBuyRequest.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tvBuyPriceResult.setText(priceFormat.format(totalPrice) + "원");
        if (totalPrice <= 30000) {
            tvBuyTot.setText(priceFormat.format(totalPrice + 3000) + "원");
            tvBuyTip.setText("3,000원");
        } else {
            tvBuyTot.setText(priceFormat.format(totalPrice) + "원");
            tvBuyTip.setText("무료배송");
        }

    }

    @Override
    public void selectSQLite() {
        super.selectSQLite();
        cursor = helper.selectAddressData("DELIVERY_ADDR");
        userInfos.clear();
        while (cursor.moveToNext()) {
            String addr = cursor.getString(0);
            String addrDetail = cursor.getString(1);
            String date = cursor.getString(2);

            if (addr != null) {
                UserInfo userInfo = new UserInfo(addr, addrDetail, date);
                userInfos.add(userInfo);
            }
        }

        if (userInfos.size() != 0) {
            etBuyAddr.setText(userInfos.get(0).getUserAddr());
            etBuyAddrDetail.setText(userInfos.get(0).getUserAddrDetail());
        } else {

        }
    }

    @Override
    public String insertAsyncData(String buyCode) {

        url = Constant.SERVER_IP + "honey/Buy_Order_Insert_Return.jsp?"
                + "Client_cId=" + userId
                + "&buyNum=" + buyCode
                + "&buyPostNum=" + null
                + "&buyAddress1=" + etBuyAddr.getText()
                + "&buyAddress2=" + etBuyAddrDetail.getText()
                + "&buyRequests=" + requestStr
                + "&buyDeliveryPrice=" + tvBuyTot.getText();
        Log.e(TAG, url);
        String result = null;
        try {
            InsertNetworkTask task = new InsertNetworkTask(BuyActivity.this, url, "insert");
            Object obj = task.execute().get();
            result = (String) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    View.OnClickListener btnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch (view.getId()) {
                case R.id.btn_buy_search:
                    intent = new Intent(BuyActivity.this, JoinAddrActivity.class);
                    intent.putExtra("BUY_SEARCH", true);
                    startActivity(intent);
                    break;
                case R.id.btn_go_buy:
                    Log.e(TAG, etBuyAddr.getText().toString());
                    Log.e(TAG, etBuyAddrDetail.getText().toString());
                    if (requestStr == null) {
                        Log.e(TAG, etBuyRequest.getText().toString());
                        requestStr = etBuyRequest.getText().toString();
                    } else {
                        Log.e(TAG, requestStr);
                    }
                    buyCode = getTime();
                    int result = Integer.parseInt(insertAsyncData(buyCode));
                    Log.e(TAG, "result " + result);
                    if (result >= 1) {
                        intent = new Intent(BuyActivity.this, BuyResultActivity.class);
                        intent.putExtra("BUY_PRICE", tvBuyPriceResult.getText());
                        intent.putExtra("BUY_TIP", tvBuyTip.getText());
                        intent.putExtra("BUY_TOTAL", tvBuyTot.getText());
                        intent.putExtra("BUY_CODE", buyCode);
                        startActivity(intent);
                    }
                    break;
            }
        }
    };

    private String getTime(){
        now = System.currentTimeMillis();
        date = new Date(now);
        return dateFormat.format(date);
    }



}