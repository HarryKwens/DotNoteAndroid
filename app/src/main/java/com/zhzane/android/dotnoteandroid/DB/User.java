package com.zhzane.android.dotnoteandroid.DB;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by KWENS on 2016/2/16.
 * 用户实体类。在setter中对数据进行格式验证。
 */
public class User {
    public int id;
    public String UserId;
    public String UserName;
    public Double TotalMoney;
    public String RelatedUserId;
    public String MAC;

    public User(){}

    public User(int id, String userId, String userName, Double totalMoney, String relatedUserId, String MAC) {
        this.id = id;
        UserId = userId;
        UserName = userName;
        TotalMoney = totalMoney;
        RelatedUserId = relatedUserId;
        this.MAC = MAC;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        try {
            String regex ="^[A-Za-z0-9]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(userId);
            boolean rs = matcher.matches();
            if (rs){
            UserId = userId;
            }
            else{
                Exception e = new Exception("UserId数据格式不正确");
                throw e;
            }
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        try {
            String regex ="^[\\u4e00-\\u9fa5A-Za-z0-9]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(userName);
            boolean rs = matcher.matches();
            if (rs){
                UserName = userName;
            }
            else{
                Exception e = new Exception("UserName数据格式不正确");
                throw e;
            }
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }

    public Double getTotalMoney() {
        return TotalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        try {
            String regex ="^-[1-9]\\d*\\.\\d*|-0\\.\\d*[1-9]\\d*|[1-9]\\d*\\.\\d*|-0\\.\\d*[1-9]\\d*$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(Double.toString(totalMoney));
            boolean rs = matcher.matches();
            if (rs){
                TotalMoney = totalMoney;
            }
            else{
                Exception e = new Exception("totalMoney数据格式不正确");
                throw e;
            }
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }

    public String getRelatedUserId() {
        return RelatedUserId;
    }

    public void setRelatedUserId(String relatedUserId) {
        try {
            String regex ="^[A-Za-z0-9]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(relatedUserId);
            boolean rs = matcher.matches();
            if (rs){
                RelatedUserId = relatedUserId;
            }
            else{
                Exception e = new Exception("RelatedUserId数据格式不正确");
                throw e;
            }
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        try {
            String regex ="^[A-Za-z0-9]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(MAC);
            boolean rs = matcher.matches();
            if (rs){
                this.MAC = MAC;
            }
            else{
                Exception e = new Exception("MAC数据格式不正确");
                throw e;
            }
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }
}
