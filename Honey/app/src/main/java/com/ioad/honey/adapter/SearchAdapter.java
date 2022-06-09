package com.ioad.honey.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ioad.honey.R;
import com.ioad.honey.bean.Search;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter {

    private final String TAG = getClass().getSimpleName();
    private Context mContext = null;
    private int layout = 0;
    private ArrayList<Search> searches = null;
    private LayoutInflater inflater = null;

    public SearchAdapter(Context mContext, int layout, ArrayList<Search> searches) {
        this.mContext = mContext;
        this.layout = layout;
        this.searches = searches;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return searches.size();
    }

    @Override
    public Object getItem(int position) {
        return searches.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        view = inflater.inflate(this.layout, viewGroup, false);
        ImageView ivSearchImg = view.findViewById(R.id.iv_search_img);
        TextView tvSearchMenu = view.findViewById(R.id.tv_search_menu);

        tvSearchMenu.setText(searches.get(position).getSearchKeyword());

        return view;
    }
}
