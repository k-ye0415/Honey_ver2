package com.ioad.honey.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ioad.honey.activity.JoinActivity;
import com.ioad.honey.activity.JoinAddrActivity;
import com.ioad.honey.bean.UserInfo;
import com.ioad.honey.R;
import com.ioad.honey.common.Util;
import com.ioad.honey.task.LoginNetworkTask;
import com.ioad.honey.common.Constant;
import com.ioad.honey.common.Shared;

import java.util.ArrayList;

public class JoinFragment extends Fragment {

    Button btn_join_back, btn_next;
    EditText etJoinId, etJoinPw;
    JoinActivity joinActivity;
    ArrayList<UserInfo> userInfos;

    String joinId, joinPw;
    String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join, container, false);

        etJoinId = view.findViewById(R.id.et_join_id);
        etJoinPw = view.findViewById(R.id.et_join_pw);

        btn_next = view.findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinId = etJoinId.getText().toString().trim();
                joinPw = etJoinPw.getText().toString().trim();
                if (joinId.equals("")) {
//                    Toast.makeText(getActivity(), R.string.check_id, Toast.LENGTH_SHORT).show();
                    Util.showToast(getActivity(), "아이디를 입력해주세요");
                } else if (joinPw.equals("")) {
//                    Toast.makeText(getActivity(), R.string.check_pw, Toast.LENGTH_SHORT).show();
                    Util.showToast(getActivity(), "비밀번호를 입력해주세요");
                } else if (!joinId.equals("") && !joinPw.equals("")) {
                    url = Constant.SERVER_IP + "honey/honey_login_confirm_j.jsp?cId=" + joinId + "&cPw=" + joinPw;
                    userInfos = new ArrayList<>();

                    checkJoin();
                }

            }
        });

        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        joinActivity = (JoinActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        joinActivity = null;
    }


    private void checkJoin() {
        try {
            LoginNetworkTask task = new LoginNetworkTask(getActivity(), url, "login", "login");
            Object obj = task.execute().get();
            userInfos = (ArrayList<UserInfo>) obj;

            if (userInfos != null) {
//                Toast.makeText(getActivity(), R.string.overlap_id, Toast.LENGTH_SHORT).show();
                Util.showToast(getActivity(), "사용중인 아이디입니다");
            } else {
                Intent intent = new Intent(getActivity(), JoinAddrActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                intent.putExtra("JOIN_ID", etJoinId.getText().toString());
//                intent.putExtra("JOIN_PW", etJoinPw.getText().toString());
                Shared.setStringPrf(getActivity(), "JOIN_ID", etJoinId.getText().toString());
                Shared.setStringPrf(getActivity(), "JOIN_PW", etJoinPw.getText().toString());
                startActivity(intent);
//                Bundle bundle = new Bundle();
//                bundle.putString("JOIN_ID", etJoinId.getText().toString());
//                bundle.putString("JOIN_PW", etJoinPw.getText().toString());
//
//                JoinAddrFragment joinAddrFragment = new JoinAddrFragment();
//                joinAddrFragment.setArguments(bundle);
//
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frg_join, joinAddrFragment);
////                transaction.remove(this);
//                transaction.commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}