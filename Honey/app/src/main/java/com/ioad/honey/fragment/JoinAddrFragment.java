package com.ioad.honey.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ioad.honey.activity.JoinAddrActivity;
import com.ioad.honey.R;
import com.ioad.honey.task.AddressNetworkTask;

public class JoinAddrFragment extends Fragment {

    Button btnAddrSearch;
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join_addr, container, false);

        if (getArguments() != null ) {
            String joinId = getArguments().getString("JOIN_ID");
            String joinPw = getArguments().getString("JOIN_PW");
        }

        btnAddrSearch = view.findViewById(R.id.btn_addr_search);

        btnAddrSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int status = AddressNetworkTask.getConnectivityStatus(getActivity());
                if (status == AddressNetworkTask.TYPE_MOBILE || status == AddressNetworkTask.TYPE_WIFI) {
                    Intent intent = new Intent(getActivity(), JoinAddrActivity.class);
                    startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY);
                } else {
                    Toast.makeText(getActivity(), "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        Log.e("TAG", data);
                    }
                }
        }
    }
}