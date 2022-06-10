package com.ioad.honey.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ioad.honey.R;
import com.ioad.honey.bean.UserInfo;

import java.util.ArrayList;

public class AddressAdapter extends BaseAdapter {


    Context mContext;
    int layout = 0;
    LayoutInflater inflater;
    ArrayList<UserInfo> address;
    int selectIndex;


    public AddressAdapter(Context mContext, int layout, ArrayList<UserInfo> address) {
        this.mContext = mContext;
        this.layout = layout;
        this.address = address;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.selectIndex = 0;
    }

    @Override
    public int getCount() {
        return address.size();
    }

    @Override
    public Object getItem(int position) {
        return address.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        view = inflater.inflate(this.layout, viewGroup, false);

        TextView tvAddress = view.findViewById(R.id.tv_address);
        ImageView ivAddrCheck = view.findViewById(R.id.iv_address_check);

        if (selectIndex == position) {
            tvAddress.setText(address.get(position).getUserAddr());
            ivAddrCheck.setVisibility(View.VISIBLE);
            tvAddress.setTextColor(Color.parseColor("#000000"));
            tvAddress.setTypeface(null, Typeface.BOLD);
        } else {
            tvAddress.setText(address.get(position).getUserAddr());
            tvAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectIndex = position;
                    notifyDataSetChanged();
                }
            });
        }


//        if (address.size() != 0) {
//            if (selectIndex == 0) {
//                tvAddress.setText(address.get(position).getUserAddr());
//                ivAddrCheck.setVisibility(View.VISIBLE);
//                tvAddress.setTextColor(Color.parseColor("#000000"));
//                tvAddress.setTypeface(null, Typeface.BOLD);
//            } else {
//                tvAddress.setText(address.get(position).getUserAddr());
//                tvAddress.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        selectIndex = position;
//                        notifyDataSetChanged();
//                    }
//                });
//            }
//        } else {
//
//        }

        return view;
    }
}
