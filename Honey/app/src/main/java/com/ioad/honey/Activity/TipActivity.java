package com.ioad.honey.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ioad.honey.Adapter.TipAdapter;
import com.ioad.honey.Bean.Tip;
import com.ioad.honey.R;
import com.ioad.honey.Task.ImageLoadTask;
import com.ioad.honey.Task.SelectNetworkTask;
import com.ioad.honey.common.Constant;

import java.util.ArrayList;

public class TipActivity extends AppCompatActivity {

    ImageView iv_tip_menu, iv_tip_title;
    TextView tv_tip_name;
    RecyclerView rv_tip_list;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    Button btn_tip_add;

    ImageLoadTask task;
    String selectCode;
    String selectName;
    String strUrl;
    ArrayList<Tip> tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        iv_tip_menu = findViewById(R.id.iv_tip_menu);
        iv_tip_title = findViewById(R.id.iv_tip_title);
        tv_tip_name = findViewById(R.id.tv_tip_name);
        rv_tip_list = findViewById(R.id.rv_tip_list);
        btn_tip_add = findViewById(R.id.btn_tip_add);

        // 값받아오기
        Intent intent = getIntent();
        selectCode = intent.getStringExtra("mCode");
        selectName = intent.getStringExtra("mName");

        tv_tip_name.setText(selectName);
        getImage(selectCode, iv_tip_menu);
        getImage(null, iv_tip_title);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getTipList();
    }

    public void getImage(String imageCode, ImageView imageView) {
        String url = "";
        if (imageCode != null) {
            url = Constant.SERVER_URL_IMG + "foodcode" + imageCode + ".png";
        } else {
            url = Constant.SERVER_URL_IMG + "tip_title.png";
        }
        task = new ImageLoadTask(url, imageView);
        task.execute();
    }


    private void getTipList() {
        strUrl = Constant.SERVER_URL_JSP + "tip_select.jsp?mCode=" + selectCode;
        try {
            SelectNetworkTask networkTask = new SelectNetworkTask(TipActivity.this, strUrl, "select", "tip");
            Object obj = networkTask.execute().get();
            tips = (ArrayList<Tip>) obj;
            layoutManager = new LinearLayoutManager(TipActivity.this);
            rv_tip_list.setLayoutManager(layoutManager);

            adapter = new TipAdapter(TipActivity.this, R.layout.tip_item, tips);
            rv_tip_list.setAdapter(adapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}