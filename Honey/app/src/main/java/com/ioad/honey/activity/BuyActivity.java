package com.ioad.honey.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ioad.honey.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class BuyActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private EditText etBuyAddr, etBuyAddrDetail, etBuyRequest;
    private Button btnBuySearch, btnGoBuy;
    private RadioGroup rgBuyKind;
    private RadioButton rbBuyCard, rbBuyBank;
    private TextView tvBuyPriceResult, tvBuyTip, tvBuyTot;

    int totalPrice;
    DecimalFormat priceFormat = new DecimalFormat("###,###");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-hhmmss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        etBuyAddr = findViewById(R.id.et_buy_addr);
        etBuyAddrDetail = findViewById(R.id.et_buy_addr_detail);
        etBuyRequest = findViewById(R.id.et_buy_request);
        btnBuySearch = findViewById(R.id.btn_buy_search);
        btnGoBuy = findViewById(R.id.btn_go_buy);
        rgBuyKind = findViewById(R.id.rg_buy_kind);
        tvBuyPriceResult = findViewById(R.id.tv_buy_price_result);
        tvBuyTip = findViewById(R.id.tv_buy_tip);
        tvBuyTot = findViewById(R.id.tv_buy_total);

        Intent intent = getIntent();
        totalPrice = intent.getIntExtra("TOTAL_PRICE", 0);

        if (totalPrice > 10000 && totalPrice < 30000) {
            tvBuyTot.setText(priceFormat.format(totalPrice + 3000) + "원");
            tvBuyTip.setText("3,000원");
        } else {
            tvBuyTot.setText(priceFormat.format(totalPrice) + "원");
            tvBuyTip.setText("무료배송");
        }


    }
}