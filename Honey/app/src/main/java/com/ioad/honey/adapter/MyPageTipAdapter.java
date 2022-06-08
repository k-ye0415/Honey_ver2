package com.ioad.honey.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ioad.honey.R;
import com.ioad.honey.bean.Tip;

import java.util.ArrayList;

public class MyPageTipAdapter extends BaseAdapter {

    private Context mContext;
    private int layout = 0;
    private ArrayList<Tip> tips = null;
    private LayoutInflater inflater = null;

    public MyPageTipAdapter(Context mContext, int layout, ArrayList<Tip> tips) {
        this.mContext = mContext;
        this.layout = layout;
        this.tips = tips;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return tips.size();
    }

    @Override
    public Object getItem(int position) {
        return tips.get(position).getcId();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = inflater.inflate(this.layout, viewGroup, false);

        TextView tvMyTipNm = view.findViewById(R.id.tv_my_tip_name);
        TextView tvMyTipContent = view.findViewById(R.id.tv_my_tip_content);
        TextView tvMyTipDay = view.findViewById(R.id.tv_my_tip_day);

        tvMyTipNm.setText(tips.get(position).getMenuName());
        tvMyTipContent.setText(tips.get(position).getTipContent());
        tvMyTipDay.setText(tips.get(position).getTipAddDay());

        return view;
    }
}
