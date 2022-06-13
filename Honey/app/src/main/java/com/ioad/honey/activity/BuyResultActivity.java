package com.ioad.honey.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.ioad.honey.R;
import com.ioad.honey.bean.BuyDetail;
import com.ioad.honey.common.BaseActivity;
import com.ioad.honey.common.Constant;
import com.ioad.honey.task.SelectNetworkTask;

import java.util.ArrayList;

public class BuyResultActivity extends BaseActivity {

    private TextView tvBuyMenu, tvBuyAddr, tvBuyRequest, tvBuyPrice, tvBuyTip, tvBuyPriceTot;
    private Button btnGoDetail, btnGoMain;
    private String buyCode, buyPrice, buyTip, buyPriceTot, url;

    private ArrayList<BuyDetail> details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_result);

        tvBuyMenu = findViewById(R.id.tv_buy_result_menu);
        tvBuyAddr = findViewById(R.id.tv_buy_result_addr);
        tvBuyRequest = findViewById(R.id.tv_buy_result_req);
        tvBuyPrice = findViewById(R.id.tv_buy_result_price);
        tvBuyTip = findViewById(R.id.tv_buy_result_tip);
        tvBuyPriceTot = findViewById(R.id.tv_buy_result_price_tot);
        btnGoDetail = findViewById(R.id.btn_go_detail);
        btnGoMain = findViewById(R.id.btn_go_main);

        Intent intent = getIntent();
        buyCode = intent.getStringExtra("BUY_CODE");
        buyPrice = intent.getStringExtra("BUY_PRICE");
        buyTip = intent.getStringExtra("BUY_TIP");
        buyPriceTot = intent.getStringExtra("BUY_TOTAL");

        details = new ArrayList<>();

        tvBuyPrice.setText(buyPrice);
        tvBuyTip.setText(buyTip);
        tvBuyPriceTot.setText(buyPriceTot);

    }

    @Override
    protected void onResume() {
        super.onResume();
        url = Constant.SERVER_IP + "honey/Buy_Order_Select_Info.jsp?buyNum=" + buyCode;
        selectAsyncData(url);
    }

    @Override
    public void selectAsyncData(String url) {
        try {
            SelectNetworkTask task = new SelectNetworkTask(BuyResultActivity.this, url, "select", "buy_info");
            Object obj = task.execute().get();
            details = (ArrayList<BuyDetail>) obj;

            int count = Integer.parseInt(details.get(0).getCount());

            tvBuyMenu.setText(details.get(0).getMenuName() + details.get(0).getIngredientCapacity()
            + details.get(0).getIngredientUnit() + "외 " + (count - 1) + "개");
            tvBuyAddr.setText(details.get(0).getBuyAddr() + details.get(0).getBuyAddrDetail());
            tvBuyRequest.setText(details.get(0).getBuyRequest());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}