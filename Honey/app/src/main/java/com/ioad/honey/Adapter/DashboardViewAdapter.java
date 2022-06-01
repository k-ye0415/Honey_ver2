package com.ioad.honey.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.ioad.honey.R;
import com.ioad.honey.Task.ImageLoadTask;
import com.ioad.honey.common.Constant;

public class DashboardViewAdapter extends PagerAdapter {

    private Context mContext;
    private String[] imageURL;
    ImageView iv_dashboard;

    public DashboardViewAdapter(Context mContext, String[] namesArr) {
        this.mContext = mContext;
        this.imageURL = namesArr;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = null;
        if (mContext != null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dashborad_page, container, false);

            iv_dashboard = view.findViewById(R.id.iv_dashboard);
            getDashboardImage(imageURL[position]);
        }

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return imageURL.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void getDashboardImage(String url) {
        String imageURL = url;
        ImageLoadTask task = new ImageLoadTask(imageURL, iv_dashboard);
        task.execute();
    }

}
