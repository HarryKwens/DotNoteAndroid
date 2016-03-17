package com.zhzane.android.dotnoteandroid.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.util.JsonReader;
import android.util.Log;

import com.zhzane.android.dotnoteandroid.activities.PersonActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KWENS on 2016/2/16.
 */

public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;
    public JSONObject jsonObject;
    public User currentUser;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();

        List<User> users = queryCurrentUser();
        if (users.size() > 0) {
            for (User user : users) {
                currentUser = user;
            }
        } else {
            currentUser = new User();
            currentUser.UserId = 1;
            currentUser.UserName = "username";
            currentUser.TotalMoney = 0.0;
            currentUser.RelatedUserId = "";
            currentUser.MAC = "";
            addUser(currentUser);
        }
    }

    /*添加账单*/

    public void addBill(Bill bill) {
        db.beginTransaction();
        try {
            db.execSQL("INSERT INTO Bill (UserId,Money,CreateTime,LastModifiedTime,ExternalId,TagId,Describe) values (?,?,?,?,?,?,?)",
                    new Object[]{bill.UserId, bill.Money, bill.CreateTime, bill.LastModifiedTime, bill.ExternalId, bill.TagId, bill.Describe});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void addBills(List<Bill> bills) {
        db.beginTransaction();
        try {
            for (Bill bill : bills) {
                db.execSQL("INSERT INTO Bill (UserId,Money,CreateTime,LastModifiedTime,ExternalId,TagId,Describe) values (?,?,?,?,?,?,?)",
                        new Object[]{bill.UserId, bill.Money, bill.CreateTime, bill.LastModifiedTime, bill.ExternalId, bill.TagId, bill.Describe});
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    /*添加用户*/
    public void addUser(User user) {
        db.beginTransaction();
        try {
            db.execSQL("INSERT INTO User (UserId,UserName,TotalMoney,RelatedUserId,MAC) VALUES(?,?,?,?,?)",
                    new Object[]{user.UserId, user.UserName, user.TotalMoney, user.RelatedUserId, user.MAC});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void addUser(List<User> users) {
        db.beginTransaction();
        try {
            for (User user : users) {
                db.execSQL("INSERT INTO User (UserId,UserName,TotalMoney,RelatedUserId,MAC) VALUES(?,?,?,?,?)",
                        new Object[]{user.UserId, user.UserName, user.TotalMoney, user.RelatedUserId, user.MAC});
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    /*添加标签*/
    public void addTag(Tag tag) {
        db.beginTransaction();
        try {
            db.execSQL("INSERT INTO Tag (TagId,TagName,UseNum,Describe,mac) VALUES(?,?,?,?,?)",
                    new Object[]{tag.TagId, tag.TagName, tag.UseNum, tag.Describe, tag.mac});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void addTag(List<Tag> tags) {
        db.beginTransaction();
        try {
            for (Tag tag : tags) {
                db.execSQL("INSERT INTO Tag (TagId,TagName,UseNum,Describe,mac) VALUES(?,?,?,?,?)",
                        new Object[]{tag.TagId, tag.TagName, tag.UseNum, tag.Describe, tag.mac});
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    /*账单修改*/
    public void updateBill(Bill bill) {
        ContentValues cv = new ContentValues();
        cv.put("UserId", bill.UserId);
        cv.put("Money", bill.Money);
        cv.put("CreateTime", bill.CreateTime);
        cv.put("LastModifiedTime", bill.LastModifiedTime);
        cv.put("ExternalId", bill.ExternalId);
        cv.put("TagId", bill.TagId);
        cv.put("Describe", bill.Describe);
        db.update("Bill", cv, "_id = ?", new String[]{Integer.toString(bill._id)});
    }

    /*用户修改*/
    public void updateUser(User user) {
        ContentValues cv = new ContentValues();
        cv.put("UserId", user.UserId);
        cv.put("UserName", user.UserName);
        cv.put("TotalMoney", user.TotalMoney);
        cv.put("RelatedUserId", user.RelatedUserId);
        cv.put("MAC", user.MAC);
        db.update("User", cv, "UserId = ?", new String[]{Integer.toString(user.UserId)});
    }

    /*标签修改*/
    public void updateTag(Tag tag) {
        ContentValues cv = new ContentValues();
        cv.put("TagID", tag.TagId);
        cv.put("TagName", tag.TagName);
        cv.put("UseNum", tag.UseNum);
        cv.put("Describe", tag.Describe);
        cv.put("mac", tag.mac);
        db.update("Tag", cv, "TagId = ?", new String[]{Integer.toString(tag.TagId)});
    }

    /*删除账单*/
    public void deleteBill(Bill bill) {
        db.beginTransaction();
        try {
            db.delete("Bill", "_id = ?", new String[]{Integer.toString(bill._id)});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    /*删除用户*/
    public void deleteUser(User user) {
        db.beginTransaction();
        try {
            db.delete("User", "UserId = ?", new String[]{Integer.toString(user.UserId)});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    /*查询全部*/
    public Cursor queryTheCursor(String tableName) {
        Cursor c = db.rawQuery("SELECT * FROM " + tableName, null);
        return c;
    }

    public Cursor queryTheCursorByWhere(String tableName, String column, String where) {
        Cursor c = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + column + " = ?", new String[]{where});
        return c;
    }

    public Cursor queryTheCursor(String tableName, String whereStr) {
        Cursor c = db.rawQuery("SELECT * FROM " + tableName + " ORDER BY " + whereStr, null);
        return c;
    }

    /*查询账单列表*/
    public List<Bill> queryBill() throws JSONException {
        ArrayList<Bill> bills = new ArrayList<Bill>();
        JSONArray jsons = new JSONArray();
        jsonObject = new JSONObject();
        //Cursor c = queryTheCursor("Bill");
        Cursor c = queryTheCursorByWhere("Bill","UserId", "1");
        while (c.moveToNext()) {
            Bill bill = new Bill();
            bill._id = c.getInt(c.getColumnIndex("_id"));
            bill.UserId = c.getString(c.getColumnIndex("UserId"));
            bill.Money = c.getDouble(c.getColumnIndex("Money"));
            bill.CreateTime = c.getString(c.getColumnIndex("CreateTime"));
            bill.LastModifiedTime = c.getString(c.getColumnIndex("LastModifiedTime"));
            bill.ExternalId = c.getString(c.getColumnIndex("ExternalId"));
            bill.TagId = c.getString(c.getColumnIndex("TagId"));
            bill.Describe = c.getString(c.getColumnIndex("Describe"));

            if (bill.UserId.equals(String.valueOf(currentUser.UserId))) {
                jsons.put(bill.toJSON(String.valueOf(currentUser.UserId)));
            }

            bills.add(bill);
        }
        jsonObject.put("bill", jsons);
        c.close();
        return bills;
    }

    /*查询用户列表*/
    public List<User> queryCurrentUser() {
        ArrayList<User> users = new ArrayList<User>();
        Cursor c = queryTheCursorByWhere("User", "UserId", "1");
        JSONArray jsons = new JSONArray();
        while (c.moveToNext()) {
            User user = new User();
            user._id = c.getInt(c.getColumnIndex("_id"));
            user.UserId = c.getInt(c.getColumnIndex("UserId"));
            user.UserName = c.getString(c.getColumnIndex("UserName"));
            user.TotalMoney = c.getDouble(c.getColumnIndex("TotalMoney"));
            user.RelatedUserId = c.getString(c.getColumnIndex("RelatedUserId"));
            user.MAC = c.getString(c.getColumnIndex("MAC"));

            try {
                jsons.put(user.toJSON(String.valueOf(user.UserId)));
            } catch (Exception e) {

            }
            users.add(user);
        }
        try {
            jsonObject.put("user", jsons);
        } catch (Exception e) {

        }
        c.close();
        return users;
    }

    public List<User> queryUser() {
        ArrayList<User> users = new ArrayList<User>();
        Cursor c = queryTheCursor("User");
        JSONArray jsons = new JSONArray();
        while (c.moveToNext()) {
            User user = new User();
            user._id = c.getInt(c.getColumnIndex("_id"));
            user.UserId = c.getInt(c.getColumnIndex("UserId"));
            user.UserName = c.getString(c.getColumnIndex("UserName"));
            user.TotalMoney = c.getDouble(c.getColumnIndex("TotalMoney"));
            user.RelatedUserId = c.getString(c.getColumnIndex("RelatedUserId"));
            user.MAC = c.getString(c.getColumnIndex("MAC"));

            try {
                jsons.put(user.toJSON(String.valueOf(user.UserId)));
            } catch (Exception e) {

            }
            users.add(user);
        }
        try {
            jsonObject.put("user", users);
        } catch (Exception e) {

        }
        c.close();
        return users;
    }

    /*查询标签列表*/
    public List<Tag> queryTag() {
        ArrayList<Tag> tags = new ArrayList<Tag>();
        Cursor c = queryTheCursor("Tag", "UseNum");
        JSONArray jsons = new JSONArray();
        while (c.moveToNext()) {
            Tag tag = new Tag();
            tag.TagId = c.getInt(c.getColumnIndex("TagId"));
            tag.TagName = c.getString(c.getColumnIndex("TagName"));
            tag.UseNum = c.getInt(c.getColumnIndex("UseNum"));
            tag.Describe = c.getString(c.getColumnIndex("Describe"));
            tag.mac = c.getString(c.getColumnIndex("mac"));
            try {
                jsons.put(tag.toJSON());
            } catch (Exception e) {
                e.printStackTrace();
            }

            tags.add(tag);
        }
        try {
            jsonObject.put("tag", jsons);
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.close();
        return tags;
    }

    public void toJSON(String userId) {

        queryCurrentUser();
        queryTag();

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName = "MyJson.json";
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdirs();
        }

        File file = new File(path, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                file.delete();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(jsonObject.toString());
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取Json文件。
     */
    public void getJSON() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName = "MyJson.json";
        String jsonStr;
        String resultStr = "";
        List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();

        try {
            //读取json文件
            File file = new File(path, fileName);
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));

            while ((jsonStr = br.readLine()) != null) {
                resultStr += jsonStr;
            }
            JSONObject jsonOb = new JSONObject(resultStr);
            //分别获取JSON文件中bill，user，tag数据。
            getJsonData(jsonOb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取json文件中的数据，并插入数据库
     *
     * @param json json文件中的数据，以JSONObject形式保存。
     */
    public void getJsonData(JSONObject json) {
        try {
            JSONArray billJa = json.getJSONArray("bill");
            JSONArray userJa = json.getJSONArray("user");
            JSONArray tagJa = json.getJSONArray("tag");
            List<User> userlst = queryUser();   //本地数据库中的用户
            List<Tag> taglst = queryTag();      //本地数据库中的标签

            //获取用户数据并插入数据
            boolean isHasUser = false;
            User newUser = new User();
            try {
                //判断导入用户是否已经存在，如不存在，新增用户id添加新用户。
                for (int i = 0; i < userJa.length(); i++) {
                    JSONTokener jsonTokener = new JSONTokener(userJa.get(i).toString());
                    JSONObject jo = new JSONObject(jsonTokener.nextValue().toString());
                    for (User user : userlst) {
                        if (user.MAC.equals(jo.getString("MAC"))) {
                            isHasUser = true;
                            newUser.UserId = user.UserId;
                            user.UserName = jo.getString("UserName");
                            updateUser(user);
                            break;
                        }
                    }
                    if (!isHasUser) {
                        newUser.UserId = userlst.size();
                        newUser.UserName = jo.getString("UserName");
                        newUser.TotalMoney = jo.getDouble("TotalMoney");
                        newUser.RelatedUserId = jo.getString("RelateUserId");
                        newUser.MAC = jo.getString("MAC");
                        addUser(newUser);
                    }
                }
            } catch (Exception e) {

            }
            //获取账单数据并插入数据
            try {
                List<Bill> billList = new ArrayList<>();
                for (int i = 0; i < billJa.length(); i++) {
                    JSONTokener jsonTokener = new JSONTokener(billJa.get(i).toString());
                    JSONObject jo = new JSONObject(jsonTokener.nextValue().toString());
                    Bill jsonBill = new Bill();
                    jsonBill.UserId = String.valueOf(newUser.UserId);
                    jsonBill.Money = jo.getDouble("Money");
                    jsonBill.CreateTime = jo.getString("CreateTime");
                    jsonBill.LastModifiedTime = jo.getString("LastModifiedTime");
                    jsonBill.ExternalId = jo.getString("ExternalId");
                    jsonBill.TagId = jo.getString("TagId");
                    jsonBill.Describe = jo.getString("Describe");
                    billList.add(jsonBill);
                }
                addBills(billList);
            } catch (Exception e) {

            }
            //获取标签数据并插入数据
            try {
                List<Tag> tagList = new ArrayList<>();
                boolean isHasTag = false;
                for (int i = 0; i < tagJa.length(); i++) {
                    JSONTokener jsonTokener = new JSONTokener(tagJa.get(i).toString());
                    JSONObject jo = new JSONObject(jsonTokener.nextValue().toString());
                    for (Tag tag : taglst) {
                        if (tag.mac.equals(jo.getString("mac")) && tag.TagName.equals(jo.getString("TagName"))) {
                            tag.UseNum = jo.getInt("UseNum");
                            updateTag(tag);
                            isHasTag = true;
                            break;
                        }
                    }
                    if (!isHasTag) {
                        Tag tag = new Tag();
                        tag.TagId = taglst.size();
                        tag.TagName = jo.getString("TagName");
                        tag.UseNum = jo.getInt("UseNum");
                        tag.Describe = jo.getString("Describe");
                        tag.mac = jo.getString("mac");
                        tagList.add(tag);
                    }
                }
                addTag(tagList);
            } catch (Exception e) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*关闭数据库*/
    public void CloseDB() {
        db.close();
    }

}
