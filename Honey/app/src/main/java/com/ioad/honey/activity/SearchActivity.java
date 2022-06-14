package com.ioad.honey.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.ioad.honey.R;
import com.ioad.honey.adapter.MySearchAdapter;
import com.ioad.honey.adapter.SearchAdapter;
import com.ioad.honey.bean.Search;
import com.ioad.honey.common.BaseActivity;
import com.ioad.honey.common.Constant;
import com.ioad.honey.common.DBHelper;
import com.ioad.honey.common.Util;
import com.ioad.honey.task.SelectNetworkTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class SearchActivity extends BaseActivity {

    private final String TAG = getClass().getSimpleName();
    private Context mContext;

    private EditText etSearch;
    private Button btnSearch;
    private LinearLayout llMySearch, llSearchList;
    private ListView lvMySearch, lvSearchList;

    private long now = 0;
    private Date date = null;
    private SimpleDateFormat dateFormat = null;
    private DBHelper helper;
    private Cursor cursor;


    private ArrayList<Search> searches = new ArrayList<>();
    private SearchAdapter adapter;
    private MySearchAdapter mySearchAdapter;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mContext = SearchActivity.this;

        etSearch = findViewById(R.id.et_search);
        btnSearch = findViewById(R.id.btn_search);
        llMySearch = findViewById(R.id.ll_my_search);
        llSearchList = findViewById(R.id.ll_search_list);
        lvMySearch = findViewById(R.id.lv_my_search);
        lvSearchList = findViewById(R.id.lv_search_list);

        helper = new DBHelper(mContext);

        llMySearch.setVisibility(View.VISIBLE);
        llSearchList.setVisibility(View.INVISIBLE);

        lvSearchList.setOnItemClickListener(itemClickListener);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                llMySearch.setVisibility(View.VISIBLE);
                llSearchList.setVisibility(View.INVISIBLE);
            }
        });

        etSearch.setOnClickListener(onClickListener);
        btnSearch.setOnClickListener(onClickListener);


    }

    @Override
    protected void onResume() {
        super.onResume();
        selectSQLite();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.et_search:
                    llMySearch.setVisibility(View.VISIBLE);
                    llSearchList.setVisibility(View.INVISIBLE);
                    break;
                case R.id.btn_search:
                    llMySearch.setVisibility(View.INVISIBLE);
                    llSearchList.setVisibility(View.VISIBLE);
                    insertSQLite();
                    selectAsyncData();
                    break;
            }
        }
    };

    @Override
    public void insertSQLite() {
        if (!etSearch.getText().toString().isEmpty()) {
            String searchDate = Util.currentTime();
            String searchKeyword = etSearch.getText().toString().trim();
            helper.insertSearchData("SEARCH_LIST", searchKeyword, searchDate);
        } else {
//            Toast.makeText(mContext, "검색어를 입력해주세요", Toast.LENGTH_SHORT).show();
            Util.showToast(mContext, "검색어를 입력해주세요");
        }
    }

    @Override
    public void selectAsyncData() {
        try {
            url = Constant.SERVER_IP + "honny_tip_m/honny_tip_kfood_Select_m.jsp?searchVlaue=" + etSearch.getText().toString();
            SelectNetworkTask task = new SelectNetworkTask(mContext, url, "select", "kfood");
            Object obj = task.execute().get();
            searches.clear();
            searches = (ArrayList<Search>) obj;

            adapter = new SearchAdapter(mContext, R.layout.search_list_item, searches);
            lvSearchList.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selectSQLite() {
        cursor = helper.selectSearchData("SEARCH_LIST");
        searches.clear();
        while (cursor.moveToNext()) {
            String seq = String.valueOf(cursor.getInt(0));
            String keyword = cursor.getString(1);
            String date = cursor.getString(2);

            Search search = new Search(seq, keyword, date);
            searches.add(search);
        }

        mySearchAdapter = new MySearchAdapter(mContext, R.layout.my_saerch_list_item, searches);
        lvMySearch.setAdapter(mySearchAdapter);
    }

    //    private void searchSave() {
//        if (!etSearch.getText().toString().isEmpty()) {
//            String searchDate = getTime();
//            String searchKeyword = etSearch.getText().toString().trim();
//            helper.insertSearchData("SEARCH_LIST", searchKeyword, searchDate);
//        } else {
////            Toast.makeText(SearchActivity.this, "검색어를 입력해주세요", Toast.LENGTH_SHORT).show();
//            Util.showToast(SearchActivity.this, "검색어를 입력해주세요");
//        }
//    }

//    private void getSearchList() {
//        try {
//            url = Constant.SERVER_IP + "honny_tip_m/honny_tip_kfood_Select_m.jsp?searchVlaue=" + etSearch.getText().toString();
//            SelectNetworkTask task = new SelectNetworkTask(SearchActivity.this, url, "select", "kfood");
//            Object obj = task.execute().get();
//            searches.clear();
//            searches = (ArrayList<Search>) obj;
//
//            adapter = new SearchAdapter(SearchActivity.this, R.layout.search_list_item, searches);
//            lvSearchList.setAdapter(adapter);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }


//    private String getTime() {
//        String result;
//        now = System.currentTimeMillis();
//        date = new Date(now);
//        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        result = dateFormat.format(date);
//        return result;
//    }

//    private void getMySearchList() {
//        cursor = helper.selectSearchData("SEARCH_LIST");
//        searches.clear();
//        while (cursor.moveToNext()) {
//            String seq = String.valueOf(cursor.getInt(0));
//            String keyword = cursor.getString(1);
//            String date = cursor.getString(2);
//
//            Search search = new Search(seq, keyword, date);
//            searches.add(search);
//        }
//
//        mySearchAdapter = new MySearchAdapter(SearchActivity.this, R.layout.my_saerch_list_item, searches);
//        lvMySearch.setAdapter(mySearchAdapter);
//
//    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Intent intent = new Intent(mContext, SelectMenuActivity.class);
            intent.putExtra("mCode", searches.get(position).getSearchSeq());
            intent.putExtra("mName", searches.get(position).getSearchKeyword());
            startActivity(intent);
        }
    };
}