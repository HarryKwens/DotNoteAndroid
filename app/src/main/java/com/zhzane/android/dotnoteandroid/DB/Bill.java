package com.zhzane.android.dotnoteandroid.DB;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by KWENS on 2016/2/16.
 * 账单实体类。在setter中对数据进行格式验证。
 */
public class Bill {
    public int id;
    public String BillNo;
    public String UserId;
    public Double Money;
    public String CreateTime;
    public String TagId;

    public Bill(){}

    public Bill(int id,String billNo, String userId, Double money, String createTime, String tagId) {
        this.id = id;
        BillNo = billNo;
        UserId = userId;
        Money = money;
        CreateTime = createTime;
        TagId = tagId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBillNo() {
        return BillNo;
    }

    public void setBillNo(String billNo) {
        try {
            String regex ="^[A-Za-z0-9]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(billNo);
            boolean rs = matcher.matches();
            if (rs){
                BillNo = billNo;
            }
            else{
                Exception e = new Exception("BillNo数据格式不正确");
                throw e;
            }
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }

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
        try {
            String regex ="^-[1-9]\\d*\\.\\d*|-0\\.\\d*[1-9]\\d*|[1-9]\\d*\\.\\d*|-0\\.\\d*[1-9]\\d*$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(Double.toString(money));
            boolean rs = matcher.matches();
            if (rs){
                Money = money;
            }
            else{
                Exception e = new Exception("money数据格式不正确");
                throw e;
            }
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        try {
            String regex ="^[A-Za-z0-9]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(createTime);
            boolean rs = matcher.matches();
            if (rs){
                CreateTime = createTime;
            }
            else{
                Exception e = new Exception("createTime数据格式不正确");
                throw e;
            }
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }

    public String getTagId() {
        return TagId;
    }

    public void setTagId(String tagId) {
        TagId = tagId;
    }
}
