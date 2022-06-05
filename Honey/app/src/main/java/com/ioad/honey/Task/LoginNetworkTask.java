package com.ioad.honey.Task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ioad.honey.Activity.LoginActivity;
import com.ioad.honey.Bean.UserInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class LoginNetworkTask extends AsyncTask<Integer, String, Object> {

    final String TAG = getClass().getSimpleName();

    private Context mContext;
    private String mAddr;
    private String where;
    private String kind;
    private ArrayList<UserInfo> userInfos;

    public LoginNetworkTask(Context mContext, String mAddr, String where, String kind) {
        this.mContext = mContext;
        this.mAddr = mAddr;
        this.where = where;
        this.kind = kind;
        this.userInfos = new ArrayList<>();
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String result = null;
        String count = null;

        try {
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                while (true) {
                    String strLine = bufferedReader.readLine();
                    if (strLine == null) break;
                    stringBuffer.append(strLine + "\n");
                }

                if (where.equals("login")) {
                    parserSelect(stringBuffer.toString());
                } else {
                    result = parserAction(stringBuffer.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (inputStream != null) inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (where.equals("login")) {
            if (userInfos != null && userInfos.size() != 0) {
                return userInfos;
            } else {
                return null;
            }
        } else {
            return result;
        }
    }


    private void parserSelect(String str){
        try{
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("login_info"));
            userInfos.clear();

            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String userId = jsonObject1.getString("cId");

                if (userId == null || userId.equals("")) {
                    Log.d(TAG, "cId is Null");
                } else {
                    UserInfo userInfo = new UserInfo(userId);
                    userInfos.add(userInfo);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String parserAction(String str) {
        String returnValue = null;
        try {
            JSONObject jsonObject = new JSONObject(str);
            returnValue = jsonObject.getString("result");
        }catch (Exception e) {
            e.printStackTrace();
        }

        return returnValue;
    }
}
