package com.ioad.honey.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ioad.honey.R;
import com.ioad.honey.activity.DaumActivity;
import com.ioad.honey.activity.SearchActivity;
import com.ioad.honey.bean.UserInfo;
import com.ioad.honey.common.DBHelper;
import com.ioad.honey.common.Util;
import com.ioad.honey.task.AddressNetworkTask;

import java.util.ArrayList;

public class AddrBottomSheet extends BottomSheetDialogFragment {


    Button btnMainAddrSearch;
    ListView lvMainAddrList;
    private DBHelper helper;
    private Cursor cursor;
    private ArrayList<UserInfo> userInfos;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addr_bottom_sheet, container, false);

        btnMainAddrSearch = view.findViewById(R.id.btn_main_addr_search);
        lvMainAddrList = view.findViewById(R.id.lv_main_addr_list);

        btnMainAddrSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int status = AddressNetworkTask.getConnectivityStatus(getActivity());
                if (status == AddressNetworkTask.TYPE_MOBILE || status == AddressNetworkTask.TYPE_WIFI) {
                    Intent intent = new Intent(getActivity(), DaumActivity.class);
                    startActivity(intent);
                } else {
                    Util.showToast(getActivity(), "인터넷 연결을 확인해주세요");
                }
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void getMyAddressList() {
        cursor = helper.selectAddressData("DELIVERY_ADDR");
        userInfos.clear();
        while (cursor.moveToNext()) {
            String addr = cursor.getString(0);
            String addrDetail = cursor.getString(1);
            String date = cursor.getString(2);

            UserInfo userInfo = new UserInfo(addr, addrDetail, date);
            userInfos.add(userInfo);
        }
    }
}