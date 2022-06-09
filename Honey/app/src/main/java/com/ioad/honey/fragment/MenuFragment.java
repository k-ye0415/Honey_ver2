package com.ioad.honey.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ioad.honey.adapter.MenuAdapter;
import com.ioad.honey.bean.Menu;
import com.ioad.honey.R;
import com.ioad.honey.task.ImageLoadTask;
import com.ioad.honey.task.SelectNetworkTask;
import com.ioad.honey.common.Constant;

import java.util.ArrayList;

public class MenuFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private ImageLoadTask task;
    private ImageView iv_menu;
    private RecyclerView rv_menu;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<Menu> menus;
    private String name;
    private String JSPUrl;

    public MenuFragment(String imageName, String jspURL){
        this.name = imageName;
        this.JSPUrl = Constant.SERVER_URL_JSP + jspURL;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        iv_menu = view.findViewById(R.id.iv_menu);
        rv_menu = view.findViewById(R.id.rv_menu);

        getKoreanImage(name, iv_menu);
        getListData();

        return view;
    }

    public void getKoreanImage(String imageNm, ImageView imageView) {
        String url = Constant.SERVER_URL_IMG + imageNm;
        task = new ImageLoadTask(url, imageView);
        task.execute();
    }

    private void getListData() {
        try {
            SelectNetworkTask networkTask = new SelectNetworkTask(getActivity(), JSPUrl, "select", "food");
            Object obj = networkTask.execute().get();
            menus = (ArrayList<Menu>) obj;

            layoutManager = new LinearLayoutManager(getActivity());
            rv_menu.setLayoutManager(layoutManager);

            adapter = new MenuAdapter(getActivity(), R.layout.menu_layout, menus);
            rv_menu.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}