package com.ioad.honey.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ioad.honey.Adapter.IngredientAdapter;
import com.ioad.honey.Adapter.MenuRecyclerAdapter;
import com.ioad.honey.Bean.Ingredient;
import com.ioad.honey.R;
import com.ioad.honey.Task.ImageLoadTask;
import com.ioad.honey.Task.SelectNetworkTask;
import com.ioad.honey.common.Constant;
import com.ioad.honey.common.Shared;

import java.util.ArrayList;
import java.util.Arrays;

public class SelectMenuActivity extends AppCompatActivity {

    ImageView iv_select_menu;
    TextView tv_select_name, tv_tip_count, tv_tip;
    RecyclerView rv_select_menu;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    Button btn_cart;

    String selectCode, selectName, tipCount;
    ImageLoadTask task;
    ArrayList<Ingredient> ingredients;

    SharedPreferences preferences;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_menu);

        iv_select_menu = findViewById(R.id.iv_select_menu);
        tv_select_name = findViewById(R.id.tv_select_name);
        tv_tip_count = findViewById(R.id.tv_tip_count);
        tv_tip = findViewById(R.id.tv_tip);
        rv_select_menu = findViewById(R.id.rv_select_menu);
        btn_cart = findViewById(R.id.btn_cart);


        Intent intent = getIntent();
        selectCode = intent.getStringExtra("mCode");
        selectName = intent.getStringExtra("mName");

        getSelectImage(selectCode, iv_select_menu);
        tv_select_name.setText(selectName);

        tipCount = tipCount();
        tv_tip_count.setText("꿀팁 " + tipCount + "건");
        tv_tip.setOnClickListener(tipOnClickListener);
        btn_cart.setOnClickListener(cartOnClickListener);
    }


    @Override
    protected void onResume() {
        super.onResume();
        tipCount = tipCount();
        tv_tip_count.setText("꿀팁 " + tipCount + "건");

        getListData();
    }

    View.OnClickListener tipOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(SelectMenuActivity.this, TipActivity.class);
            intent.putExtra("mCode", selectCode);
            intent.putExtra("mName", selectName);
            startActivity(intent);
        }
    };

    View.OnClickListener cartOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ArrayList<String> codeArr = new ArrayList<>();
            ArrayList<String> nameArr = new ArrayList<>();
            codeArr = Shared.getStringArrayPref(SelectMenuActivity.this, "SELECT_CODE");
            nameArr = Shared.getStringArrayPref(SelectMenuActivity.this, "SELECT_NAME");
            // Login 부터하자
            for (int i = 0; i < codeArr.size(); i++) {
                url = Constant.SERVER_URL_JSP + "ingredient_cart_insert.jsp?id=";
            }

        }
    };


    public void getSelectImage(String imageCode, ImageView imageView) {
        String url = Constant.SERVER_URL_IMG + "foodcode" + imageCode + "-1.png";
        task = new ImageLoadTask(url, imageView);
        task.execute();
    }


    private String tipCount(){
        String count = null;
        url = Constant.SERVER_URL_JSP + "tip_count_select.jsp?code=" + selectCode;
        Log.e("TAG", "url :::::::: " + url);
        try {
            SelectNetworkTask networkTask = new SelectNetworkTask(SelectMenuActivity.this, url, "count", "tip");
            Object obj = networkTask.execute().get();

            count = (String) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    private void getListData() {
        url = Constant.SERVER_URL_JSP + "menu_select_ingredient.jsp?code=" + selectCode;
        try {
            SelectNetworkTask networkTask = new SelectNetworkTask(this, url, "select", "ingredient");
            Object obj = networkTask.execute().get();
            ingredients = (ArrayList<Ingredient>) obj;

            layoutManager = new LinearLayoutManager(this);
            rv_select_menu.setLayoutManager(layoutManager);

            adapter = new IngredientAdapter(this, R.layout.ingredient_list_layout, ingredients);
            rv_select_menu.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}