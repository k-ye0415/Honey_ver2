package com.ioad.honey.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ioad.honey.Adapter.DashboardViewAdapter;
import com.ioad.honey.R;
import com.ioad.honey.Task.ImageLoadTask;
import com.ioad.honey.common.Constant;

import java.util.Timer;
import java.util.TimerTask;


public class DashboardFragment extends Fragment {

    private ViewPager viewPager;
    private DashboardViewAdapter adapter;
    private ImageLoadTask task;

    String[] imageURL = {
            Constant.SERVER_URL_IMG +  "dashboard-1.png",
            Constant.SERVER_URL_IMG +  "dashboard-2.png",
            Constant.SERVER_URL_IMG +  "dashboard-3.png"
    };

    ImageView iv_main_1, iv_main_week, iv_main_week_1, iv_main_week_2, iv_main_week_3, iv_main_event;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 1500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        iv_main_1 = view.findViewById(R.id.iv_main_1);
        iv_main_week = view.findViewById(R.id.iv_main_week);
        iv_main_week_1 = view.findViewById(R.id.iv_main_week_1);
        iv_main_week_2 = view.findViewById(R.id.iv_main_week_2);
        iv_main_week_3 = view.findViewById(R.id.iv_main_week_3);
        iv_main_event = view.findViewById(R.id.iv_main_event);
        viewPager = view.findViewById(R.id.viewPager);
        adapter = new DashboardViewAdapter(getActivity(), imageURL);
        viewPager.setAdapter(adapter);

        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentPage == 3) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_MS, PERIOD_MS);

        getMainImage("mainimg-1.png", iv_main_1);
        getMainImage("main_week.png", iv_main_week);
        getMainImage("week_1.png", iv_main_week_1);
        getMainImage("week_2.png", iv_main_week_2);
        getMainImage("week_3.png", iv_main_week_3);
        getMainImage("google_map_event.png", iv_main_event);

        return view;
    }

    public void getMainImage(String imageNm, ImageView imageView) {
        String url = Constant.SERVER_URL_IMG + imageNm;
        task = new ImageLoadTask(url, imageView);
        task.execute();
    }

}