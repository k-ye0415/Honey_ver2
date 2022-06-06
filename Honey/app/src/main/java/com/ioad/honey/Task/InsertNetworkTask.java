package com.ioad.honey.Task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class InsertNetworkTask extends AsyncTask<Integer, String, Object> {

    Context mContext;
    String urlStr;
    String where;

    public InsertNetworkTask(Context mContext, String urlStr, String where) {
        this.mContext = mContext;
        this.urlStr = urlStr;
        this.where = where;
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String result = null;

        try{
            URL url = new URL(urlStr);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                while (true) {
                    String strLine = bufferedReader.readLine();
                    if (strLine == null) break;
                    stringBuffer.append(strLine + "\n");
                }
//                if (where.equals("select")) {
//                    parserSelect(stringBuffer.toString());
//                    //리턴값없고
//                } else if (where.equals("login")){
//                    parserlogin(stringBuffer.toString());
//                } else if (where.equals("id_find")){
//                    parserid_find(stringBuffer.toString());
//                } else if (where.equals("pw_find")){
//                    parserpw_find(stringBuffer.toString());
//                } else {
                    result = parserAction(stringBuffer.toString());
                    //리턴값있어
//                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (inputStream != null) inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }

    private String parserAction(String str){
        String returnValue = null;
        try {
            JSONObject jsonObject = new JSONObject(str);
            returnValue = jsonObject.getString("result");
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnValue;
    }
}
