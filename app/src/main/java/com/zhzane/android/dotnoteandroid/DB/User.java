package com.zhzane.android.dotnoteandroid.DB;

/**
 * Created by KWENS on 2016/2/16.
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
}
