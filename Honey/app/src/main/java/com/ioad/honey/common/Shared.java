package com.ioad.honey.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Shared {
    public static SharedPreferences preferences;

    public static void setStringArrayPref(Context context, String key, ArrayList<String> values) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        JSONArray arr = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            arr.put(values.get(i));
        }

        if (!values.isEmpty()) {
            editor.putString(key, arr.toString());
        } else {
            editor.putString(key, null);
        }

        editor.commit();
    }

    public static void setStringPrf(Context context, String key, String value) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public static void removeStringArrayPref(Context context, String key) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
//        String json = preferences.getString(key, "");
//        if (json != null) {
//            try {
//                JSONArray array = new JSONArray(json);
//                for (int i = 0; i < array.length(); i++) {
//                    editor.remove(i);
//                    Log.e(TAG, array.toString());
//                }
//            } catch (JSONException e){
//                e.printStackTrace();
//            }
//        }
                    editor.remove(key);
                    editor.commit();
    }

    public static void removeStringPrf(Context context, String key) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.commit();
    }


    public static ArrayList<String> getStringArrayPref(Context context, String key) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String json = preferences.getString(key, null);
        ArrayList<String> urls = new ArrayList<String>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

    public static String getStringPref(Context context, String key) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String str = preferences.getString(key, "");
        return str;
    }
}
