package com.zhzane.android.dotnoteandroid.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by KWENS on 2016/2/16.
 * @DataBase_Name 数据库名称。
 * @DataBase_Vision 数据库版本。
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DataBase_Name = "DotNote_DB";
    private static final int DataBase_Vision = 1;

    public DBHelper(Context context){
        super(context,DataBase_Name,null,DataBase_Vision);
    }
    //第一次创建数据库时onCreate调用，创建表。
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建账单表（Bill）
        db.execSQL("CREATE TABLE Bill" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT,BillNo VARCHAR,UserId VARCHAR,Money DECIMAL,CreateTime VARCHAR," +
                "LastModifiedTime VARCHAR,ExternalId VARCHAR,TagId VARCHAR)");
        //创建用户表（User）
        db.execSQL("CREATE TABLE User" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT,UserId VARCHAR,UserName VARCHAR,TotalMoney DECIMAL,RelatedUserId VARCHAR,MAC VARCHAR)");
        //创建标签表（Tag）
        db.execSQL("CREATE TABLE Tag" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT,TagId VARCHAR,TagName VARCHAR)");
    }
    //版本修改时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
