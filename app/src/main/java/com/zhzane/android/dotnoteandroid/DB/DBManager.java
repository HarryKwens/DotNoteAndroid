package com.zhzane.android.dotnoteandroid.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KWENS on 2016/2/16.
 */

public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context){
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }
    /*添加账单*/

    public void addBill(Bill bill){
        db.beginTransaction();
        try {
            db.execSQL("INSERT INTO Bill values(?,?,?,?,?,?,?,?)",
                    new Object[]{bill.BillNo,bill.UserId,bill.Money,bill.CreateTime,bill.LastModifiedTime,bill.ExternalId,bill.TagId,bill.Describe});
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    public void addBills(List<Bill> bills){
        db.beginTransaction();
        try {
            for (Bill bill : bills) {
                db.execSQL("INSERT INTO Bill VALUES(null,?,?,?,?,?,?,?,?)",
                        new Object[]{bill.BillNo,bill.UserId,bill.Money,bill.CreateTime,bill.LastModifiedTime,bill.ExternalId,bill.TagId,bill.Describe});
            }
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }
    /*添加用户*/
    public void addUser(List<User> users){
        db.beginTransaction();
        try {
            for (User user : users) {
                db.execSQL("INSERT INTO User VALUES(null,?,?,?,?,?)",new Object[]{user.UserId,user.UserName,user.TotalMoney,user.RelatedUserId,user.MAC});
            }
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }
    /*添加标签*/
    public void addTag(List<Tag> tags){
        db.beginTransaction();
        try {
            for (Tag tag : tags) {
                db.execSQL("INSERT INTO User VALUES(null,?,?)",new Object[]{tag.TagId,tag.TagName});
            }
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }
    /*账单修改*/
    public void updateBill(Bill bill){
        ContentValues cv = new ContentValues();
        cv.put("BillNo",bill.BillNo);
        cv.put("UserId",bill.UserId);
        cv.put("Money",bill.Money);
        cv.put("CreateTime",bill.CreateTime);
        cv.put("LastModifiedTime",bill.LastModifiedTime);
        cv.put("ExternalId",bill.ExternalId);
        cv.put("TagId", bill.TagId);
        db.update("Bill", cv, "BillNo = ?", new String[]{bill.BillNo});
    }
    /*用户修改*/
    public void updateUser(User user){
        ContentValues cv = new ContentValues();
        cv.put("UserId",user.UserId);
        cv.put("UserName",user.UserName);
        cv.put("TotalMoney",user.TotalMoney);
        cv.put("RelatedUserId",user.RelatedUserId);
        cv.put("MAC",user.MAC);
        db.update("User",cv,"UserId = ?",new String[]{user.UserId});
    }
    /*标签修改*/
    public void updateTag(Tag tag){
        ContentValues cv = new ContentValues();
        cv.put("TagID",tag.TagId);
        cv.put("TagName",tag.TagName);
        db.update("Tag",cv,"TagId = ?", new String[]{tag.TagId});
    }
    /*删除账单*/
    public void deleteBill(Bill bill){
        db.beginTransaction();
        try {
            db.delete("Bill","BillNo = ?",new String[]{bill.BillNo});
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }
    /*删除用户*/
    public void deleteUser(User user){
        db.beginTransaction();
        try {
            db.delete("User","UserId = ?",new String[]{user.UserId});
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }
    /*查询全部*/
    public Cursor queryTheCursor(String tableName){
        Cursor c = db.rawQuery("SELECT * FROM" +tableName,null);
        return c;
    }
    /*查询账单列表*/
    public List<Bill> queryBill(){
        ArrayList<Bill> bills = new ArrayList<Bill>();
        Cursor c = queryTheCursor("Bill");
        while (c.moveToNext()){
            Bill bill = new Bill();
            bill._id = c.getInt(c.getColumnIndex("id"));
            bill.BillNo = c.getString(c.getColumnIndex("BillNo"));
            bill.UserId = c.getString(c.getColumnIndex("UserId"));
            bill.Money = c.getDouble(c.getColumnIndex("Money"));
            bill.CreateTime = c.getString(c.getColumnIndex("CreateTime"));
            bill.TagId = c.getString(c.getColumnIndex("TagId"));
            bills.add(bill);
        }
        c.close();
        return bills;
    }
    /*查询用户列表*/
    public List<User> queryUser(){
        ArrayList<User> users = new ArrayList<User>();
        Cursor c = queryTheCursor("User");
        while (c.moveToNext()){
            User user = new User();
            user._id = c.getInt(c.getColumnIndex("id"));
            user.UserId = c.getString(c.getColumnIndex("UserId"));
            user.UserName = c.getString(c.getColumnIndex("UserName"));
            user.TotalMoney = c.getDouble(c.getColumnIndex("TotalMoney"));
            user.RelatedUserId = c.getString(c.getColumnIndex("RelatedUserId"));
            user.MAC = c.getString(c.getColumnIndex("MAC"));
            users.add(user);
        }
        c.close();
        return users;
    }
    /*查询标签列表*/
    public List<Tag> queryTag(){
        ArrayList<Tag> tags = new ArrayList<Tag>();
        Cursor c = queryTheCursor("Tag");
        while (c.moveToNext()){
            Tag tag = new Tag();
            tag.TagId = c.getString(c.getColumnIndex("TagId"));
            tag.TagName = c.getString(c.getColumnIndex("TagName"));
            tags.add(tag);
        }
        c.close();
        return tags;
    }
    /*关闭数据库*/
    public void CloseDB(){
        db.close();
    }

}
