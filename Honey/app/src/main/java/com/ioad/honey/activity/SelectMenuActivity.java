package com.ioad.honey.activity;

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
import android.widget.Toast;

import com.ioad.honey.adapter.IngredientAdapter;
import com.ioad.honey.bean.Ingredient;
import com.ioad.honey.R;
import com.ioad.honey.common.Util;
import com.ioad.honey.task.ImageLoadTask;
import com.ioad.honey.task.InsertNetworkTask;
import com.ioad.honey.task.SelectNetworkTask;
import com.ioad.honey.common.Constant;
import com.ioad.honey.common.Shared;

import java.util.ArrayList;

public class SelectMenuActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private ImageView ivSelectNenu;
    private TextView tvSelectName, tvTipCount, tvTip;
    private RecyclerView rvSelectMenu;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private Button btnCart;

    private String selectCode, selectName, tipCount;
    private ImageLoadTask task;
    private ArrayList<Ingredient> ingredients;

    private String url;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_menu);

        userId = Shared.getStringPref(SelectMenuActivity.this, "USER_ID");

        ivSelectNenu = findViewById(R.id.iv_select_menu);
        tvSelectName = findViewById(R.id.tv_select_name);
        tvTipCount = findViewById(R.id.tv_tip_count);
        tvTip = findViewById(R.id.tv_tip);
        rvSelectMenu = findViewById(R.id.rv_select_menu);
        btnCart = findViewById(R.id.btn_cart);


        Intent intent = getIntent();
        selectCode = intent.getStringExtra("mCode");
        selectName = intent.getStringExtra("mName");

        getSelectImage(selectCode, ivSelectNenu);
        tvSelectName.setText(selectName);

        tipCount = tipCount();
        tvTipCount.setText("꿀팁 " + tipCount + "건");
        tvTip.setOnClickListener(tipOnClickListener);
        btnCart.setOnClickListener(cartOnClickListener);
    }


    @Override
    protected void onResume() {
        super.onResume();
        tipCount = tipCount();
        tvTipCount.setText("꿀팁 " + tipCount + "건");

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
            String result = null;
            ArrayList<String> codeArr = new ArrayList<>();
            ArrayList<String> nameArr = new ArrayList<>();
            codeArr = Shared.getStringArrayPref(SelectMenuActivity.this, "SELECT_CODE");
            nameArr = Shared.getStringArrayPref(SelectMenuActivity.this, "SELECT_NAME");

            for (int i = 0; i < codeArr.size(); i++) {
                url = Constant.SERVER_URL_JSP + "ingredient_cart_insert.jsp?id=" + userId + "&iCode=" + codeArr.get(i) + "&mCode=" + selectCode;
                Log.d(TAG, "cart Insert url : " + url);
                result = insertCart();
            }

            if (result.equals("1")) {
                if (codeArr.size() == 1) {
//                    Toast.makeText(SelectMenuActivity.this, "장바구니 담기 완료", Toast.LENGTH_SHORT).show();
                    Util.showToast(SelectMenuActivity.this, "장바구니 담기 완료");
                } else {
//                    Toast.makeText(SelectMenuActivity.this, nameArr.get(0) + "외 " + codeArr.size() + "장바구니 담기 완료", Toast.LENGTH_SHORT).show();
                    Util.showToast(SelectMenuActivity.this, nameArr.get(0) + "외 " + codeArr.size() + "장바구니 담기 완료");
                }
                Shared.removeStringArrayPref(SelectMenuActivity.this, "SELECT_CODE");
                Shared.removeStringArrayPref(SelectMenuActivity.this, "SELECT_NAME");
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
            rvSelectMenu.setLayoutManager(layoutManager);

            adapter = new IngredientAdapter(this, R.layout.ingredient_list_layout, ingredients);
            rvSelectMenu.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String insertCart() {
        String result = null;
        try {
            InsertNetworkTask task = new InsertNetworkTask(SelectMenuActivity.this, url, "insert");
            Object obj = task.execute().get();
            result = (String) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}