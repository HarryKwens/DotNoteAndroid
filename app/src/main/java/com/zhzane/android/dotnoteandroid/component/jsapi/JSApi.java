package com.zhzane.android.dotnoteandroid.component.jsapi;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.zhzane.android.dotnoteandroid.DB.DBManager;

/**
 * Created by zhzane on 16/3/11.
 *
 * 提供给JavaScript调用的接口必须加上 @JavascriptInterface
 * 在JS中，通过JSApi.method() 调用
 */
public class JSApi {

    private Context context;
    private DBManager mgr;

    public JSApi(Context context, DBManager mgr) {
        this.context = context;
        this.mgr = mgr;
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
     * 测试用接口
     */
    @JavascriptInterface
    public String getBillList() {
        String result = "";
        try {
            result = mgr.queryBill().toString();
        } catch (Exception e) {
            Log.d("DotNote_DB_Error", "getBillList");
        }
        return result;
    }
}