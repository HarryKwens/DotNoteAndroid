package com.zhzane.android.dotnoteandroid.component.jsapi;

import com.zhzane.android.dotnoteandroid.DB.Bill;

import java.util.Comparator;

/**
 * Created by KWENS on 2016/4/12.
 * 账单排序
 */
public class sortClass implements Comparator {
    public int compare(Object arg0,Object arg1){
        Bill bill0 = (Bill)arg0;
        Bill bill1 = (Bill)arg1;
        int flag = bill1.getCreateTime().compareTo(bill0.getCreateTime());
        return flag;
    }
}
