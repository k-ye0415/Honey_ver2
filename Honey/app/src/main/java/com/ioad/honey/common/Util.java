package com.ioad.honey.common;

import android.content.Context;
import android.widget.Toast;

public class Util {
    Context mContext;


    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
