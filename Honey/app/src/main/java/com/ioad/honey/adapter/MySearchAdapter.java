package com.ioad.honey.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ioad.honey.R;
import com.ioad.honey.bean.Cart;
import com.ioad.honey.bean.Search;
import com.ioad.honey.common.DBHelper;

import java.util.ArrayList;

public class MySearchAdapter extends BaseAdapter {

    private Context mContext;
    private int layout = 0;
    private LayoutInflater inflater;
    private ArrayList<Search> searches;
    private DBHelper helper;

    public MySearchAdapter(Context mContext, int layout, ArrayList<Search> searches) {
        this.mContext = mContext;
        this.layout = layout;
        this.searches = searches;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.helper = new DBHelper(mContext);
    }

    @Override
    public int getCount() {
        return searches.size();
    }

    @Override
    public Object getItem(int position) {
        return searches.get(position).getSearchSeq();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        view = inflater.inflate(this.layout, viewGroup, false);
        TextView tvMySearchWord = view.findViewById(R.id.tv_my_search_word);
        TextView tvMySearchDate = view.findViewById(R.id.tv_my_search_date);
        Button btnMySearchDel = view.findViewById(R.id.btn_my_search_del);

        tvMySearchWord.setText(searches.get(position).getSearchKeyword());
        tvMySearchDate.setText(searches.get(position).getSearchDate());

        btnMySearchDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.deleteSearchData("SEARCH_LIST", searches.get(position).getSearchKeyword());
                searches.remove(searches.get(position));
                notifyDataSetChanged();
            }
        });


        return view;
    }
}
