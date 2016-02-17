package com.zhzane.android.dotnoteandroid.DB;

import java.util.Date;

/**
 * Created by KWENS on 2016/2/16.
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
}
