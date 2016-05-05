package com.zhzane.android.dotnoteandroid.component.jsapi;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.zhzane.android.dotnoteandroid.DB.Bill;
import com.zhzane.android.dotnoteandroid.DB.DBManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by zhzane on 16/3/11.
 * <p/>
 * 提供给JavaScript调用的接口必须加上 @JavascriptInterface
 * 在JS中，通过JSApi.method() 调用
 */
public class JSApi {

    private Context context;
    private DBManager mgr;
    private List<Bill> bills;

    public JSApi(Context context, DBManager mgr) {
        this.context = context;
        this.mgr = mgr;
        bills = new ArrayList<>();
    }

    /**
     * 测试用接口
     */
    @JavascriptInterface
    public String test() {
        return "This is a test. Successful!";
    }

    /**
     * 测试用接口
     */
    @JavascriptInterface
    public void showToast() {
        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取今日资金流动总和接口
     */
    @JavascriptInterface
    public String getTodayMoney() {
        String result;
        double totalMoney = 0.0;
        SimpleDateFormat formatter;
        Date curDate;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        curDate = new Date(System.currentTimeMillis());
        String DayNow = formatter.format(curDate).substring(0, 10);

        List<Bill> bills = new ArrayList<>();
        try {
            bills = mgr.queryBill();
            for (Bill bill : bills) {
                String creatDay = bill.CreateTime.substring(0, 10);
                if (DayNow.equals(creatDay)) {
                    totalMoney += bill.Money;
                }
            }
        } catch (Exception e) {
            Log.d("DotNote_DB_Error", "getBillList");
        }
        result = "￥" + totalMoney + "";
        return result;
    }
    //获取本地用户账单数据
    @JavascriptInterface
    public String getLocalBillList() {
        List<Bill> bills = new ArrayList<Bill>();
        try {
            bills = mgr.queryBill();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBillData(bills);
    }
    //获取所有账单数据，进行分享数据统计
    @JavascriptInterface
    public String getAllBillList(){
        List<Bill> bills = new ArrayList<Bill>();
        String result = "";
        try {
            bills = mgr.queryAllBill();
            List<String> userIDlist = new ArrayList<String>();
            for (Bill bill : bills){
                if (!userIDlist.contains(bill.UserId)){
                    userIDlist.add(bill.UserId);
                }
            }
            if (userIDlist.size()>1){
               result = getBillData(bills);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 获取账单数据并生成json字符串
     */
    @JavascriptInterface
    public String getBillData(List<Bill> billlst) {
        String date = "";      //该条账单创建日期
        String month = "";      //该条账单所属月份
        double money = 0.0;     //该条账单金额

        List<String> monthList = new ArrayList<>();     //年份+月份分组
//        List<Bill> bills = new ArrayList<Bill>();
        JSONObject jo = new JSONObject();

        try {
            //对查找出来的bill数据集进行排序。
            sortClass sort = new sortClass();
            Collections.sort(billlst, sort);
            //将bill数据集根据年份+月份进行分组，并生成json字符串。
            for (int i = 0; i <= monthList.size(); i++) {
                JSONArray ja = new JSONArray();
                for (Bill bill : billlst) {
                    money = bill.Money;
                    date = bill.CreateTime;
                    month = date.substring(0, 7);
                    if (!monthList.contains(date.substring(0, 7))) {
                        monthList.add(month);   //判断是否已经有该年月份的组别。
                    }
                    //对该条账单进行年月份判断，并做出分组。
                    if (monthList.get(i).toString().equals(month)) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("date", date);
                        jsonObject.put("money", money);
                        ja.put(jsonObject);
                    }
                }
                jo.put(monthList.get(i).toString(), ja);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jo.toString();
    }

}