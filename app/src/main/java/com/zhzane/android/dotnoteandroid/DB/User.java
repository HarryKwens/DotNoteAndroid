package com.zhzane.android.dotnoteandroid.DB;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by KWENS on 2016/2/16.
 * 用户实体类。在setter中对数据进行格式验证。
 * @id 自增列id;
 * @UserId 用户Id 格式为英文数字组合，长度为4~15个字符；
 * @UserName 用户名称 格式为中英文组合，长度为3~16个字符；
 * @TotalMoney 总金额。
 * @RelatedUserId 关联用户Id,用于进行多用户数据分析时对所需分析的用户进行确定
 * @MAC 客户端端口标识。
 */

public class User {
    public int _id;
    public int UserId;
    public String UserName;
    public Double TotalMoney;
    public String RelatedUserId;
    public String MAC;

    public User(){}

    public User(int id, int userId, String userName, Double totalMoney, String relatedUserId, String MAC) {
        this._id = id;
        UserId = userId;
        UserName = userName;
        TotalMoney = totalMoney;
        RelatedUserId = relatedUserId;
        this.MAC = MAC;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        String regex ="^[A-Za-z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(String.valueOf(userId));
        boolean rs = matcher.matches();
        if (rs){
        UserId = userId;
        }
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        String regex ="^[\\u4e00-\\u9fa5A-Za-z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userName);
        boolean rs = matcher.matches();
        if (rs){
            UserName = userName;
        }
    }

    public Double getTotalMoney() {
        return TotalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        String regex ="^-[1-9]\\d*\\.\\d*|-0\\.\\d*[1-9]\\d*|[1-9]\\d*\\.\\d*|-0\\.\\d*[1-9]\\d*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(Double.toString(totalMoney));
        boolean rs = matcher.matches();
        if (rs){
            TotalMoney = totalMoney;
        }
    }

    public String getRelatedUserId() {
        return RelatedUserId;
    }

    public void setRelatedUserId(String relatedUserId) {
        String regex ="^[A-Za-z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(relatedUserId);
        boolean rs = matcher.matches();
        if (rs){
            RelatedUserId = relatedUserId;
        }
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        String regex ="^[A-Za-z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(MAC);
        boolean rs = matcher.matches();
        if (rs){
            this.MAC = MAC;
        }
    }

    public String toJSON(String userId) throws JSONException {
        JSONObject json = new JSONObject();
        if (userId.equals(String.valueOf(UserId))) {
            json.put("UserId", userId);
            json.put("UserName", UserName);
            json.put("TotalMoney", TotalMoney);
            json.put("RelatedUserId", RelatedUserId);
            json.put("MAC", MAC);
        }
        return json.toString();
    }
}
