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
import com.ioad.honey.common.Util;
import com.ioad.honey.task.InsertNetworkTask;
import com.ioad.honey.common.Constant;
import com.ioad.honey.common.Shared;

public class JoinElseFragment extends Fragment {

    EditText etJoinName, etJoinPhone, etJoinEmail;
    Button btnJoinFinish;
    String joinId, joinPw, joinAddr, joinAddrDetail, joinName, joinPhone, joinEmail;
    String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join_else, container, false);

        etJoinName = view.findViewById(R.id.et_join_name);
        etJoinPhone = view.findViewById(R.id.et_join_phone);
        etJoinEmail = view.findViewById(R.id.et_join_email);
        btnJoinFinish = view.findViewById(R.id.btn_join_finish);

        btnJoinFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinName = etJoinName.getText().toString().trim();
                joinPhone = etJoinPhone.getText().toString().trim();
                joinEmail = etJoinEmail.getText().toString().trim();

                if (joinName.equals("") || joinPhone.equals("") || joinEmail.equals("")) {
//                    Toast.makeText(getActivity(), R.string.else_empty_check, Toast.LENGTH_SHORT).show();
                    Util.showToast(getActivity(), "이름, 전화번호, 이메일을 입력해주세요");
                } else {
                    joinId = Shared.getStringPref(getActivity(), "JOIN_ID");
                    joinPw = Shared.getStringPref(getActivity(), "JOIN_PW");
                    joinAddr = Shared.getStringPref(getActivity(), "JOIN_ADDR");
                    joinAddrDetail = Shared.getStringPref(getActivity(), "JOIN_ADDR_DETAIL");
                    //String urlAddr = "http://"+macIP+":8080/honey/honey_signup_j.jsp?";  //회원가입 완료

                    Log.d("TAG", "join ----------- ");
                    Log.d("TAG", "joinId : " + joinId);
                    Log.d("TAG", "joinPw : " + joinPw);
                    Log.d("TAG", "joinAddr : " + joinAddr);
                    Log.d("TAG", "joinAddrDetail : " + joinAddrDetail);
                    Log.d("TAG", "joinName : " + joinName);
                    Log.d("TAG", "joinPhone : " + joinPhone);
                    Log.d("TAG", "joinEmail : " + joinEmail);
                    Log.d("TAG", "join ----------- ");

                    url = Constant.SERVER_IP + "honey/honey_signup_j.jsp?cId=" + joinId
                            + "&cPw=" + joinPw + "&cName=" + joinName + "&cTelno=" + joinPhone
                            + "&cEmail=" + joinEmail + "&cAddress1=" + joinAddr+ "&cAddress2=" + joinAddrDetail+ "&cPostNum=" + null;
                    Log.e("TAG", "joinURL : " + url);
                    String result = insertJoinUser();
                    if (result.equals("1")) {
//                        Toast.makeText(getActivity(), R.string.welcome, Toast.LENGTH_SHORT).show();
                        Util.showToast(getActivity(),"환영합니다");
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
                        Util.showToast(getActivity(), "오류로 인해 다시 시도해주세요");
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
        });


        return view;
    }


    private String insertJoinUser() {
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

}