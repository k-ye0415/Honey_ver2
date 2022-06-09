package com.ioad.honey.common;

import android.util.Log;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    public void selectAsyncData(String url){
        Log.d("INSERT_URL", url);
    }
    public String insertAsyncData() {
        String result = null;
        return result;
    }

    public String updateAsyncData() {
        String result = null;
        return result;
    }

    public String deleteAsyncData() {
        String result = null;
        return result;
    }
    public void imageAsync(String imageCode, ImageView imageView){}


}
