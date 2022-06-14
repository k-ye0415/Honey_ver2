package com.ioad.honey.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ioad.honey.adapter.TipAdapter;
import com.ioad.honey.bean.Tip;
import com.ioad.honey.R;
import com.ioad.honey.common.BaseActivity;
import com.ioad.honey.task.ImageLoadTask;
import com.ioad.honey.task.SelectNetworkTask;
import com.ioad.honey.common.Constant;

import java.util.ArrayList;

public class TipActivity extends BaseActivity {

    private final String TAG = getClass().getSimpleName();
    private Context mContext;
    private ImageView ivTipMenu, ivTipTitle;
    private TextView tvTipName;
    private RecyclerView rvTipList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private Button btn_tip_add;

    private ImageLoadTask task;
    private String selectCode;
    private String selectName;
    private String strUrl;
    private ArrayList<Tip> tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        mContext = TipActivity.this;

        ivTipMenu = findViewById(R.id.iv_tip_menu);
        ivTipTitle = findViewById(R.id.iv_tip_title);
        tvTipName = findViewById(R.id.tv_tip_name);
        rvTipList = findViewById(R.id.rv_tip_list);
        btn_tip_add = findViewById(R.id.btn_tip_add);

        // 값받아오기
        Intent intent = getIntent();
        selectCode = intent.getStringExtra("mCode");
        selectName = intent.getStringExtra("mName");

        setActivityView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        selectAsyncData();
    }

    @Override
    public void setActivityView() {
        tvTipName.setText(selectName);
//        getImage(selectCode, ivTipMenu);
//        getImage(null, ivTipTitle);
        imageAsync(selectCode, ivTipMenu);
        imageAsync(null, ivTipTitle);
    }

    @Override
    public void imageAsync(String imageCode, ImageView imageView) {
        String url = "";
        if (imageCode != null) {
            url = Constant.SERVER_URL_IMG + "foodcode" + imageCode + ".png";
        } else {
            url = Constant.SERVER_URL_IMG + "tip_title.png";
        }
        task = new ImageLoadTask(url, imageView);
        task.execute();
    }

    @Override
    public void selectAsyncData() {
        strUrl = Constant.SERVER_URL_JSP + "tip_select.jsp?mCode=" + selectCode;
        try {
            SelectNetworkTask networkTask = new SelectNetworkTask(mContext, strUrl, "select", "tip");
            Object obj = networkTask.execute().get();
            tips = (ArrayList<Tip>) obj;
            layoutManager = new LinearLayoutManager(mContext);
            rvTipList.setLayoutManager(layoutManager);

            adapter = new TipAdapter(mContext, R.layout.tip_item, tips);
            rvTipList.setAdapter(adapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void getTipList() {
//        strUrl = Constant.SERVER_URL_JSP + "tip_select.jsp?mCode=" + selectCode;
//        try {
//            SelectNetworkTask networkTask = new SelectNetworkTask(TipActivity.this, strUrl, "select", "tip");
//            Object obj = networkTask.execute().get();
//            tips = (ArrayList<Tip>) obj;
//            layoutManager = new LinearLayoutManager(TipActivity.this);
//            rvTipList.setLayoutManager(layoutManager);
//
//            adapter = new TipAdapter(TipActivity.this, R.layout.tip_item, tips);
//            rvTipList.setAdapter(adapter);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}