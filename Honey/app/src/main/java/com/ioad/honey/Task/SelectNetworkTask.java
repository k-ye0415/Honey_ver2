package com.ioad.honey.Task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ioad.honey.Bean.Ingredient;
import com.ioad.honey.Bean.Menu;
import com.ioad.honey.Bean.Tip;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SelectNetworkTask extends AsyncTask<Integer, String, Object> {

    final String TAG = getClass().getSimpleName();

    private Context mContext;
    private String mAddr;
    private ArrayList<Menu> menus;
    private ArrayList<Tip> tips;
    private ArrayList<Ingredient> ingredients;
    private String where;
    private String kind;

    public SelectNetworkTask(Context mContext, String mAddr, String where, String kind) {
        this.mContext = mContext;
        this.mAddr = mAddr;
        this.where = where;
        this.kind = kind;

        switch (this.kind) {
            case "tip" :
                this.tips = new ArrayList<>();
                break;
            case "food":
                this.menus = new ArrayList<>();
                break;
            case "ingredient" :
                this.ingredients = new ArrayList<>();
                break;
        }
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


                switch (where) {
                    case "select":
                        parserSelect(stringBuffer.toString(), kind);
                        break;
                    case "count":
                        count = parserSelectCount(stringBuffer.toString());
                        break;
                    default:
                        result = parserAction(stringBuffer.toString());
                        break;
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

        if (where.equals("count")) {
            return count;
        }

        if (where.equals("select") && kind.equals("tip")) {
            return tips;
        } else if (where.equals("select") && kind.equals("food")){
            return menus;
        } else if (where.equals("select") && kind.equals("ingredient")) {
            return ingredients;
        }
        else {
            return result;
        }
    }


    private void parserSelect(String str, String kind) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString(kind));



            switch (kind) {
                case "tip":
                    tips.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                        String code = jsonObject1.getString("code");
                        String cId = jsonObject1.getString("id");
                        String cName = jsonObject1.getString("name");
                        String content = jsonObject1.getString("content");


                        Tip tip = new Tip(code, cId, cName, content);
                        tips.add(tip);
                    }
                    break;
                case "food" :
                    menus.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                        String code = jsonObject1.getString("code");
                        String category = jsonObject1.getString("category");
                        String name = jsonObject1.getString("name");
                        String image1 = jsonObject1.getString("image1");

                        Log.d(TAG, "code ::: " + code);
                        Log.d(TAG, "category ::: " + category);
                        Log.d(TAG, "name ::: " + name);
                        Log.d(TAG, "image1 ::: " + image1);

                        Menu menu = new Menu(code, category, name, image1);
                        menus.add(menu);

                    }
                    break;
                case "ingredient":
                    ingredients.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                        String code = jsonObject1.getString("code");
                        String name = jsonObject1.getString("name");
                        String capacity = jsonObject1.getString("capacity");
                        String unit = jsonObject1.getString("unit");
                        String price = jsonObject1.getString("price");
                        boolean isChecked = false;

                        Ingredient ingredient = new Ingredient(isChecked, code, name, capacity, unit, price);
                        ingredients.add(ingredient);
                    }
                    break;
            }



        } catch (Exception e) {
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


    private String parserSelectCount(String str) {
        String countResult = null;
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("tipcount"));

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                countResult = jsonObject1.getString("count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return countResult;
    }
}
