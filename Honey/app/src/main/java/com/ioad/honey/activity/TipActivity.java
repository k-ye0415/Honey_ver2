package com.ioad.honey.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ioad.honey.adapter.TipAdapter;
import com.ioad.honey.bean.Tip;
import com.ioad.honey.R;
import com.ioad.honey.common.BaseActivity;
import com.ioad.honey.common.Shared;
import com.ioad.honey.common.Util;
import com.ioad.honey.task.ImageLoadTask;
import com.ioad.honey.task.InsertNetworkTask;
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
    private Button btnTipAdd;

    private EditText etTipContent;
    private Button btnTipAddCancel, btnTipAddInsert;

    private ImageLoadTask task;
    private String selectCode;
    private String selectName;
    private String userId, strUrl;
    private ArrayList<Tip> tips;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        mContext = TipActivity.this;

        ivTipMenu = findViewById(R.id.iv_tip_menu);
        ivTipTitle = findViewById(R.id.iv_tip_title);
        tvTipName = findViewById(R.id.tv_tip_name);
        rvTipList = findViewById(R.id.rv_tip_list);
        btnTipAdd = findViewById(R.id.btn_tip_add);

        userId = Shared.getStringPref(mContext, "USER_ID");

        // ???????????????
        Intent intent = getIntent();
        selectCode = intent.getStringExtra("mCode");
        selectName = intent.getStringExtra("mName");

        setActivityView();

        btnTipAdd.setOnClickListener(btnOnClickListener);


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


    View.OnClickListener btnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (userId.length() == 0) {
                Util.showToast(mContext, "????????? ????????????!");
            } else {
                dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_layout);


                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

                Window window = dialog.getWindow();
                window.setAttributes(layoutParams);
                dialog.show();

                etTipContent = dialog.findViewById(R.id.et_tip_content);
                btnTipAddCancel = dialog.findViewById(R.id.btn_tip_add_cancel);
                btnTipAddInsert = dialog.findViewById(R.id.btn_tip_add_insert);

                btnTipAddCancel.setOnClickListener(dialogBtnOnClickListener);
                btnTipAddInsert.setOnClickListener(dialogBtnOnClickListener);



            }
        }
    };


    View.OnClickListener dialogBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_tip_add_cancel:
                    dialog.dismiss();
                    break;
                case R.id.btn_tip_add_insert:
                    String result = insertAsyncData();
                    if (result.equals("1")) {
                        Util.showToast(mContext, "?????? ????????????");
                    } else {
                        Util.showToast(mContext, "?????????????????? ????????? ??? ?????????");
                    }

                    dialog.dismiss();
                    onResume();
                    break;
            }
        }
    };

    @Override
    public String insertAsyncData() {
        String result = null;
        String content = etTipContent.getText().toString();
        if (content != null) {
            try {
                strUrl = Constant.SERVER_URL_JSP + "tip_insert.jsp?mCode=" + selectCode + "&cId=" + userId + "&tipContent=" + content;
                InsertNetworkTask task = new InsertNetworkTask(mContext, strUrl, "insert");
                Object obj = task.execute().get();
                result = (String) obj;


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
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