package com.zhzane.android.dotnoteandroid.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by KWENS on 2016/2/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DataBase_Name = "";
    private static final int DataBase_Vision = 1;

    public DBHelper(Context context){
        super(context,DataBase_Name,null,DataBase_Vision);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Bill" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT,BillNo VARCHAR,UserId VARCHAR,Money DECIMAL,CreateTime VARCHAR,LableId VARCHAR)");
        db.execSQL("CREATE TABLE IF NOT EXISTS User" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT,UserId VARCHAR,UserName VARCHAR,TotalMoney DECIMAL,RelatedUserId VARCHAR,MAC VARCHAR)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Tag" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT,TagId VARCHAR,TagName VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
