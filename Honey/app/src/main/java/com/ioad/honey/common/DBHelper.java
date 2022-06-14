package com.ioad.honey.common;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {

    private final String TAG = getClass().getSimpleName();
    public static final String DATABASE = "honey.db";
    private SQLiteDatabase db;
    private Cursor cursor;
    private Context mContext;
    private String tableName;
    long now = 0;
    Date date = null;
    SimpleDateFormat dateFormat = null;


    public DBHelper(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String searchQuery = "CREATE TABLE if not exists SEARCH_LIST (INDEX_NUM INTEGER PRIMARY KEY AUTOINCREMENT, SEARCH_TEXT, SEARCH_DATE, SEARCH_DELETE)";
        String deliveryAddr = "CREATE TABLE if not exists DELIVERY_ADDR (INDEX_NUM INTEGER PRIMARY KEY AUTOINCREMENT, ADDRESS, ADDRESS_DETAIL, INSERT_DATE)";
        sqLiteDatabase.execSQL(searchQuery);
        sqLiteDatabase.execSQL(deliveryAddr);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public void insertSearchData(String tableName, String search, String date) {
        db = this.getWritableDatabase();
        String insertQuery = "INSERT INTO " + tableName + " ('SEARCH_TEXT', 'SEARCH_DATE') VALUES ('" + search + "', '" + date + "');";
        Log.e(TAG, insertQuery);
        db.execSQL(insertQuery);
    }

    public void insertAddressData(String tableName, String address, String addressDetail, String date) {
        db = this.getWritableDatabase();
        String insertQuery = "INSERT INTO " + tableName + " ('ADDRESS', 'ADDRESS_DETAIL', 'INSERT_DATE') VALUES ('" + address + "', '" + addressDetail + "', '" + date + "');";
        Log.e(TAG, insertQuery);
        db.execSQL(insertQuery);
    }

    public Cursor selectSearchData(String tableName) {
        db = this.getReadableDatabase();
        String selectQuery = "SELECT INDEX_NUM, SEARCH_TEXT,MAX(SEARCH_DATE) FROM " + tableName + " WHERE SEARCH_DELETE is NULL GROUP BY SEARCH_TEXT ORDER BY SEARCH_DATE DESC";
        Log.e(TAG, selectQuery);
        cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public Cursor selectAddressData(String tableName) {
        db = this.getReadableDatabase();
        String selectQuery = "SELECT ADDRESS, ADDRESS_DETAIL, INSERT_DATE FROM " + tableName + " ORDER BY INSERT_DATE DESC LIMIT 0, 5";
        Log.e(TAG, selectQuery);
        cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public void deleteSearchData(String tableName, String search) {
        db = this.getWritableDatabase();
        String deleteQuery = "UPDATE " + tableName + " SET SEARCH_DELETE = '" + getTime() + "' WHERE SEARCH_TEXT = '" + search + "';";
        Log.e(TAG, deleteQuery);
        db.execSQL(deleteQuery);
    }


    private String getTime() {
        String result;
        now = System.currentTimeMillis();
        date = new Date(now);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        result = dateFormat.format(date);
        return result;
    }
}
