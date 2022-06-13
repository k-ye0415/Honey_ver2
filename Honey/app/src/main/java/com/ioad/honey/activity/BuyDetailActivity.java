package com.ioad.honey.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ioad.honey.R;
import com.ioad.honey.bean.BuyDetail;
import com.ioad.honey.common.BaseActivity;
import com.ioad.honey.common.Constant;
import com.ioad.honey.task.SelectNetworkTask;

import java.util.ArrayList;

public class BuyDetailActivity extends BaseActivity {

    private Button btnBuyCancel;
    private TextView tvBuyDate, tvBuyCode, tvBuyAddr, tvBuyRequest, tvBuyStatus, tvBuyPriceTot;
    private RecyclerView rvBuyList;
    private String buyCode, url;
    private ArrayList<BuyDetail> details;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_detail);

        btnBuyCancel = findViewById(R.id.btn_buy_cancel);
        tvBuyDate = findViewById(R.id.tv_buy_date);
        tvBuyCode = findViewById(R.id.tv_buy_code);
        tvBuyAddr = findViewById(R.id.tv_buy_addr);
        tvBuyRequest = findViewById(R.id.tv_buy_request);
        tvBuyStatus = findViewById(R.id.tv_buy_status);
        tvBuyPriceTot = findViewById(R.id.tv_buy_price_total);
        rvBuyList = findViewById(R.id.rv_buy_list);

        Intent intent = getIntent();
        buyCode = intent.getStringExtra("BUY_CODE");

        details = new ArrayList<>();

        url = Constant.SERVER_IP + "honey/Payment_Detail_All_List.jsp?buyNum=" + buyCode;
        selectAsyncData(url);

    }

    @Override
    public void selectAsyncData(String url) {
        super.selectAsyncData(url);
        try {
            SelectNetworkTask task = new SelectNetworkTask(BuyDetailActivity.this, url, "select", "paymentDetail_info");
            Object obj = task.execute().get();
            details = (ArrayList<BuyDetail>) obj;

//            tvBuyDate.setText("주문일자 : " + details.get(0).getBuyDate());
//            tvBuyCode.setText("주문번호 : " + buyCode);
//            tvBuyAddr.setText("배송지 : " + details.get(0).getBuyPostNum() + " " + details.get(0).getBuyAddr() + " " + details.get(0).getBuyAddrDetail());
//            tvBuyPriceTot.setText("총 금액 : " + details.get(0).getBuyDeliveryPrice());
//
//            if (details.get(0).getBuyCancelDate().equals("null")) {
//                btnBuyCancel.setVisibility(View.VISIBLE);
//                tvBuyStatus.setText("주문 완료");
//                tvBuyStatus.setTextColor(0xFF000000);
//            } else {
//                btnBuyCancel.setVisibility(View.INVISIBLE);
//                tvBuyStatus.setText("주문 취소 : " + details.get(0).getBuyCancelDate());
//                tvBuyStatus.setTextColor(0xFFFF0000);
//            }
//
//            if (details.get(0).getBuyRequest().equals("null")) {
//                tvBuyRequest.setVisibility(View.INVISIBLE);
//            } else {
//                tvBuyRequest.setVisibility(View.VISIBLE);
//                tvBuyRequest.setText("요청 사항 : " + details.get(0).getBuyRequest());
//            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}