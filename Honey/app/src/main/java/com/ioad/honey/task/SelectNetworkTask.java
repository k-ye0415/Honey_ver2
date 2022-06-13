package com.ioad.honey.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ioad.honey.bean.BuyDetail;
import com.ioad.honey.bean.BuyHistory;
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

    private final String TAG = getClass().getSimpleName();

    private Context mContext;
    private String mAddr;
    private ArrayList<Menu> menus;
    private Menu menu;
    private ArrayList<Tip> tips;
    private Tip tip;
    private ArrayList<Ingredient> ingredients;
    private Ingredient ingredient;
    private ArrayList<Cart> carts;
    private Cart cart;
    private ArrayList<BuyHistory> histories;
    private BuyHistory history;
    private ArrayList<UserInfo> userInfos;
    private UserInfo userInfo;
    private ArrayList<Search> searches;
    private ArrayList<BuyDetail> details;
    private BuyDetail buyDetail;
    private Search search;
    private String where;
    private String kind;

    public SelectNetworkTask(Context mContext, String mAddr, String where, String kind) {
        this.mContext = mContext;
        this.mAddr = mAddr;
        this.where = where;
        this.kind = kind;

        switch (this.kind) {
            case "tip":
            case "MypageCart":
                this.tips = new ArrayList<>();
                break;
            case "food":
                this.menus = new ArrayList<>();
                break;
            case "ingredient":
                this.ingredients = new ArrayList<>();
                break;
            case "carts_info":
                this.carts = new ArrayList<>();
                break;
            case "paymentHistory_info":
                this.histories = new ArrayList<>();
                break;
            case "myPage":
            case "login_info":
                this.userInfos = new ArrayList<>();
                break;
            case "kfood":
                this.searches = new ArrayList<>();
                break;
            case "paymentDetail_info":
            case "buy_info":
                this.details = new ArrayList<>();
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
        } else if (where.equals("select") && kind.equals("food")) {
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
        } else if (where.equals("select") && kind.equals("login_info")) {
            return userInfos;
        } else if (where.equals("select") && kind.equals("paymentDetail_info")) {
            return details;
        } else if (where.equals("select") && kind.equals("buy_info")) {
            return details;
        }
        else {
            return result;
        }
    }


    private void parserSelect(String str, String kind) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString(kind));
            JSONObject resultJSON;

            switch (kind) {
                case "tip":
                    tips.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        resultJSON = (JSONObject) jsonArray.get(i);
                        String code = resultJSON.getString("code");
                        String cId = resultJSON.getString("id");
                        String cName = resultJSON.getString("name");
                        String content = resultJSON.getString("content");


                        tip = new Tip(code, cId, cName, content);
                        tips.add(tip);
                    }
                    break;
                case "food":
                    menus.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        resultJSON = (JSONObject) jsonArray.get(i);
                        String code = resultJSON.getString("code");
                        String category = resultJSON.getString("category");
                        String name = resultJSON.getString("name");
                        String image1 = resultJSON.getString("image1");

                        menu = new Menu(code, category, name, image1);
                        menus.add(menu);

                    }
                    break;
                case "ingredient":
                    ingredients.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        resultJSON = (JSONObject) jsonArray.get(i);
                        String code = resultJSON.getString("code");
                        String name = resultJSON.getString("name");
                        String capacity = resultJSON.getString("capacity");
                        String unit = resultJSON.getString("unit");
                        String price = resultJSON.getString("price");
                        boolean isChecked = false;

                        ingredient = new Ingredient(isChecked, code, name, capacity, unit, price);
                        ingredients.add(ingredient);
                    }
                    break;
                case "carts_info":
                    carts.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        resultJSON = (JSONObject) jsonArray.get(i);

                        int cartCode = resultJSON.getInt("cartCode");
                        int cartEA = resultJSON.getInt("cartEA");
                        String iName = resultJSON.getString("iName");
                        String iCapacity = resultJSON.getString("iCapacity");
                        String iUnit = resultJSON.getString("iUnit");
                        int iPrice = resultJSON.getInt("iPrice");
                        String mName = resultJSON.getString("mName");
                        String mImagePath = resultJSON.getString("mImagePath");

                        cart = new Cart(cartCode, cartEA, iName, iCapacity, iUnit, iPrice, mName, mImagePath);
                        carts.add(cart);
                    }
                    break;
                case "paymentHistory_info":
                    histories.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        resultJSON = (JSONObject) jsonArray.get(i);

                        String buyNum = resultJSON.getString("buyNum");
                        String buyDeliveryPrice = resultJSON.getString("buyDeliveryPrice");
                        String buyDay = resultJSON.getString("buyDay");
                        String buyCencelDay = resultJSON.getString("buyCencelDay");
                        String iName = resultJSON.getString("iName");
                        String iCapacity = resultJSON.getString("iCapacity");
                        String iUnit = resultJSON.getString("iUnit");
                        String count = resultJSON.getString("count");

                        history = new BuyHistory(buyNum, buyDeliveryPrice, buyDay, buyCencelDay, iName, iCapacity, iUnit, count);
                        histories.add(history);
                    }
                    break;
                case "MypageCart":
                    tips.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        resultJSON = (JSONObject) jsonArray.get(i);

                        String userId = resultJSON.getString("Client_cId");
                        String mCode = resultJSON.getString("mCode");
                        String mName = resultJSON.getString("mName");
                        String tipContent = resultJSON.getString("tipContent");
                        String tipAddDay = resultJSON.getString("tipAddDay");

                        tip = new Tip(userId, mCode, mName, tipContent, tipAddDay);
                        tips.add(tip);

                    }
                    break;
                case "myPage":
                    userInfos.clear();
                    resultJSON = (JSONObject) jsonArray.get(0);
                    String userId = resultJSON.getString("userId");
                    String userPw = resultJSON.getString("userPw");
                    String userName = resultJSON.getString("userName");
                    String userTel = resultJSON.getString("userTel");
                    String userPostNum = resultJSON.getString("userPostNum").equals("null")
                            ? "00000"
                            : resultJSON.getString("userPostNum");
                    String userAddress1 = resultJSON.getString("userAddress1");
                    String userAddress2 = resultJSON.getString("userAddress2");
                    String userEmail = resultJSON.getString("userEmail");
                    String cartCount = resultJSON.getString("cartCount");

                    userInfo = new UserInfo(userId, userPw, userName, userTel, userPostNum, userAddress1, userAddress2, userEmail, cartCount);
                    userInfos.add(userInfo);
                    break;
                case "kfood":
                    searches.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        resultJSON = (JSONObject) jsonArray.get(i);
                        String menuCode = resultJSON.getString("mCode");
                        String menuCategory = resultJSON.getString("mCategory");
                        String menuName = resultJSON.getString("mName");
                        String menuImage1 = resultJSON.getString("mImagePath");
                        String menuAddDay = resultJSON.getString("mAddDay");
                        String menuImage2 = resultJSON.getString("mImage2");

                        Search search = new Search(menuCode, menuCategory, menuName, menuImage1, menuAddDay, menuImage2);
                        searches.add(search);
                    }
                    break;
                case "login_info":
                    userInfos.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        resultJSON = (JSONObject) jsonArray.get(i);
                        String userID = resultJSON.getString("cId");

                        userInfo = new UserInfo(userID);
                        userInfos.add(userInfo);

                    }
                    break;
                case "paymentDetail_info":
                    details.clear();
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                        String ingredientName = jsonObject1.getString("iName");
                        String ingredientCapacity = jsonObject1.getString("iCapacity");
                        String ingredientUnit = jsonObject1.getString("iUnit");
                        String ingredientPrice = jsonObject1.getString("iPrice");
                        String menuName = jsonObject1.getString("mName");
                        String menuImagePath = jsonObject1.getString("mImagePath");
                        String buyPostNum = jsonObject1.getString("buyPostNum").equals("null")
                                ? ""
                                : jsonObject1.getString("buyPostNum");
                        String buyAddr = jsonObject1.getString("buyAddress1");
                        String buyAddrDetail = jsonObject1.getString("buyAddress2");
                        String buyRequests = jsonObject1.getString("buyRequests");
                        String buyDeliveryPrice = jsonObject1.getString("buyDeliveryPrice");
                        int buyEA = jsonObject1.getInt("buyEA");
                        String buyDay = jsonObject1.getString("buyDay");
                        String buyCancelDay = jsonObject1.getString("buyCencelDay");

                        buyDetail = new BuyDetail(ingredientName, ingredientCapacity, ingredientUnit, ingredientPrice,
                                menuName, menuImagePath, buyPostNum, buyAddr, buyAddrDetail, buyRequests, buyDeliveryPrice,
                                buyEA, buyDay, buyCancelDay);
                        details.add(buyDetail);

                    }
                    break;
                case "buy_info":
                    details.clear();
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                        String iName = jsonObject1.getString("iName");
                        String iCapacity = jsonObject1.getString("iCapacity");
                        String iUnit = jsonObject1.getString("iUnit");
                        String buyPostNum = jsonObject1.getString("buyPostNum");
                        String buyAddress1 = jsonObject1.getString("buyAddress1");
                        String buyAddress2 = jsonObject1.getString("buyAddress2");
                        String buyRequests = jsonObject1.getString("buyRequests");
                        String buyDeliveryPrice = jsonObject1.getString("buyDeliveryPrice");
                        String count = jsonObject1.getString("count");

                        buyDetail = new BuyDetail(iName, iCapacity, iUnit, buyPostNum, buyAddress1, buyAddress2, buyRequests, buyDeliveryPrice, count);
                        details.add(buyDetail);

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
        } catch (Exception e) {
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
