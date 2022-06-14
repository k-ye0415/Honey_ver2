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

    /**
     * Activity View
     * */
    public void setActivityView(){

    }

    /**
     * AsyncTask Select List
     * */
    public void selectAsyncData() {}

    /**
     * AsyncTask Select data
     * */
    public String selectAsyncResult() {
        String result = null;
        return result;
    }

    /**
     * AsyncTask insert
     * */
    public String insertAsyncData() {
        String result = null;
        return result;
    }

    /**
     * AsyncTask insert
     * */
    public String insertAsyncData(String buyCode) {
        String result = null;
        return result;
    }

    /**
     * AsyncTask update
     * */
    public String updateAsyncData() {
        String result = null;
        return result;
    }

    /**
     * AsyncTask update
     * */
    public String updateAsyncData(String tempURL) {
        String result = null;
        return result;
    }

    /**
     * AsyncTask delete
     * */
    public String deleteAsyncData() {
        String result = null;
        return result;
    }

    /**
     * SQLite select
     * */
    public void selectSQLite(){}

    /**
     * SQLite insert
     * */
    public void insertSQLite(){}

    /**
     * SQLite update
     * */
    public void updateSQLite(){}

    /**
     * SQLite delete
     * */
    public void deleteSQLite(){}


    /**
     * AsyncTask Image
     * */
    public void imageAsync(String imageCode, ImageView imageView) {
    }


}
