package com.ioad.honey.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ioad.honey.R;
import com.ioad.honey.common.Util;

public class TOSFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private CheckBox cbFirst, cbSecond, cbThird, cbAll;
    private Button btnFirstTOS, btnSecondTOS, btnThirdTOS, btnTOSFinish;
    private LinearLayout firstTOS, secondTOS, thirdTOS;
    private boolean isFirst = true;
    private boolean isSecond = true;
    private boolean isThird = true;
    private boolean isAll = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_t_o_s, container, false);

        cbFirst = view.findViewById(R.id.cb_first);
        cbSecond = view.findViewById(R.id.cb_second);
        cbThird = view.findViewById(R.id.cb_third);
        cbAll = view.findViewById(R.id.cb_all);
        btnFirstTOS = view.findViewById(R.id.btn_first_TOS);
        btnSecondTOS = view.findViewById(R.id.btn_second_TOS);
        btnThirdTOS = view.findViewById(R.id.btn_third_TOS);
        btnTOSFinish = view.findViewById(R.id.btn_TOS_finish);
        firstTOS = view.findViewById(R.id.ll_first_TOS);
        secondTOS = view.findViewById(R.id.ll_second_TOS);
        thirdTOS = view.findViewById(R.id.ll_third_TOS);

        btnFirstTOS.setOnClickListener(btnOnClickListener);
        btnSecondTOS.setOnClickListener(btnOnClickListener);
        btnThirdTOS.setOnClickListener(btnOnClickListener);
        btnTOSFinish.setOnClickListener(btnOnClickListener);

        cbFirst.setOnCheckedChangeListener(checkBoxOnCheckedListener);
        cbSecond.setOnCheckedChangeListener(checkBoxOnCheckedListener);
        cbThird.setOnCheckedChangeListener(checkBoxOnCheckedListener);
        cbAll.setOnCheckedChangeListener(checkBoxOnCheckedListener);


        return view;
    }

    CompoundButton.OnCheckedChangeListener checkBoxOnCheckedListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {

            String text = compoundButton.getText().toString();
            if (text.contains("??????")) {
                if (isAll) {
                    cbFirst.setChecked(true);
                    cbSecond.setChecked(true);
                    cbThird.setChecked(true);
                    isAll = false;
                } else {
                    cbFirst.setChecked(false);
                    cbSecond.setChecked(false);
                    cbThird.setChecked(false);
                    isAll = true;
                }
            }


        }
    };


    View.OnClickListener btnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_first_TOS:
                    if (isFirst) {
                        firstTOS.setVisibility(View.VISIBLE);
                        isFirst = false;
                    } else {
                        firstTOS.setVisibility(View.GONE);
                        isFirst = true;
                    }
                    break;
                case R.id.btn_second_TOS:
                    if (isSecond) {
                        secondTOS.setVisibility(View.VISIBLE);
                        isSecond = false;
                    } else {
                        secondTOS.setVisibility(View.GONE);
                        isSecond = true;
                    }
                    break;
                case R.id.btn_third_TOS:
                    if (isThird) {
                        thirdTOS.setVisibility(View.VISIBLE);
                        isThird = false;
                    } else {
                        thirdTOS.setVisibility(View.GONE);
                        isThird = true;
                    }
                    break;
                case R.id.btn_TOS_finish:
                    if (cbFirst.isChecked() == false || cbSecond.isChecked() == false
                            || cbThird.isChecked() == false || cbAll.isChecked() == false) {
//                        Toast.makeText(getActivity(), R.string.agree_TOS, Toast.LENGTH_SHORT).show();
                        Util.showToast(getActivity(), "????????? ??????????????????");
                    } else {
                        JoinFragment joinFragment = new JoinFragment();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frg_join, joinFragment);
                        transaction.commit();
                    }
            }
        }
    };
}