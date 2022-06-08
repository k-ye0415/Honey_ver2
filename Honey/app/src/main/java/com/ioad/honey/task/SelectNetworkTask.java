package com.ioad.honey.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ioad.honey.bean.ButHistory;
import com.ioad.honey.bean.Cart;
import com.ioad.honey.bean.Ingredient;
import com.ioad.honey.bean.Menu;
import com.ioad.honey.bean.Search;
import com.ioad.honey.bean.Tip;
import com.ioad.honey.bean.UserInfo;

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
    private ArrayList<Cart> carts;
    private ArrayList<ButHistory> histories;
    private ArrayList<UserInfo> userInfos;
    private ArrayList<Search> searches;
    private String where;
    private String kind;

    public SelectNetworkTask(Context mContext, String mAddr, String where, String kind) {
        this.mContext = mContext;
        this.mAddr = mAddr;
        this.where = where;
        this.kind = kind;

        switch (this.kind) {
            case "tip" :
            case "MypageCart":
                this.tips = new ArrayList<>();
                break;
            case "food":
                this.menus = new ArrayList<>();
                break;
            case "ingredient" :
                this.ingredients = new ArrayList<>();
                break;
            case "carts_info":
                this.carts = new ArrayList<>();
                break;
            case "paymentHistory_info":
                this.histories = new ArrayList<>();
                break;
            case "myPage":
                this.userInfos = new ArrayList<>();
                break;
            case "kfood":
                this.searches = new ArrayList<>();
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
        } else if (where.equals("select") && kind.equals("carts_info")) {
            return carts;
        } else if (where.equals("select") && kind.equals("paymentHistory_info")) {
            return histories;
        } else if (where.equals("select") && kind.equals("MypageCart")) {
            return tips;
        } else if (where.equals("select") && kind.equals("myPage")) {
            return userInfos;
        } else if (where.equals("select") && kind.equals("kfood")) {
            return searches;
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
                case "carts_info":
                    carts.clear();
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                        int cartCode = jsonObject1.getInt("cartCode");
                        int cartEA = jsonObject1.getInt("cartEA");
                        String iName = jsonObject1.getString("iName");
                        String iCapacity = jsonObject1.getString("iCapacity");
                        String iUnit = jsonObject1.getString("iUnit");
                        int iPrice = jsonObject1.getInt("iPrice");
                        String mName = jsonObject1.getString("mName");
                        String mImagePath = jsonObject1.getString("mImagePath");

                        Cart cart = new Cart(cartCode, cartEA, iName, iCapacity, iUnit, iPrice, mName, mImagePath);
                        carts.add(cart);
                    }
                    break;
                case "paymentHistory_info":
                    histories.clear();
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                        String buyNum = jsonObject1.getString("buyNum");
                        String buyDeliveryPrice = jsonObject1.getString("buyDeliveryPrice");
                        String buyDay = jsonObject1.getString("buyDay");
                        String buyCencelDay = jsonObject1.getString("buyCencelDay");
                        String iName = jsonObject1.getString("iName");
                        String iCapacity = jsonObject1.getString("iCapacity");
                        String iUnit = jsonObject1.getString("iUnit");
                        String count = jsonObject1.getString("count");

                        ButHistory history = new ButHistory(buyNum, buyDeliveryPrice, buyDay, buyCencelDay, iName, iCapacity, iUnit, count);
                        histories.add(history);
                    }
                    break;
                case "MypageCart":
                    tips.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                        String userId = jsonObject1.getString("Client_cId");
                        String mCode = jsonObject1.getString("mCode");
                        String mName = jsonObject1.getString("mName");
                        String tipContent = jsonObject1.getString("tipContent");
                        String tipAddDay = jsonObject1.getString("tipAddDay");

                        Tip tip = new Tip(userId, mCode, mName, tipContent, tipAddDay);
                        tips.add(tip);

                    }
                    break;
                case "myPage":
                    userInfos.clear();
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                    String userId = jsonObject1.getString("userId");
                    String userPw = jsonObject1.getString("userPw");
                    String userName = jsonObject1.getString("userName");
                    String userTel = jsonObject1.getString("userTel");
                    String userPostNum = jsonObject1.getString("userPostNum").equals("null")
                            ? "00000"
                            : jsonObject1.getString("userPostNum");
                    String userAddress1 = jsonObject1.getString("userAddress1");
                    String userAddress2 = jsonObject1.getString("userAddress2");
                    String userEmail = jsonObject1.getString("userEmail");
                    String cartCount = jsonObject1.getString("cartCount");

                    UserInfo userInfo = new UserInfo(userId, userPw, userName, userTel, userPostNum, userAddress1, userAddress2, userEmail, cartCount);
                    userInfos.add(userInfo);
                    break;
                case "kfood":
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject2 = (JSONObject) jsonArray.get(i);
                        String menuCode = jsonObject2.getString("mCode");
                        String menuCategory = jsonObject2.getString("mCategory");
                        String menuName = jsonObject2.getString("mName");
                        String menuImage1 = jsonObject2.getString("mImagePath");
                        String menuAddDay = jsonObject2.getString("mAddDay");
                        String menuImage2 = jsonObject2.getString("mImage2");

                        Search search = new Search(menuCode, menuCategory, menuName, menuImage1, menuAddDay, menuImage2);
                        searches.add(search);
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
