package com.ioad.honey.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ioad.honey.R;
import com.ioad.honey.adapter.BuyHistoryAdapter;
import com.ioad.honey.bean.BuyHistory;
import com.ioad.honey.common.Constant;
import com.ioad.honey.common.Shared;
import com.ioad.honey.task.SelectNetworkTask;

import java.util.ArrayList;

public class BuyHistoryActivity extends BaseActivity {

    LinearLayout llHistoryList, llHistoryEmpty;
    Button btnGoShopping;
    RecyclerView rvHistoryList;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    String userId, url;
    ArrayList<BuyHistory> histories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_history);

        llHistoryList = findViewById(R.id.ll_history_list);
        llHistoryEmpty = findViewById(R.id.ll_history_empty);
        rvHistoryList = findViewById(R.id.rv_history_list);
        btnGoShopping = findViewById(R.id.btn_go_shopping);

        userId = Shared.getStringPref(BuyHistoryActivity.this, "USER_ID");

    }

    @Override
    protected void onResume() {
        super.onResume();
        url = Constant.SERVER_IP + "honey/Payment_History_Select_Info.jsp?Client_cId=" + userId;
        selectAsyncData(url);
    }

//    private void selectGetData() {
//        try {
//            url = Constant.SERVER_IP + "honey/Payment_History_Select_Info.jsp?Client_cId=" + userId;
//
//            SelectNetworkTask task = new SelectNetworkTask(BuyHistoryActivity.this, url, "select", "paymentHistory_info");
//            Object obj = task.execute().get();
//            histories = (ArrayList<BuyHistory>) obj;
//
//            if (histories.isEmpty()) {
//                llHistoryList.setVisibility(View.INVISIBLE);
//                llHistoryEmpty.setVisibility(View.VISIBLE);
//            } else {
//                llHistoryList.setVisibility(View.VISIBLE);
//                llHistoryEmpty.setVisibility(View.INVISIBLE);
//
//                layoutManager = new LinearLayoutManager(BuyHistoryActivity.this);
//                rvHistoryList.setLayoutManager(layoutManager);
//                adapter = new BuyHistoryAdapter(BuyHistoryActivity.this, R.layout.buy_history_list_layout, histories);
//                rvHistoryList.setAdapter(adapter);
//            }
//
//
//        } catch (Exception e) {
//
//        }
//    }

    @Override
    public void selectAsyncData(String url) {
        super.selectAsyncData(url);
        try {
            SelectNetworkTask task = new SelectNetworkTask(BuyHistoryActivity.this, url, "select", "paymentHistory_info");
            Object obj = task.execute().get();
            histories = (ArrayList<BuyHistory>) obj;

            if (histories.isEmpty()) {
                llHistoryList.setVisibility(View.INVISIBLE);
                llHistoryEmpty.setVisibility(View.VISIBLE);
            } else {
                llHistoryList.setVisibility(View.VISIBLE);
                llHistoryEmpty.setVisibility(View.INVISIBLE);

                layoutManager = new LinearLayoutManager(BuyHistoryActivity.this);
                rvHistoryList.setLayoutManager(layoutManager);
                adapter = new BuyHistoryAdapter(BuyHistoryActivity.this, R.layout.buy_history_list_layout, histories);
                rvHistoryList.setAdapter(adapter);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}