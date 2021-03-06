package com.ioad.honey.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ioad.honey.activity.MainCategoryActivity;
import com.ioad.honey.R;
import com.ioad.honey.common.BaseFragment;
import com.ioad.honey.common.DBHelper;
import com.ioad.honey.common.Util;
import com.ioad.honey.task.InsertNetworkTask;
import com.ioad.honey.common.Constant;
import com.ioad.honey.common.Shared;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JoinElseFragment extends BaseFragment {

    private final String TAG = getClass().getSimpleName();
    private EditText etJoinName, etJoinPhone, etJoinEmail;
    private Button btnJoinFinish;
    private String joinId, joinPw, joinAddr, joinAddrDetail, joinName, joinPhone, joinEmail;
    private String url;
    private DBHelper helper;
    private SimpleDateFormat dateFormat = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join_else, container, false);

        etJoinName = view.findViewById(R.id.et_join_name);
        etJoinPhone = view.findViewById(R.id.et_join_phone);
        etJoinEmail = view.findViewById(R.id.et_join_email);
        btnJoinFinish = view.findViewById(R.id.btn_join_finish);

        btnJoinFinish.setOnClickListener(btnOnClickListener);


        return view;
    }

    @Override
    public String insertAsyncData() {
        String result = null;
        try {
            InsertNetworkTask task = new InsertNetworkTask(getActivity(), url, "insert");
            Object obj = task.execute().get();
            result = (String)obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

//    private String insertJoinUser() {
//        String result = null;
//        try {
//            InsertNetworkTask task = new InsertNetworkTask(getActivity(), url, "insert");
//            Object obj = task.execute().get();
//            result = (String)obj;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

    View.OnClickListener btnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            joinName = etJoinName.getText().toString().trim();
            joinPhone = etJoinPhone.getText().toString().trim();
            joinEmail = etJoinEmail.getText().toString().trim();

            if (joinName.equals("") || joinPhone.equals("") || joinEmail.equals("")) {
//                    Toast.makeText(getActivity(), R.string.else_empty_check, Toast.LENGTH_SHORT).show();
                Util.showToast(getActivity(), "??????, ????????????, ???????????? ??????????????????");
            } else {
                joinId = Shared.getStringPref(getActivity(), "JOIN_ID");
                joinPw = Shared.getStringPref(getActivity(), "JOIN_PW");
                joinAddr = Shared.getStringPref(getActivity(), "JOIN_ADDR");
                joinAddrDetail = Shared.getStringPref(getActivity(), "JOIN_ADDR_DETAIL");

                url = Constant.SERVER_IP + "honey/honey_signup_j.jsp?cId=" + joinId
                        + "&cPw=" + joinPw + "&cName=" + joinName + "&cTelno=" + joinPhone
                        + "&cEmail=" + joinEmail + "&cAddress1=" + joinAddr+ "&cAddress2=" + joinAddrDetail+ "&cPostNum=" + null;
                Log.e(TAG, "joinURL : " + url);
                String result = insertAsyncData();
                if (result.equals("1")) {
//                        Toast.makeText(getActivity(), R.string.welcome, Toast.LENGTH_SHORT).show();
                    Util.showToast(getActivity(),"???????????????");
                    String date = Util.currentTime();
                    helper.insertAddressData("DELIVERY_ADDR", joinAddr, joinAddrDetail, date);
                    Shared.removeStringPrf(getActivity(), "JOIN_ID");
                    Shared.removeStringPrf(getActivity(), "JOIN_PW");
                    Shared.removeStringPrf(getActivity(), "JOIN_ADDR");
                    Shared.removeStringPrf(getActivity(), "JOIN_ADDR_DETAIL");
                    Shared.removeStringPrf(getActivity(), "USER_ID");
                    Shared.setStringPrf(getActivity(), "USER_ID", joinId);
                    Intent intent = new Intent(getActivity(), MainCategoryActivity.class);
                    startActivity(intent);
                } else {
//                        Toast.makeText(getActivity(), R.string.join_false, Toast.LENGTH_SHORT).show();
                    Util.showToast(getActivity(), "????????? ?????? ?????? ??????????????????");
                    Shared.removeStringPrf(getActivity(), "JOIN_ID");
                    Shared.removeStringPrf(getActivity(), "JOIN_PW");
                    Shared.removeStringPrf(getActivity(), "JOIN_ADDR");
                    Shared.removeStringPrf(getActivity(), "JOIN_ADDR_DETAIL");
                    Shared.removeStringPrf(getActivity(), "USER_ID");
                    Intent intent = new Intent(getActivity(), MainCategoryActivity.class);
                    startActivity(intent);
                }
            }
        }
    };

}