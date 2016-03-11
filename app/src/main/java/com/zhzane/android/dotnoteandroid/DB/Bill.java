package com.zhzane.android.dotnoteandroid.DB;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by KWENS on 2016/2/16.
 * 账单实体类。在setter中对数据进行格式验证。
 *
 * @id 自增列ID；
 * @BillNo 账单计数，记录第N笔账单。
 * @UserID 账单所属用户Id。
 * @Money 每笔账单所记录金钱数目。正数表示收入，负数表示支出。
 * @CreateTime 每笔账单创建时间。
 * @LastModifiedTime 每笔账单的最后修改时间。
 * @ExternalId 外部用户Id
 * @TagId 每笔账单所属标签，每笔账单可以同时拥有多个TagId。
 */
public class Bill {
    public int _id;
    //    public String BillNo;
    public String UserId;
    public Double Money;
    public String CreateTime;
    public String LastModifiedTime;
    public String ExternalId;
    public String TagId;
    public String Describe;

    public Bill() {
    }

    public Bill(int id, String userId, Double money, String createTime, String lastModifiedTime, String externalId, String tagId, String describe) {
        this._id = id;
//        BillNo = billNo;
        UserId = userId;
        Money = money;
        CreateTime = createTime;
        LastModifiedTime = lastModifiedTime;
        ExternalId = externalId;
        TagId = tagId;
        Describe = describe;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

//    public String getBillNo() {
//        return BillNo;
//    }
//
//    public void setBillNo(String billNo) {
//        String regex ="^[A-Za-z0-9]+$";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(billNo);
//        boolean rs = matcher.matches();
//        if (rs){
//            BillNo = billNo;
//        }
//    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public Double getMoney() {
        return Money;
    }

    public void setMoney(Double money) {
        String regex = "^-[1-9]\\d*\\.\\d*|-0\\.\\d*[1-9]\\d*|[1-9]\\d*\\.\\d*|-0\\.\\d*[1-9]\\d*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(Double.toString(money));
        boolean rs = matcher.matches();
        if (rs) {
            Money = money;
        }
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        String regex = "^[A-Za-z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(createTime);
        boolean rs = matcher.matches();
        if (rs) {
            CreateTime = createTime;
        }
    }

    public String getTagId() {
        return TagId;
    }

    public void setTagId(String tagId) {
        TagId = tagId;
    }

    public String getLastModifiedTime() {
        return LastModifiedTime;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        LastModifiedTime = lastModifiedTime;
    }

    public String getExternalId() {
        return ExternalId;
    }

    public void setExternalId(String externalId) {
        String regex = "^[A-Za-z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(externalId);
        boolean rs = matcher.matches();
        if (rs) {
            ExternalId = externalId;
        }
    }

    public String getDescribe() {
        return Describe;
    }

    public void setDescribe(String describe) {
        String regex = "^[A-Za-z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(describe);
        boolean rs = matcher.matches();
        if (rs) {
            Describe = describe;
        }
    }

    public String toJSON(String userId) throws JSONException {
        JSONObject json = new JSONObject();
        if (userId.equals(UserId)) {
            json.put("UserId", userId);
            json.put("Money", Money);
            json.put("CreateTime", CreateTime);
            json.put("LastModifiedTime", LastModifiedTime);
            json.put("ExternalId", ExternalId);
            json.put("TagId", TagId);
            json.put("Describe", Describe);
        }
        return json.toString();

    }
}
