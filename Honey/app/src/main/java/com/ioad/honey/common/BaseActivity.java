package com.ioad.honey.common;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ioad.honey.task.SelectNetworkTask;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void selectAsyncData(String url) {
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

    public void imageAsync(String imageCode, ImageView imageView) {
    }


}
